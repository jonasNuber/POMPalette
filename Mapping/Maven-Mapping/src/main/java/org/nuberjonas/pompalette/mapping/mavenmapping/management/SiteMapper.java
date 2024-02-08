package org.nuberjonas.pompalette.mapping.mavenmapping.management;

import org.apache.maven.model.Site;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.SiteDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class SiteMapper {

    private SiteMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static SiteDTO mapToDTO(Site site){
        return Optional.ofNullable(site)
                .map(src -> new SiteDTO(
                        src.getId(),
                        src.getName(),
                        src.getUrl(),
                        src.isChildSiteUrlInheritAppendPath(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("id")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("childSiteUrlInheritAppendPath"))
                ))
                .orElse(null);
    }

    public static Site mapToModel(SiteDTO siteDTO){
        if(siteDTO == null){
            return null;
        }

        var site = new Site();
        site.setId(siteDTO.id());
        site.setName(siteDTO.name());
        site.setUrl(siteDTO.url());
        site.setChildSiteUrlInheritAppendPath(siteDTO.childSiteUrlInheritAppendPath());

        if (siteDTO.locations() != null) {
            siteDTO.locations().forEach((key, value) ->
                    site.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        site.setLocation("", InputLocationMapper.mapToModel(siteDTO.location()));
        site.setLocation("id", InputLocationMapper.mapToModel(siteDTO.idLocation()));
        site.setLocation("name", InputLocationMapper.mapToModel(siteDTO.nameLocation()));
        site.setLocation("url", InputLocationMapper.mapToModel(siteDTO.urlLocation()));
        site.setLocation("childSiteUrlInheritAppendPath", InputLocationMapper.mapToModel(siteDTO.childSiteUrlInheritAppendPathLocation()));

        return site;
    }
}
