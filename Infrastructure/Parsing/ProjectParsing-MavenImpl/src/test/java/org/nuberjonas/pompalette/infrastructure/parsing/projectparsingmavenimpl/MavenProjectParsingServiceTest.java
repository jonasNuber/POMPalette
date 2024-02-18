package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MavenProjectParsingServiceTest {
    private MavenXpp3Reader reader;
    private MapperService<Model, ProjectDTO> mapperService;
    private MavenProjectParsingService parsingService;

    @BeforeEach
    void init(){
        reader = mock(MavenXpp3Reader.class);
        mapperService = mock(MapperService.class);
        parsingService = new MavenProjectParsingService(mapperService);
    }

    @Test
    void loadProject_ShouldReturnValidProjectDTOForValidPath() throws XmlPullParserException, IOException {
        var model = validModel();
        var expected = validProjectDTO();
        var projectPath = mock(Path.class);
        when(projectPath.getFileName()).thenReturn(Paths.get("pom.xml"));
        when(projectPath.toString()).thenReturn("pom.xml");
        when(projectPath.toFile()).thenReturn(new File("pom.xml"));
        when(reader.read(any(FileInputStream.class))).thenReturn(model);
        when(mapperService.mapToDestination(any(Model.class))).thenReturn(expected);

        var actual = parsingService.loadProject(projectPath);

        assertThat(actual.modelVersion()).isEqualTo(expected.modelVersion());
        assertThat(actual.groupId()).isEqualTo(expected.groupId());
        assertThat(actual.artifactId()).isEqualTo(expected.artifactId());
        assertThat(actual.version()).isEqualTo(expected.version());
        assertThat(actual.name()).isEqualTo(expected.name());
    }

    @Test
    void loadProject_ShouldThrowExceptionForIOException() throws XmlPullParserException, IOException {
        doThrow(IOException.class).when(reader).read(any(FileInputStream.class));

        assertThatThrownBy(() ->
                parsingService.loadProject(Paths.get("/src/test/resources/pom.xml"))
        ).isInstanceOf(ProjectParsingException.class)
                .hasMessageContaining("Project pom for the path: \\src\\test\\resources\\pom.xml could not be loaded.");
    }

    private Model validModel(){
        var model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId("com.example");
        model.setArtifactId("example-project");
        model.setVersion("1.0.0");
        model.setName("Example Project");

        return model;
    }

    private ProjectDTO validProjectDTO(){
        return new ProjectDTO(
                null,
                "4.0.0",
                null,
                "com.example",
                "example-project",
                "1.0.0",
                null,
                "Example Project",
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