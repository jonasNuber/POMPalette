package org.nuberjonas.pompalette.mapping.projectmapping.contributing;

import org.apache.maven.model.MailingList;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class MailingListMapper {

    private MailingListMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static MailingListDTO mapToDTO(MailingList mailingList){
        return Optional.ofNullable(mailingList)
                .map(src -> new MailingListDTO(
                        src.getName(),
                        src.getSubscribe(),
                        src.getUnsubscribe(),
                        src.getPost(),
                        src.getArchive(),
                        src.getOtherArchives(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("subscribe")),
                        InputLocationMapper.mapToDTO(src.getLocation("unsubscribe")),
                        InputLocationMapper.mapToDTO(src.getLocation("post")),
                        InputLocationMapper.mapToDTO(src.getLocation("archive")),
                        InputLocationMapper.mapToDTO(src.getLocation("otherArchives"))))
                .orElse(null);
    }

    public static MailingList mapToModel(MailingListDTO mailingListDTO){
        if(mailingListDTO == null){
            return null;
        }

        var mailingList = new MailingList();

        mailingList.setName(mailingListDTO.name());
        mailingList.setSubscribe(mailingListDTO.subscribe());
        mailingList.setUnsubscribe(mailingListDTO.unsubscribe());
        mailingList.setPost(mailingListDTO.post());
        mailingList.setArchive(mailingListDTO.archive());
        mailingList.setOtherArchives(mailingListDTO.otherArchives());

        if (mailingListDTO.locations() != null) {
            mailingListDTO.locations().forEach((key, value) ->
                    mailingList.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        mailingList.setLocation("", InputLocationMapper.mapToModel(mailingListDTO.location()));
        mailingList.setLocation("name", InputLocationMapper.mapToModel(mailingListDTO.nameLocation()));
        mailingList.setLocation("subscribe", InputLocationMapper.mapToModel(mailingListDTO.subscribeLocation()));
        mailingList.setLocation("unsubscribe", InputLocationMapper.mapToModel(mailingListDTO.unsubscribeLocation()));
        mailingList.setLocation("post", InputLocationMapper.mapToModel(mailingListDTO.postLocation()));
        mailingList.setLocation("archive", InputLocationMapper.mapToModel(mailingListDTO.archiveLocation()));
        mailingList.setLocation("otherArchives", InputLocationMapper.mapToModel(mailingListDTO.otherArchivesLocation()));

        return mailingList;
    }
}
