package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.entity.Project;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.TaskStatus;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.ProjectMemberRepository;
import com.example.taskmanagement.repository.ProjectRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ProjectRepository projectRepository,
                       ProjectMemberRepository projectMemberRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public Task createTask(TaskRequest request) {
        User assignedUser = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projectMemberRepository.existsByProjectIdAndUserId(project.getId(), assignedUser.getId())) {
            throw new RuntimeException("Assigned user is not a member of this project");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.valueOf(request.getStatus().toUpperCase()));
        task.setDueDate(request.getDueDate());
        task.setAssignedUser(assignedUser);
        task.setProject(project);

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, TaskRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (request.getActingUserId() == null) {
            throw new RuntimeException("actingUserId is required");
        }

        User actingUser = userRepository.findById(request.getActingUserId())
                .orElseThrow(() -> new RuntimeException("Acting user not found"));

        boolean isAdmin = actingUser.getRole() != null &&
                actingUser.getRole() == Role.ADMIN;

        boolean isAssignedUser = task.getAssignedUser() != null &&
                task.getAssignedUser().getId().equals(actingUser.getId());

        if (!isAdmin && !isAssignedUser) {
            throw new RuntimeException("Only assigned user or admin can update task");
        }

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getStatus() != null) {
            TaskStatus current = task.getStatus();
            TaskStatus next = TaskStatus.valueOf(request.getStatus().toUpperCase());

            if (current == TaskStatus.DONE && next != TaskStatus.DONE) {
                throw new RuntimeException("Invalid status transition from DONE");
            }

            task.setStatus(next);
        }

        if (request.getAssignedUserId() != null) {
            User newAssignedUser = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            if (!projectMemberRepository.existsByProjectIdAndUserId(
                    task.getProject().getId(),
                    newAssignedUser.getId())) {
                throw new RuntimeException("Assigned user is not a member of this project");
            }

            task.setAssignedUser(newAssignedUser);
        }

        return taskRepository.save(task);
    }

    public List<Task> getTasks(Long projectId, Long userId, String status, Long actingUserId) {

        if (projectId != null && actingUserId != null) {
            boolean isMember = projectMemberRepository.existsByProjectIdAndUserId(projectId, actingUserId);

            if (!isMember) {
                throw new RuntimeException("Only project members can access tasks of this project");
            }
        }

        if (projectId != null && status != null) {
            return taskRepository.findByProjectIdAndStatus(projectId, TaskStatus.valueOf(status.toUpperCase()));
        }

        if (projectId != null) {
            return taskRepository.findByProjectId(projectId);
        }

        if (userId != null) {
            return taskRepository.findByAssignedUserId(userId);
        }

        if (status != null) {
            return taskRepository.findByStatus(TaskStatus.valueOf(status.toUpperCase()));
        }

        return taskRepository.findAll();
    }
}