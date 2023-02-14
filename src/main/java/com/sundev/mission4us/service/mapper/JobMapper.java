package com.sundev.mission4us.service.mapper;

import com.sundev.mission4us.domain.Job;
import com.sundev.mission4us.service.dto.JobDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<JobDTO> toDtoIdSet(Set<Job> job);
}
