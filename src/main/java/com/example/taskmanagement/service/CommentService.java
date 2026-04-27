package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.CommentRequest;
import com.example.taskmanagement.entity.Comment;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.CommentRepository;
import com.example.taskmanagement.repository.ProjectMemberRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public CommentService(CommentRepository commentRepository,
                          TaskRepository taskRepository,
                          UserRepository userRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }


    public Comment addComment(Long taskId, CommentRequest request) {

        // Get task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));


        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        boolean isMember = projectMemberRepository
                .existsByProjectIdAndUserId(task.getProject().getId(), user.getId());

        if (!isMember) {
            throw new RuntimeException("User is not a member of this project");
        }

        // Create comment
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setTask(task);
        comment.setUser(user);

        return commentRepository.save(comment);
    }


    public List<Comment> getComments(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}