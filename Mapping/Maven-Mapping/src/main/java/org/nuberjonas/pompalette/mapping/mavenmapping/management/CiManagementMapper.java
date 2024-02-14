package org.nuberjonas.pompalette.mapping.mavenmapping.management;

import org.apache.maven.model.CiManagement;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class CiManagementMapper {

    private CiManagementMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static CiManagementDTO mapToDTO(CiManagement ciManagement){
        return Optional.ofNullable(ciManagement)
                .map(src -> new CiManagementDTO(
                        src.getSystem(),
                        src.getUrl(),
                        ListMapper.mapList(src.getNotifiers(), NotifierMapper::mapToDTO),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("system")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("notifiers"))))
                .orElse(null);
    }

    public static CiManagement mapToModel(CiManagementDTO ciManagementDTO){
        if(ciManagementDTO == null){
            return null;
        }

        var ciManagement = new CiManagement();
        ciManagement.setSystem(ciManagementDTO.system());
        ciManagement.setUrl(ciManagementDTO.url());
        ciManagement.setNotifiers(ListMapper.mapList(ciManagementDTO.notifiers(), NotifierMapper::mapToModel));

        if (ciManagementDTO.locations() != null) {
            ciManagementDTO.locations().forEach((key, value) ->
                    ciManagement.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        ciManagement.setLocation("", InputLocationMapper.mapToModel(ciManagementDTO.location()));
        ciManagement.setLocation("system", InputLocationMapper.mapToModel(ciManagementDTO.systemLocation()));
        ciManagement.setLocation("url", InputLocationMapper.mapToModel(ciManagementDTO.urlLocation()));
        ciManagement.setLocation("notifiers", InputLocationMapper.mapToModel(ciManagementDTO.notifiersLocation()));

        return ciManagement;
    }
}
