package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Attachment;
import com.sundev.mission4us.service.dto.AttachmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClientMapper.class, ProviderMapper.class })
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    @Mapping(target = "provider", source = "provider", qualifiedByName = "id")
    AttachmentDTO toDto(Attachment s);
}
