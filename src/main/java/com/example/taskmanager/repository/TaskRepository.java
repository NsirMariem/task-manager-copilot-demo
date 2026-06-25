package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByUserUsername(String username);

    List<Task> findByStatusAndUserUsername(TaskStatus status, String username);

    List<Task> findByPriorityAndUserUsername(TaskPriority priority, String username);

    Optional<Task> findByIdAndUserUsername(Long id, String username);

    boolean existsByIdAndUserUsername(Long id, String username);
}
