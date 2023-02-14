package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Mission;
import com.sundev.mission4us.service.dto.MissionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mission} and its DTO {@link MissionDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, ClientMapper.class })
public interface MissionMapper extends EntityMapper<MissionDTO, Mission> {
    @Mapping(target = "languages", source = "languages", qualifiedByName = "idSet")
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    MissionDTO toDto(Mission s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MissionDTO toDtoId(Mission mission);

    @Mapping(target = "removeLanguage", ignore = true)
    Mission toEntity(MissionDTO missionDTO);
}
