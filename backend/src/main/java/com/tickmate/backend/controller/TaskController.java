package com.tickmate.backend.controller;

import com.tickmate.backend.domain.dto.TaskRequestDTO;
import com.tickmate.backend.domain.dto.TaskResponseDTO;
import com.tickmate.backend.domain.dto.validators.CreateTaskValidatorGroup;
import com.tickmate.backend.domain.entity.TaskEntity;
import com.tickmate.backend.mapper.TaskMapper;
import com.tickmate.backend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    @Operation(summary = "Get Tasks")
    public ResponseEntity<Page<TaskResponseDTO>> getTasks(@RequestParam(required = false) List<TaskEntity.Status> status,
                                                          @PageableDefault(size = 20,
                                                                  page = 0,
                                                                  sort = "dueDate",
                                                                  direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TaskEntity> tasks = taskService.getTasks(status, pageable);
        return ResponseEntity.ok().body(tasks.map(taskMapper::toDTO));
    }

    @PostMapping
    @Operation(summary = "Create Task")
    public ResponseEntity<TaskResponseDTO> createTask(@Validated({Default.class, CreateTaskValidatorGroup.class}) @RequestBody TaskRequestDTO dto) {
        TaskEntity task = taskMapper.toEntity(dto);

        TaskResponseDTO taskDTO = taskMapper.toDTO(taskService.createTask(task));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get Task By ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") UUID id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(taskMapper.toDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update Task")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable("id") UUID id, @Validated({Default.class}) @RequestBody TaskRequestDTO dto){
        TaskEntity request = taskMapper.toEntity(dto);
        TaskEntity task = taskService.updateTask(id, request);

        TaskResponseDTO taskDTO = taskMapper.toDTO(task);
        return ResponseEntity.ok(taskDTO);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete Task")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
