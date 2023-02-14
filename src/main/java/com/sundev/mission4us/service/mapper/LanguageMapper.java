package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Language;
import com.sundev.mission4us.service.dto.LanguageDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<LanguageDTO> toDtoIdSet(Set<Language> language);
}
