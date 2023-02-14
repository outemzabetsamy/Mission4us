package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Experience;
import com.sundev.mission4us.service.dto.ExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Experience} and its DTO {@link ExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProviderMapper.class })
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {
    @Mapping(target = "provider", source = "provider", qualifiedByName = "id")
    ExperienceDTO toDto(Experience s);
}
