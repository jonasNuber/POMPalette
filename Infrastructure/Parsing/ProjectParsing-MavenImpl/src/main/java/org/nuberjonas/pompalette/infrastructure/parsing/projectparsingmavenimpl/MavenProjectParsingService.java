package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ModelBaseDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ProfileDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.NotAProjectException;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;
import org.nuberjonas.pompalette.mapping.projectmapping.ProjectMapperService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.isDirectory;

public class MavenProjectParsingService implements ProjectParsingService {
    private final MavenXpp3Reader reader;
    private final MavenXpp3Writer writer;
    private final MapperService<Model, ProjectDTO> mapperService;

    @SuppressWarnings("unchecked")
    public MavenProjectParsingService(){
//        this(ServiceDiscovery.loadService(MapperService.class,
//                service -> (
//                        (service.get().sourceType().equals(Model.class)) &&
//                        (service.get().destinationType().equals(ProjectDTO.class)))
//                ));
        this(new ProjectMapperService());
    }

    protected MavenProjectParsingService(MapperService<Model, ProjectDTO> mapperService) {
        reader = new MavenXpp3Reader();
        writer = new MavenXpp3Writer();
        this.mapperService = mapperService;
    }

    @Override
    public ProjectDTO loadProject(Path projectPath) {
        return mapperService.mapToDestination(readProjectPOM(appendPomPathIfDirectory(projectPath)));
    }

    private Model readProjectPOM(Path projectPath){
        if(!Files.exists(projectPath)){
            throw new NotAProjectException(String.format("The provided path: %s does not point to a maven project.", projectPath));
        }

        try(var fis = new FileInputStream(projectPath.toFile())){
            return reader.read(fis);
        } catch (IOException e) {
            throw new ProjectParsingException(String.format("Project for the path: %s could not be loaded.", projectPath), e);
        } catch (XmlPullParserException e) {
            throw new ProjectParsingException(String.format("Project for the path: %s could not be parsed properly.", projectPath), e);
        }
    }

    private Path appendPomPathIfDirectory(Path projectPath){
        return isDirectory(projectPath) ? projectPath.resolve("pom.xml") : projectPath;
    }

    @Override
    public MultiModuleProjectDTO loadMultiModuleProject(Path projectPath) {
        var finalProjectPath = findTopLevelParentProject(appendPomPathIfDirectory(projectPath));
        var project = loadProject(finalProjectPath);
        MultiModuleProjectDTO multiModuleProject;

        if(StringUtils.containsIgnoreCase(project.artifactId(), "BOM") && project.modelBase().modules().size() == 1){
            var bom = new MultiModuleProjectDTO(project);
            bom.setProjectPath(finalProjectPath);

            finalProjectPath = resolveModulePath(finalProjectPath, project.modelBase().modules().get(0));
            project = loadProject(finalProjectPath);
            multiModuleProject = new MultiModuleProjectDTO(project);
            multiModuleProject.setProjectPath(finalProjectPath);
            multiModuleProject.addProjectBOM(bom);
        } else {
            multiModuleProject = new MultiModuleProjectDTO(project);
            multiModuleProject.setProjectPath(finalProjectPath);
            multiModuleProject.addProjectBOM(findProjectBOM(finalProjectPath, project));
        }
        
        return loadMultiModuleProjectModules(finalProjectPath, multiModuleProject);
    }


    private Path findTopLevelParentProject(Path currentProjectPath){
        var model = readProjectPOM(currentProjectPath);

        if(hasParent(model)){
            return findTopLevelParentProject(currentProjectPath.resolveSibling(model.getParent().getRelativePath()).normalize().toAbsolutePath());
        } else {
            return currentProjectPath;
        }
    }

    private boolean hasParent(Model model){
        return model.getParent() != null;
    }

    private MultiModuleProjectDTO findProjectBOM(Path projectPath, ProjectDTO project) {
        var groupId = project.groupId();
        var artifactId = project.artifactId();
        Optional<DependencyDTO> possibleBom = Optional.empty();

        if (project.modelBase().dependencyManagement() != null) {
            possibleBom = project.modelBase().dependencyManagement().dependencies()
                    .stream()
                    .filter(dependencyDTO ->
                            "pom".equals(dependencyDTO.type()) &&
                                    (StringUtils.containsIgnoreCase(dependencyDTO.groupId(), groupId) || StringUtils.containsIgnoreCase(dependencyDTO.groupId(), artifactId)))
                    .findFirst();
        }

        if(possibleBom.isPresent()){
            var bom = possibleBom.get();
            List<Path> folders;

            try {
                folders = Files.list(projectPath.getParent()).filter(Files::isDirectory).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (Path folder : folders) {
                if(StringUtils.containsIgnoreCase(folder.getFileName().toString(), "bom") || Files.isRegularFile(folder.resolve("pom.xml"))){
                    var bomProject = loadProject(folder.resolve("pom.xml"));

                    if(bomProject.groupId().equals(bom.groupId()) && bomProject.artifactId().equals(bom.artifactId()) && bomProject.version().equals(bom.version())){
                        return new MultiModuleProjectDTO(bomProject);
                    }
                }
            }
        }

        return null;
    }

    private MultiModuleProjectDTO loadMultiModuleProjectModules(Path path, MultiModuleProjectDTO multiModuleProject){
        var project = multiModuleProject.get();

        List<String> modules = Optional.ofNullable(project.modelBase())
                .map(ModelBaseDTO::modules)
                .orElse(Collections.emptyList());

        List<String> defaultModules = project.profiles().stream()
                .filter(p -> p.id().equals("default"))
                .findFirst()
                .map(ProfileDTO::modelBase)
                .map(ModelBaseDTO::modules)
                .orElse(Collections.emptyList());

        List<String> selectedModules = modules.isEmpty() ? defaultModules : modules;

        selectedModules.forEach(moduleName -> {
            var modulePath = resolveModulePath(path, moduleName);
            var moduleProject = new MultiModuleProjectDTO(loadProject(modulePath));

            moduleProject = loadMultiModuleProjectModules(modulePath, moduleProject);
            multiModuleProject.addModule(moduleProject);
        });

        return multiModuleProject;
    }

    private Path resolveModulePath(Path parentPath, String moduleName) {
        return parentPath.resolveSibling(moduleName).resolve("pom.xml");
    }

    @Override
    public void writeProject(ProjectDTO project, Path projectPath) {
        projectPath = appendPomPathIfDirectory(projectPath);

        try(var fos = new FileOutputStream(projectPath.toFile())) {
            writer.write(fos, mapperService.mapToSource(project));
        } catch (IOException e) {
            throw new ProjectParsingException(String.format("The project could not be written to the path: %s.", projectPath), e);
        }
    }

    @Override
    public void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject, Path projectPath) {
        var finalProjectPath = appendPomPathIfDirectory(projectPath);

        writeProject(multiModuleProject.get(), finalProjectPath);
        multiModuleProject.getModules().forEach(module -> writeMultiModuleProject(module, resolveModulePath(finalProjectPath, module.get().name())));
    }
}
