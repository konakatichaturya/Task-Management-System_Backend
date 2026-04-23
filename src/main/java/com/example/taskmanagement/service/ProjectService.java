package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.ProjectRequest;
import com.example.taskmanagement.entity.Project;
import com.example.taskmanagement.entity.ProjectMember;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.ProjectMemberRepository;
import com.example.taskmanagement.repository.ProjectRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public Project createProject(ProjectRequest request) {
        User creator = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCreatedBy(creator);

        Project savedProject = projectRepository.save(project);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(savedProject);
        projectMember.setUser(creator);

        projectMemberRepository.save(projectMember);

        return savedProject;
    }

    public String addMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)) {
            return "User already added to project";
        }

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(user);

        projectMemberRepository.save(member);
        return "Member added successfully";
    }
    public String removeMember(Long projectId, Long userId) {
        ProjectMember member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        projectMemberRepository.delete(member);
        return "Member removed successfully";
    }
}