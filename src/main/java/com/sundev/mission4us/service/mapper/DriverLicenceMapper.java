package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.DriverLicence;
import com.sundev.mission4us.service.dto.DriverLicenceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DriverLicence} and its DTO {@link DriverLicenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverLicenceMapper extends EntityMapper<DriverLicenceDTO, DriverLicence> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<DriverLicenceDTO> toDtoIdSet(Set<DriverLicence> driverLicence);
}
