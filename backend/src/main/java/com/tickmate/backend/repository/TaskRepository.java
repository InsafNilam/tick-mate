package com.tickmate.backend.repository;

import com.tickmate.backend.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, UUID>,
        PagingAndSortingRepository<TaskEntity, UUID> {
    @Query("""
        SELECT t FROM TaskEntity t
        WHERE
          (:status IS NULL OR t.status IN :status)
          AND (:priority IS NULL OR t.priority IN :priority)
          AND (:q IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%')))
    """)
    Page<TaskEntity> searchTasks(@Param("status") List<TaskEntity.Status> status,
                                 @Param("priority") List<TaskEntity.Priority> priority,
                                 @Param("q") String query,
                                 Pageable pageable);
}
