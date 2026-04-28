package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toResponseDto(task);
    }

    public List<TaskResponseDTO> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO create(TaskRequestDTO request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .build();
        return toResponseDto(taskRepository.save(task));
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        return toResponseDto(taskRepository.save(task));
    }

    public TaskResponseDTO updateStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(status);
        return toResponseDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    private TaskResponseDTO toResponseDto(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .build();
    }
}
