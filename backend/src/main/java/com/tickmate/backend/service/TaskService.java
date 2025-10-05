package com.tickmate.backend.service;

import com.tickmate.backend.domain.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    TaskEntity createTask(TaskEntity taskRequest);
    Page<TaskEntity> getTasks(List<TaskEntity.Status> status, Pageable pageable);
    Optional<TaskEntity> getTaskById(UUID taskId);
    TaskEntity updateTask(UUID taskId, TaskEntity taskRequest);
    void deleteTask(UUID taskId);
}
