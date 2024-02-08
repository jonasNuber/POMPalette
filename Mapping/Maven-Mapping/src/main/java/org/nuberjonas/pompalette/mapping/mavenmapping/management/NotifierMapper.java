package org.nuberjonas.pompalette.mapping.mavenmapping.management;

import org.apache.maven.model.Notifier;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.NotifierDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class NotifierMapper {

    private NotifierMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static NotifierDTO mapToDTO(Notifier notifier){
        return Optional.ofNullable(notifier)
                .map(src -> new NotifierDTO(
                        src.getType(),
                        src.isSendOnError(),
                        src.isSendOnFailure(),
                        src.isSendOnSuccess(),
                        src.isSendOnWarning(),
                        src.getAddress(),
                        src.getConfiguration(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("type")),
                        InputLocationMapper.mapToDTO(src.getLocation("sendOnError")),
                        InputLocationMapper.mapToDTO(src.getLocation("sendOnFailure")),
                        InputLocationMapper.mapToDTO(src.getLocation("sendOnSuccess")),
                        InputLocationMapper.mapToDTO(src.getLocation("sendOnWarning")),
                        InputLocationMapper.mapToDTO(src.getLocation("address")),
                        InputLocationMapper.mapToDTO(src.getLocation("configuration"))))
                .orElse(null);
    }

    public static Notifier mapToModel(NotifierDTO notifierDTO){
        if(notifierDTO == null){
            return null;
        }

        var notifier = new Notifier();
        notifier.setType(notifierDTO.type());
        notifier.setSendOnError(notifierDTO.sendOnError());
        notifier.setSendOnFailure(notifierDTO.sendOnFailure());
        notifier.setSendOnSuccess(notifierDTO.sendOnSuccess());
        notifier.setSendOnWarning(notifierDTO.sendOnWarning());
        notifier.setAddress(notifierDTO.address());
        notifier.setConfiguration(notifierDTO.configuration());

        if (notifierDTO.locations() != null) {
            notifierDTO.locations().forEach((key, value) ->
                    notifier.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        notifier.setLocation("", InputLocationMapper.mapToModel(notifierDTO.location()));
        notifier.setLocation("type", InputLocationMapper.mapToModel(notifierDTO.typeLocation()));
        notifier.setLocation("sendOnError", InputLocationMapper.mapToModel(notifierDTO.sendOnErrorLocation()));
        notifier.setLocation("sendOnFailure", InputLocationMapper.mapToModel(notifierDTO.sendOnFailureLocation()));
        notifier.setLocation("sendOnSuccess", InputLocationMapper.mapToModel(notifierDTO.sendOnSuccessLocation()));
        notifier.setLocation("sendOnWarning", InputLocationMapper.mapToModel(notifierDTO.sendOnWarningLocation()));
        notifier.setLocation("address", InputLocationMapper.mapToModel(notifierDTO.addressLocation()));
        notifier.setLocation("configuration", InputLocationMapper.mapToModel(notifierDTO.configurationLocation()));

        return notifier;
    }
}
