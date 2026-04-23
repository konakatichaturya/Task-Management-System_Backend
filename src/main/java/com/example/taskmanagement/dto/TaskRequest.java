package com.example.taskmanagement.dto;

import java.time.LocalDate;

public class TaskRequest {

    private String title;
    private String description;
    private String status;
    private Long assignedUserId;
    private LocalDate dueDate;
    private Long projectId;
    private Long actingUserId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getActingUserId() {
        return actingUserId;
    }
}