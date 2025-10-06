package com.tickmate.backend.repository;

import com.tickmate.backend.TestData;
import com.tickmate.backend.domain.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryIntegrationTest {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskRepositoryIntegrationTest(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    public void testThatTaskCanBeCreatedAndRetrieved() {
        TaskEntity result = TestData.createTestTaskEntityA();
        result.setId(null);

        TaskEntity task = taskRepository.save(result);

        Optional<TaskEntity> results = taskRepository.findById(task.getId());
        assertThat(results).isPresent();
        results.ifPresent(entity ->
                assertThat(entity).usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(task)
        );
    }

    @Test
    public void testThatMultipleTasksCanBeCreatedAndRetrieved() {
        TaskEntity taskA = TestData.createTestTaskEntityA();
        taskA.setId(null);

        TaskEntity taskB = TestData.createTestTaskEntityB();
        taskB.setId(null);

        TaskEntity taskC = TestData.createTestTaskEntityC();
        taskC.setId(null);

        taskRepository.save(taskA);
        taskRepository.save(taskB);
        taskRepository.save(taskC);

        Iterable<TaskEntity> allTasks = taskRepository.findAll();
        assertThat(allTasks).isNotNull();

        assertThat(allTasks)
                .hasSize(3)
                .extracting(TaskEntity::getTitle, TaskEntity::getDescription, TaskEntity::getStatus, TaskEntity::getPriority)
                .containsExactlyInAnyOrder(
                        tuple(taskA.getTitle(), taskA.getDescription(), taskA.getStatus(), taskA.getPriority()),
                        tuple(taskB.getTitle(), taskB.getDescription(), taskB.getStatus(), taskB.getPriority()),
                        tuple(taskC.getTitle(), taskC.getDescription(), taskC.getStatus(), taskC.getPriority())
                );
    }

    @Test
    public void testThatTaskCanBeUpdated() {
        TaskEntity task = TestData.createTestTaskEntityA();
        task.setId(null);

        taskRepository.save(task);

        task.setTitle("Updated Title");
        task.setStatus(TaskEntity.Status.COMPLETED);

        taskRepository.save(task);

        Optional<TaskEntity> result = taskRepository.findById(task.getId());
        assertThat(result).isPresent();
        result.ifPresent(entity ->
                assertThat(entity)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(task)
        );
    }

    @Test
    public void testThatTaskCanBeDeleted() {
        TaskEntity task = TestData.createTestTaskEntityA();
        task.setId(null);

        taskRepository.save(task);
        taskRepository.deleteById(task.getId());

        Optional<TaskEntity> result = taskRepository.findById(task.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatNonExistentTaskReturnsEmpty() {
        Optional<TaskEntity> result = taskRepository.findById(java.util.UUID.randomUUID());
        assertThat(result).isEmpty();
    }
}
