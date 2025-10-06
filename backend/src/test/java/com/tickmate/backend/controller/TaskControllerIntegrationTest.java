package com.tickmate.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tickmate.backend.TestData;
import com.tickmate.backend.domain.dto.TaskRequestDTO;
import com.tickmate.backend.domain.dto.TaskResponseDTO;
import com.tickmate.backend.domain.entity.TaskEntity;
import com.tickmate.backend.mapper.TaskMapper;
import com.tickmate.backend.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private final TaskMapper taskMapper;

    private TaskEntity testTask;

    @Autowired
    public TaskControllerIntegrationTest(TaskService taskService, MockMvc mockMvc, TaskMapper taskMapper) {
        this.mockMvc = mockMvc;
        this.taskMapper = taskMapper;
        this.taskService = taskService;
        this.objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        testTask = taskService.createTask(taskMapper.toEntity(TestData.createTestTaskRequestA()));
    }

    // ---------------- CREATE ----------------
    @Test
    public void testCreateTaskReturnsCreatedTask() throws Exception {
        TaskRequestDTO request = TestData.createTestTaskRequestA();
        TaskResponseDTO dto = TestData.createTestTaskResponseA();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(dto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(dto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(dto.getPriority().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate").value(dto.getDueDate().toString()));
    }

    // ---------------- GET ALL ----------------
    @Test
    public void testGetAllTasksReturnsTasksList() throws Exception {
        // Create a second task for testing list
        taskService.createTask(taskMapper.toEntity(TestData.createTestTaskRequestB()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1));
    }

    @Test
    public void testGetTasksWithPagination() throws Exception {
        // Create multiple tasks
        taskService.createTask(taskMapper.toEntity(TestData.createTestTaskRequestB()));
        taskService.createTask(taskMapper.toEntity(TestData.createTestTaskRequestC()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2));
    }

    @Test
    public void testGetTasksWithStatusFilter() throws Exception {
        // Create tasks with different statuses
        TaskEntity completedTask = taskMapper.toEntity(TestData.createTestTaskRequestB());
        completedTask.setStatus(TaskEntity.Status.COMPLETED);
        taskService.createTask(completedTask);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .param("status", TaskEntity.Status.COMPLETED.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").value(TaskEntity.Status.COMPLETED.name()));
    }

    // ---------------- GET BY ID ----------------
    @Test
    public void testGetTaskByIdReturnsTaskWhenExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/" + testTask.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testTask.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testTask.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testTask.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(testTask.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(testTask.getPriority().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate").value(testTask.getDueDate().toString()));
    }

    @Test
    public void testGetTaskByIdReturns404WhenNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ---------------- UPDATE ----------------
    @Test
    public void testUpdateTaskSuccessfullyUpdatesTask() throws Exception {
        TaskRequestDTO response = TestData.createTestTaskRequestA();
        response.setTitle("UPDATED TASK");

        TaskResponseDTO dto = taskMapper.toDTO(taskMapper.toEntity(response));
        String json = objectMapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/" + testTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testTask.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(dto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(dto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(dto.getPriority().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate").value(dto.getDueDate().toString()));
    }

    @Test
    public void testUpdateTaskReturns404WhenTaskNotExists() throws Exception {
        TaskRequestDTO dto = TestData.createTestTaskRequestA();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ---------------- DELETE ----------------
    @Test
    public void testDeleteTaskReturns204WhenExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + testTask.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteTaskReturns204WhenNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ---------------- EDGE CASES ----------------
    @Test
    public void testCreateTaskReturns400WhenInvalidInput() throws Exception {
        TaskRequestDTO dto = new TaskRequestDTO(); // empty DTO, invalid
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

