package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> findAll() {
        String username = getCurrentUsername();
        return taskRepository.findByUserUsername(username).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findByIdAndUserUsername(id, getCurrentUsername())
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toResponseDto(task);
    }

    public List<TaskResponseDTO> findByStatus(TaskStatus status) {
        return taskRepository.findByStatusAndUserUsername(status, getCurrentUsername()).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByPriority(TaskPriority priority) {
        return taskRepository.findByPriorityAndUserUsername(priority, getCurrentUsername()).stream()
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
                .user(getCurrentUser())
                .build();
        return toResponseDto(taskRepository.save(task));
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO request) {
        Task task = findTaskForCurrentUser(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        return toResponseDto(taskRepository.save(task));
    }

    public TaskResponseDTO updateStatus(Long id, TaskStatus status) {
        Task task = findTaskForCurrentUser(id);
        task.setStatus(status);
        return toResponseDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        Task task = findTaskForCurrentUser(id);
        taskRepository.delete(task);
    }

    private Task findTaskForCurrentUser(Long id) {
        return taskRepository.findByIdAndUserUsername(id, getCurrentUsername())
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    private String getCurrentUsername() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new IllegalStateException("No authenticated user found");
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
