package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.infrastructure.serviceloading.ServiceDiscovery;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MavenProjectParsingService implements ProjectParsingService {
    private final MavenXpp3Reader reader;
    private final MavenXpp3Writer writer;
    private final MapperService<Model, ProjectDTO> mapperService;

    @SuppressWarnings("unchecked")
    public MavenProjectParsingService(){
        this(ServiceDiscovery.loadService(MapperService.class,
                service -> (
                        (service.get().sourceType().equals(Model.class)) &&
                        (service.get().destinationType().equals(ProjectDTO.class)))
                ));
    }

    protected MavenProjectParsingService(MapperService<Model, ProjectDTO> mapperService) {
        reader = new MavenXpp3Reader();
        writer = new MavenXpp3Writer();
        this.mapperService = mapperService;
    }

    @Override
    public ProjectDTO loadProject(Path projectPath) {
        projectPath = createValidPomPath(projectPath);

        try(var fis = new FileInputStream(projectPath.toFile())){
            var model = reader.read(fis);
            return mapperService.mapToDestination(model);
        } catch (IOException e) {
            throw new ProjectParsingException(String.format("Project pom for the path: %s could not be loaded.", projectPath.toString()), e);
        } catch (XmlPullParserException e) {
            throw new ProjectParsingException(String.format("Project pom for the path: %s could not be parsed properly.", projectPath.toString()), e);
        }
    }

    private Path createValidPomPath(Path projectPath){
        var projectPathLastSegment = projectPath.getFileName().toString();

        if(!"pom.xml".equals(projectPathLastSegment)){
            if(Files.isRegularFile(projectPath)){
                return projectPath.resolveSibling("pom.xml");
            }
            return projectPath.resolve("pom.xml");
        }
        return projectPath;
    }

    @Override
    public MultiModuleProjectDTO loadMultiModuleProject(Path projectPath) {
        projectPath = createValidPomPath(projectPath);
        var project = loadProject(projectPath);
        var multiModuleProject = new MultiModuleProjectDTO(project);

        for(var moduleName : project.modelBase().modules()){
            var modulePath = createModulePath(projectPath, moduleName);
            var module = loadMultiModuleProject(modulePath);
            multiModuleProject.addModule(module);
        }

        return multiModuleProject;
    }

    private Path createModulePath(Path parentPath, String moduleName) {
        var parentPathString = parentPath.toString();
        var lastSeparatorIndex = parentPathString.lastIndexOf("/");
        var modulePathString = parentPathString.substring(0, lastSeparatorIndex) +
                "/" + moduleName +
                parentPathString.substring(lastSeparatorIndex);

        return Paths.get(modulePathString);
    }

    @Override
    public void writeProject(ProjectDTO project, Path projectPath) {
        try(var fos = new FileOutputStream(projectPath.toFile())) {
            writer.write(fos, mapperService.mapToSource(project));
        } catch (IOException e) {
            throw new ProjectParsingException(String.format("The project could not be written to the path: %s.", projectPath.toString()), e);
        }
    }

    @Override
    public void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject) {

    }
}
