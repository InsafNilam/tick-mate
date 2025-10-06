package com.tickmate.backend.repository;

import com.tickmate.backend.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, UUID>,
        PagingAndSortingRepository<TaskEntity, UUID> {
    Page<TaskEntity> findByStatusIn(List<TaskEntity.Status> status, Pageable pageable);
}
