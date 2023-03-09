package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Client;
import com.sundev.mission4us.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "userId", source = "user.id")
    ClientDTO toDto(Client s);
    @Mapping(target = "user", source = "userId")
    Client toEntity(ClientDTO s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoId(Client client);
}
