package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.ProjectRequest;
import com.example.taskmanagement.dto.AddMemberRequest;
import com.example.taskmanagement.entity.Project;
import com.example.taskmanagement.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:5174")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(@RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    @PostMapping("/{id}/members")
    public String addMember(@PathVariable Long id, @RequestBody AddMemberRequest request) {
        return projectService.addMember(id, request.getUserId());
    }
    @DeleteMapping("/{id}/members/{userId}")
    public String removeMember(@PathVariable Long id, @PathVariable Long userId) {
        return projectService.removeMember(id, userId);
    }
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    public Project getProjectById(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return projectService.getProjectByIdForMember(id, userId);
    }
}