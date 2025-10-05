package com.tickmate.backend.repository;

import com.tickmate.backend.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {
    Page<TaskEntity> findAll(Pageable pageable);
    Page<TaskEntity> findByStatusIn(List<TaskEntity.Status> status, Pageable pageable);
}
