package com.example.taskmanager.dto;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Task response payload")
public class TaskResponseDTO {

    @Schema(description = "Task identifier", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Finish report")
    private String title;

    @Schema(description = "Task description", example = "Write the quarterly report")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "MEDIUM")
    private TaskPriority priority;

    @Schema(description = "Task creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Task due date", example = "2026-05-15")
    private LocalDate dueDate;
}
