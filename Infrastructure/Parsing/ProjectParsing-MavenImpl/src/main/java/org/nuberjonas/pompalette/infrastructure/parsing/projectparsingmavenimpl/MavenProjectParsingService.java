package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ModelBaseDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ProfileDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;
import org.nuberjonas.pompalette.mapping.projectmapping.ProjectMapperService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        projectPath = appendPomPathIfDirectory(projectPath);

        try(var fis = new FileInputStream(projectPath.toFile())){
            var model = reader.read(fis);
            return mapperService.mapToDestination(model);
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
        var finalProjectPath = appendPomPathIfDirectory(projectPath);
        var project = loadProject(projectPath);
        var multiModuleProject = new MultiModuleProjectDTO(project);


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
            var modulePath = resolveModulePath(finalProjectPath, moduleName);
            multiModuleProject.addModule(loadMultiModuleProject(modulePath));
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
