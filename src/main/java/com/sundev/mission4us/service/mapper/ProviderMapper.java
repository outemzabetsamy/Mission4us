package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Provider;
import com.sundev.mission4us.service.dto.ProviderDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provider} and its DTO {@link ProviderDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, LanguageMapper.class, JobMapper.class, DriverLicenceMapper.class })
public interface ProviderMapper extends EntityMapper<ProviderDTO, Provider> {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "languages", source = "languages", qualifiedByName = "idSet")
    @Mapping(target = "jobs", source = "jobs", qualifiedByName = "idSet")
    @Mapping(target = "driverLicences", source = "driverLicences", qualifiedByName = "idSet")
    ProviderDTO toDto(Provider s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProviderDTO toDtoId(Provider provider);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "removeLanguage", ignore = true)
    @Mapping(target = "removeJob", ignore = true)
    @Mapping(target = "removeDriverLicence", ignore = true)
    Provider toEntity(ProviderDTO providerDTO);
}
