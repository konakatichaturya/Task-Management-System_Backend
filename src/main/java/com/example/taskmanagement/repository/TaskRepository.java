package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignedUserId(Long userId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);

    Page<Task> findAll(Pageable pageable);
}