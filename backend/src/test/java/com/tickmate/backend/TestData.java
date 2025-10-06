package com.tickmate.backend;

import com.tickmate.backend.domain.dto.TaskRequestDTO;
import com.tickmate.backend.domain.dto.TaskResponseDTO;
import com.tickmate.backend.domain.entity.TaskEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TestData {
    private static final UUID TASK_ID_A = UUID.randomUUID();
    private static final UUID TASK_ID_B = UUID.randomUUID();
    private static final UUID TASK_ID_C = UUID.randomUUID();
    private static final LocalDateTime dateTime = LocalDateTime.now();

    // ====== TASK ENTITY ======
    public static TaskEntity createTestTaskEntityA() {
        return TaskEntity.builder()
                .id(TASK_ID_A)
                .title("Design Landing Page")
                .description("Create a clean, responsive layout for the landing page.")
                .status(TaskEntity.Status.IN_PROGRESS)
                .priority(TaskEntity.Priority.HIGH)
                .dueDate(dateTime.plusDays(5).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public static TaskEntity createTestTaskEntityB() {
        return TaskEntity.builder()
                .id(TASK_ID_B)
                .title("Implement Authentication")
                .description("Add JWT-based login and registration endpoints.")
                .status(TaskEntity.Status.PENDING)
                .priority(TaskEntity.Priority.MEDIUM)
                .dueDate(dateTime.plusDays(7).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(1).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public static TaskEntity createTestTaskEntityC() {
        return TaskEntity.builder()
                .id(TASK_ID_C)
                .title("Write API Documentation")
                .description("Document all endpoints using OpenAPI and Swagger UI.")
                .status(TaskEntity.Status.COMPLETED)
                .priority(TaskEntity.Priority.LOW)
                .dueDate(dateTime.minusDays(3).truncatedTo(ChronoUnit.SECONDS))
                .completedAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(10).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    // ====== REQUEST DTO ======
    public static TaskRequestDTO createTestTaskRequestA() {
        return TaskRequestDTO.builder()
                .title("Design Landing Page")
                .description("Create a clean, responsive layout for the landing page.")
                .status(TaskEntity.Status.IN_PROGRESS)
                .priority(TaskEntity.Priority.HIGH)
                .dueDate(dateTime.plusDays(5).truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public static TaskRequestDTO createTestTaskRequestB() {
        return TaskRequestDTO.builder()
                .title("Implement Authentication")
                .description("Add JWT-based login and registration endpoints.")
                .status(TaskEntity.Status.PENDING)
                .priority(TaskEntity.Priority.MEDIUM)
                .dueDate(dateTime.plusDays(7).truncatedTo(ChronoUnit.SECONDS))
                .build();

    }

    public static TaskRequestDTO createTestTaskRequestC() {
        return TaskRequestDTO.builder()
                .title("Write API Documentation")
                .description("Document all endpoints using OpenAPI and Swagger UI.")
                .status(TaskEntity.Status.COMPLETED)
                .priority(TaskEntity.Priority.LOW)
                .dueDate(dateTime.minusDays(3).truncatedTo(ChronoUnit.SECONDS))
                .completedAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    // ====== RESPONSE DTO ======
    public static TaskResponseDTO createTestTaskResponseA() {
        return TaskResponseDTO.builder()
                .id(TASK_ID_A)
                .title("Design Landing Page")
                .description("Create a clean, responsive layout for the landing page.")
                .status(TaskEntity.Status.IN_PROGRESS)
                .priority(TaskEntity.Priority.HIGH)
                .dueDate(dateTime.plusDays(5).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public static TaskResponseDTO createTestTaskResponseB() {
        return TaskResponseDTO.builder()
                .id(TASK_ID_B)
                .title("Implement Authentication")
                .description("Add JWT-based login and registration endpoints.")
                .status(TaskEntity.Status.PENDING)
                .priority(TaskEntity.Priority.MEDIUM)
                .dueDate(dateTime.plusDays(7).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(1).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public static TaskResponseDTO createTestTaskResponseC() {
        return TaskResponseDTO.builder()
                .id(TASK_ID_C)
                .title("Write API Documentation")
                .description("Document all endpoints using OpenAPI and Swagger UI.")
                .status(TaskEntity.Status.COMPLETED)
                .priority(TaskEntity.Priority.LOW)
                .dueDate(dateTime.minusDays(3).truncatedTo(ChronoUnit.SECONDS))
                .completedAt(dateTime.minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .createdAt(dateTime.minusDays(1).truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(dateTime.truncatedTo(ChronoUnit.SECONDS))
                .build();
    }
}
