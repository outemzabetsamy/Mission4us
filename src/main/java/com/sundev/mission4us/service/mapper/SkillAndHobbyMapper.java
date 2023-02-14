package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.SkillAndHobby;
import com.sundev.mission4us.service.dto.SkillAndHobbyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkillAndHobby} and its DTO {@link SkillAndHobbyDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProviderMapper.class })
public interface SkillAndHobbyMapper extends EntityMapper<SkillAndHobbyDTO, SkillAndHobby> {
    @Mapping(target = "provider", source = "provider", qualifiedByName = "id")
    SkillAndHobbyDTO toDto(SkillAndHobby s);
}
