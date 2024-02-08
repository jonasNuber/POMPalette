package de.nuberjonas.pompalette.mapping.mavenmapping.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.ConfigurationContainerDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.ConfigurationContainer;

import java.util.Optional;

public class ConfigurationContainerMapper {

    private ConfigurationContainerMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ConfigurationContainerDTO mapToDTO(ConfigurationContainer configurationContainer){
        return Optional.ofNullable(configurationContainer)
                .map(src -> new ConfigurationContainerDTO(
                        src.isInherited(),
                        src.getConfiguration(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("inherited")),
                        InputLocationMapper.mapToDTO(src.getLocation("configuration")),
                        src.isInheritanceApplied()))
                .orElse(null);
    }

    public static ConfigurationContainer mapToModel(ConfigurationContainerDTO configurationContainerDTO){
        if(configurationContainerDTO == null){
            return null;
        }

        var configurationContainer = new ConfigurationContainer();
        configurationContainer.setInherited(configurationContainerDTO.inherited());
        configurationContainer.setConfiguration(configurationContainerDTO.configuration());

        if (configurationContainerDTO.locations() != null) {
            configurationContainerDTO.locations().forEach((key, value) ->
                    configurationContainer.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        configurationContainer.setLocation("", InputLocationMapper.mapToModel(configurationContainerDTO.location()));
        configurationContainer.setLocation("inherited", InputLocationMapper.mapToModel(configurationContainerDTO.inheritedLocation()));
        configurationContainer.setLocation("configuration", InputLocationMapper.mapToModel(configurationContainerDTO.configurationLocation()));

        if(!configurationContainerDTO.inheritanceApplied()){
            configurationContainer.unsetInheritanceApplied();
        }

        return configurationContainer;
    }
}
