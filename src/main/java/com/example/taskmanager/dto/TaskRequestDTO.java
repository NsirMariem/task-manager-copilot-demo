package com.example.taskmanager.dto;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Request payload for creating or updating a task")
public class TaskRequestDTO {

    @Schema(description = "Task title", example = "Finish report", required = true)
    @NotBlank(message = "Title must not be blank")
    private String title;

    @Schema(description = "Task description", example = "Write the quarterly report")
    private String description;

    @Schema(description = "Task status", example = "TODO", required = true)
    @NotNull(message = "Status is required")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "MEDIUM", required = true)
    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @Schema(description = "Due date for the task", example = "2026-05-15")
    private LocalDate dueDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
