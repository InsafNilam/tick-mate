package com.tickmate.backend.service.impl;

import com.tickmate.backend.domain.entity.TaskEntity;
import com.tickmate.backend.exception.TaskNotFoundException;
import com.tickmate.backend.repository.TaskRepository;
import com.tickmate.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public TaskEntity createTask(TaskEntity taskRequest) {
        TaskEntity task = TaskEntity.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(
                        taskRequest.getStatus() != null
                                ? taskRequest.getStatus()
                                : TaskEntity.Status.PENDING
                )
                .priority(
                        taskRequest.getPriority() != null
                                ? taskRequest.getPriority()
                                : TaskEntity.Priority.MEDIUM
                )
                .dueDate(taskRequest.getDueDate())
                .completedAt(taskRequest.getCompletedAt())
                .build();

        return taskRepository.save(task);
    }


    @Override
    public Page<TaskEntity> getTasks(List<TaskEntity.Status> status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            return taskRepository.findAll(pageable);
        }
        return taskRepository.findByStatusIn(status, pageable);
    }

    @Override
    public Optional<TaskEntity> getTaskById(UUID taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public TaskEntity updateTask(UUID taskId, TaskEntity taskRequestDTO) {
        TaskEntity existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        if (taskRequestDTO.getTitle() != null) {
            existingTask.setTitle(taskRequestDTO.getTitle());
        }
        if (taskRequestDTO.getDescription() != null) {
            existingTask.setDescription(taskRequestDTO.getDescription());
        }
        if (taskRequestDTO.getStatus() != null) {
            existingTask.setStatus(taskRequestDTO.getStatus());

            // Auto-set completedAt when status changes to COMPLETED
            if (taskRequestDTO.getStatus() == TaskEntity.Status.COMPLETED &&
                    existingTask.getCompletedAt() == null) {
                existingTask.setCompletedAt(LocalDateTime.now());
            }
        }
        if (taskRequestDTO.getPriority() != null) {
            existingTask.setPriority(taskRequestDTO.getPriority());
        }
        if (taskRequestDTO.getDueDate() != null) {
            existingTask.setDueDate(taskRequestDTO.getDueDate());
        }
        if (taskRequestDTO.getCompletedAt() != null) {
            existingTask.setCompletedAt(taskRequestDTO.getCompletedAt());
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }

        taskRepository.deleteById(taskId);
    }
}
