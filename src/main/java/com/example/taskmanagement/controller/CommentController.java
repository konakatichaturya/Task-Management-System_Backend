package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.CommentRequest;
import com.example.taskmanagement.entity.Comment;
import com.example.taskmanagement.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comments")
    public Comment addComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentService.addComment(id, request);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        return commentService.getComments(id);
    }
}