package com.tickmate.backend.domain.dto;

import com.tickmate.backend.domain.dto.validators.CreateTaskValidatorGroup;
import com.tickmate.backend.domain.entity.TaskEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    private TaskEntity.Status status;

    private TaskEntity.Priority priority;

    @Future(groups = CreateTaskValidatorGroup.class, message = "Due date must be in the future")
    private LocalDateTime dueDate;

    @PastOrPresent(groups = CreateTaskValidatorGroup.class, message = "Completed date must be in the past or present")
    private LocalDateTime completedAt;

    @AssertTrue(message = "Completed date can only be set when status is COMPLETED or ARCHIVED")
    private boolean isCompletedAtValid() {
        if (completedAt == null) {
            return true;
        }
        return status == TaskEntity.Status.COMPLETED ||
                status == TaskEntity.Status.ARCHIVED;
    }
}
