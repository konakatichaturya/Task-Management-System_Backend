package com.example.taskmanagement.dto;

public class ProjectRequest {

    private String name;
    private String description;
    private Long createdByUserId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }
}