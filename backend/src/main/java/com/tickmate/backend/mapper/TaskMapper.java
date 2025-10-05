package com.tickmate.backend.mapper;

import com.tickmate.backend.domain.dto.TaskRequestDTO;
import com.tickmate.backend.domain.dto.TaskResponseDTO;
import com.tickmate.backend.domain.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    TaskResponseDTO toDTO(TaskEntity task);
    TaskEntity toEntity(TaskRequestDTO dto);
}
