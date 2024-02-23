package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ModelBaseDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ParentDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MavenProjectParsingServiceTest {
    private static final String EXAMPLE_WRITE_PROJECT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <groupId>com.example</groupId>\n" +
            "  <artifactId>example-Project</artifactId>\n" +
            "  <version>1.0.0</version>\n" +
            "  <name>ExampleProject</name>\n" +
            "</project>\n";
    private static final String MULTI_MODULE_PARENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <groupId>com.example</groupId>\n" +
            "  <artifactId>example-project</artifactId>\n" +
            "  <version>1.0.0</version>\n" +
            "  <name>ExampleProject</name>\n" +
            "  <modules>\n" +
            "    <module>Module1</module>\n" +
            "    <module>Module2</module>\n" +
            "  </modules>\n" +
            "</project>\n";
    private static final String MODULE1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <parent>\n" +
            "    <groupId>com.example</groupId>\n" +
            "    <artifactId>example-project</artifactId>\n" +
            "    <version>1.0.0</version>\n" +
            "  </parent>\n" +
            "  <artifactId>module1</artifactId>\n" +
            "  <version>1.0.0</version>\n" +
            "  <name>Module1</name>\n" +
            "  <modules>\n" +
            "    <module>Module3</module>\n" +
            "  </modules>\n" +
            "</project>\n";
    private static final String MODULE2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <parent>\n" +
            "    <groupId>com.example</groupId>\n" +
            "    <artifactId>example-project</artifactId>\n" +
            "    <version>1.0.0</version>\n" +
            "  </parent>\n" +
            "  <artifactId>module2</artifactId>\n" +
            "  <version>1.0.0</version>\n" +
            "  <name>Module2</name>\n" +
            "</project>\n";
    private static final String MODULE3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <parent>\n" +
            "    <groupId>com.example.example-project</groupId>\n" +
            "    <artifactId>module1</artifactId>\n" +
            "    <version>1.0.0</version>\n" +
            "  </parent>\n" +
            "  <artifactId>module3</artifactId>\n" +
            "  <version>1.0.0</version>\n" +
            "  <name>Module3</name>\n" +
            "</project>\n";
    private MapperService<Model, ProjectDTO> mapperService;
    private MavenProjectParsingService parsingService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void init(){
        mapperService = mock(MapperService.class);
        parsingService = new MavenProjectParsingService(mapperService);
    }

    @Test
    void loadProject_ShouldReturnValidProjectDTOForValidPomPath() {
        shouldReturnValidProjectDTOFor(Paths.get("src/test/resources/ExampleProject/pom.xml"));
    }

    @Test
    void loadProject_ShouldReturnValidProjectDTOForValidProjectPath() {
        shouldReturnValidProjectDTOFor(Paths.get("src/test/resources/ExampleProject"));
    }

    private void shouldReturnValidProjectDTOFor(Path projectPath){
        var expected = validProjectDTO(null, "com.example", "example-project", "ExampleProject", null);
        when(mapperService.mapToDestination(any(Model.class))).thenReturn(expected);

        var actual = parsingService.loadProject(projectPath);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void loadProject_ShouldThrowExceptionForInvalidPath() {
        assertThatThrownBy(() ->
                parsingService.loadProject(Paths.get("src/test/resources/pom.xml"))
        )
                .isInstanceOf(ProjectParsingException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasMessageContaining("Project for the path: src\\test\\resources\\pom.xml could not be loaded.");
    }

    @Test
    void loadProject_ShouldThrowExceptionForMalformedProjectPOM(){
        assertThatThrownBy(() ->
                parsingService.loadProject(Paths.get("src/test/resources/InvalidExample/pom.xml"))
        )
                .isInstanceOf(ProjectParsingException.class)
                .hasCauseInstanceOf(XmlPullParserException.class)
                .hasMessageContaining("Project for the path: src\\test\\resources\\InvalidExample\\pom.xml could not be parsed properly.");
    }

    @Test
    void loadMultiModuleProject_ShouldReturnValidProjectForProjectWithoutModules(){
        var expected = new MultiModuleProjectDTO(validProjectDTO(null, "com.example", "example-project", "ExampleProject", null));
        when(mapperService.mapToDestination(any(Model.class))).thenReturn(validProjectDTO(null, "com.example", "example-project", "ExampleProject", null));

        var actual = parsingService.loadMultiModuleProject(Paths.get("src/test/resources/ExampleModuleProject/pom.xml"));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void loadMultiModuleProject_ShouldReturnValidProjectForModuleProjectWithValidPomPath(){
        shouldReturnValidMultiModuleProjectFor(Paths.get("src/test/resources/ExampleModuleProject/pom.xml"));
    }

    @Test
    void loadMultiModuleProject_ShouldReturnValidProjectForModuleProjectWithValidProjectPath(){
        shouldReturnValidMultiModuleProjectFor(Paths.get("src/test/resources/ExampleModuleProject"));
    }

    private void shouldReturnValidMultiModuleProjectFor(Path projectPath){
        var exampleProject = validProjectDTO(null, "com.example", "example-project", "ExampleProject", List.of("Module1", "Module2"));
        var module1 = validProjectDTO(new ParentDTO("com.example", "example-project", "1.0.0", null, null, null, null, null, null, null), null, "module1", "Module1", List.of("Module3"));
        var module2 = validProjectDTO(new ParentDTO("com.example", "example-project", "1.0.0", null, null, null, null, null, null, null), null, "module2", "Module2", null);
        var module3 = validProjectDTO(new ParentDTO("com.example.example-project", "module1", "1.0.0", null, null, null, null, null, null, null), null, "module3", "Module3", null);
        var module1Multi = new MultiModuleProjectDTO(module1);
        module1Multi.addModule(module3);
        var expected = new MultiModuleProjectDTO(exampleProject);
        expected.addModule(module1Multi);
        expected.addModule(module2);
        when(mapperService.mapToDestination(argThat(model -> model != null && "example-project".equals(model.getArtifactId())))).thenReturn(exampleProject);
        when(mapperService.mapToDestination(argThat(model -> model != null && "module1".equals(model.getArtifactId())))).thenReturn(module1);
        when(mapperService.mapToDestination(argThat(model -> model != null && "module2".equals(model.getArtifactId())))).thenReturn(module2);
        when(mapperService.mapToDestination(argThat(model -> model != null && "module3".equals(model.getArtifactId())))).thenReturn(module3);

        var actual = parsingService.loadMultiModuleProject(projectPath);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void writeProject_ShouldWriteProjectToFileForValidPomPath() throws IOException {
        shouldWriteProjectToFileFor(Paths.get("src/test/resources/ExampleWriteProject/pom.xml"));
    }

    @Test
    void writeProject_ShouldWriteProjectToFileForValidProjectPath() throws IOException {
        shouldWriteProjectToFileFor(Paths.get("src/test/resources/ExampleWriteProject"));
    }

    private void shouldWriteProjectToFileFor(Path projectPath) throws IOException {
        var exampleProject = validProjectDTO(null, "com.example", "example-Project", "ExampleProject", null);
        when(mapperService.mapToSource(any(ProjectDTO.class))).thenReturn(validModel(null, "com.example", "example-Project", "ExampleProject", null));

        parsingService.writeProject(exampleProject, projectPath);
        var actual = Files.readString(resolvePomPath(projectPath));

        assertThat(actual).isEqualTo(EXAMPLE_WRITE_PROJECT);

        Files.writeString(resolvePomPath(projectPath), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "</project>");
    }

    private Path resolvePomPath(Path projectPath){
        return Files.isRegularFile(projectPath) ? projectPath : projectPath.resolve(Paths.get("pom.xml"));
    }

    @Test
    void writeProject_ShouldThrowExceptionForInvalidPath(){
        assertThatThrownBy(() ->
                parsingService.writeProject(validProjectDTO(null, "com.example", "example-Project", "ExampleProject", null), Paths.get("some/wrong/path/pomers.xml"))
        )
                .isInstanceOf(ProjectParsingException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasMessageContaining("The project could not be written to the path: some\\wrong\\path\\pomers.xml.");
    }

    @Test
    void writeMultiModuleProject_ShouldWriteProjectWithoutModules() throws IOException {
        var project = new MultiModuleProjectDTO(validProjectDTO(null, "com.example", "example-Project", "ExampleProject", null));
        var projectPath = Paths.get("src/test/resources/ExampleWriteProject");
        when(mapperService.mapToSource(project.get())).thenReturn(validModel(null, "com.example", "example-Project", "ExampleProject", null));

        parsingService.writeMultiModuleProject(project, projectPath);
        var actual = Files.readString(resolvePomPath(projectPath));

        assertThat(actual).isEqualTo(EXAMPLE_WRITE_PROJECT);

        Files.writeString(resolvePomPath(projectPath), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "</project>");
    }

    @Test
    void writeMultiModuleProject_ShouldWriteProjectForPomPath() throws IOException {
        shouldWriteMultiModuleProjectToFilesFor(Paths.get("src/test/resources/ExampleModuleWriteProject/pom.xml"));
    }

    @Test
    void writeMultiModuleProject_ShouldWriteProjectForProjectPath() throws IOException {
        shouldWriteMultiModuleProjectToFilesFor(Paths.get("src/test/resources/ExampleModuleWriteProject"));
    }

    private void shouldWriteMultiModuleProjectToFilesFor(Path projectPath) throws IOException {
        var resolvedProjectPath = resolvePomPath(projectPath);
        var exampleProject = validProjectDTO(null, "com.example", "example-project", "ExampleProject", List.of("Module1", "Module2"));
        var exampleModel = validModel(null, "com.example", "example-project", "ExampleProject", List.of("Module1", "Module2"));
        var module1 = validProjectDTO(new ParentDTO("com.example", "example-project", "1.0.0", null, null, null, null, null, null, null), null, "module1", "Module1", List.of("Module3"));
        var modelParent = new Parent();
        modelParent.setGroupId("com.example");
        modelParent.setArtifactId("example-project");
        modelParent.setVersion("1.0.0");
        var modelModule1 = validModel(modelParent, null, "module1", "Module1", List.of("Module3"));
        var module2 = validProjectDTO(new ParentDTO("com.example", "example-project", "1.0.0", null, null, null, null, null, null, null), null, "module2", "Module2", null);
        var modelModule2 = validModel(modelParent, null, "module2", "Module2", null);
        var module3 = validProjectDTO(new ParentDTO("com.example.example-project", "module1", "1.0.0", null, null, null, null, null, null, null), null, "module3", "Module3", null);
        var modelModule1Parent = new Parent();
        modelModule1Parent.setGroupId("com.example.example-project");
        modelModule1Parent.setArtifactId("module1");
        modelModule1Parent.setVersion("1.0.0");
        var modelModule3 = validModel(modelModule1Parent, null, "module3", "Module3", null);
        var module1Multi = new MultiModuleProjectDTO(module1);
        module1Multi.addModule(module3);
        var multiModuleProjectDTO = new MultiModuleProjectDTO(exampleProject);
        multiModuleProjectDTO.addModule(module1Multi);
        multiModuleProjectDTO.addModule(module2);
        var paths = new ArrayList<Path>();
        paths.add(resolvedProjectPath);
        var module1Path = resolveModulePath(resolvedProjectPath, "Module1");
        paths.add(module1Path);
        paths.add(resolveModulePath(resolvedProjectPath, "Module2"));
        paths.add(resolveModulePath(module1Path, "Module3"));
        var expectedContents = new ArrayList<String>();
        expectedContents.add(MULTI_MODULE_PARENT);
        expectedContents.add(MODULE1);
        expectedContents.add(MODULE2);
        expectedContents.add(MODULE3);
        when(mapperService.mapToSource(argThat(projectDTO -> projectDTO != null && "example-project".equals(projectDTO.artifactId())))).thenReturn(exampleModel);
        when(mapperService.mapToSource(argThat(projectDTO -> projectDTO != null && "module1".equals(projectDTO.artifactId())))).thenReturn(modelModule1);
        when(mapperService.mapToSource(argThat(projectDTO -> projectDTO != null && "module2".equals(projectDTO.artifactId())))).thenReturn(modelModule2);
        when(mapperService.mapToSource(argThat(projectDTO -> projectDTO != null && "module3".equals(projectDTO.artifactId())))).thenReturn(modelModule3);

        parsingService.writeMultiModuleProject(multiModuleProjectDTO, projectPath);

        for(var i = 0; i < paths.size(); i++){
            var actual = Files.readString(paths.get(i));
            var expected = expectedContents.get(i);

            assertThat(actual).isEqualTo(expected);

            Files.writeString(paths.get(i), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\" xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "</project>");
        }
    }

    private Path resolveModulePath(Path parentPath, String moduleName) {
        return parentPath.resolveSibling(moduleName).resolve("pom.xml");
    }

    private Model validModel(Parent parent, String groupId, String artifactId, String name, List<String> modules){
        var model = new Model();
        model.setModelVersion("4.0.0");
        model.setParent(parent);
        model.setGroupId(groupId);
        model.setArtifactId(artifactId);
        model.setVersion("1.0.0");
        model.setName(name);
        model.setModules(modules);

        return model;
    }

    private ProjectDTO validProjectDTO(ParentDTO parentdto, String groupId, String artifactId, String name, List<String> modules){
        return new ProjectDTO(
                new ModelBaseDTO(modules, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null),
                "4.0.0",
                parentdto,
                groupId,
                artifactId,
                "1.0.0",
                null,
                name,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}