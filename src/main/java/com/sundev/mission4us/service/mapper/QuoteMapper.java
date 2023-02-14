package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Quote;
import com.sundev.mission4us.service.dto.QuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote} and its DTO {@link QuoteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProviderMapper.class, MissionMapper.class })
public interface QuoteMapper extends EntityMapper<QuoteDTO, Quote> {
    @Mapping(target = "provider", source = "provider", qualifiedByName = "id")
    @Mapping(target = "mission", source = "mission", qualifiedByName = "id")
    QuoteDTO toDto(Quote s);
}
