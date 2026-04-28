package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task highPriorityTask;
    private Task mediumPriorityTask;

    @BeforeEach
    void setUp() {
        highPriorityTask = Task.builder()
                .id(1L)
                .title("High priority task")
                .description("Important")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.HIGH)
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDate.now().plusDays(3))
                .build();

        mediumPriorityTask = Task.builder()
                .id(2L)
                .title("Medium priority task")
                .description("Normal")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDate.now().plusDays(5))
                .build();
    }

    @Test
    void findByPriority_shouldReturnTasksWithMatchingPriority() {
        when(taskRepository.findByPriority(TaskPriority.HIGH)).thenReturn(List.of(highPriorityTask));

        List<TaskResponseDTO> results = taskService.findByPriority(TaskPriority.HIGH);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(0).getPriority()).isEqualTo(TaskPriority.HIGH);
        assertThat(results.get(0).getTitle()).isEqualTo("High priority task");
    }
}
