package com.example.taskmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Task getTask() {
        return task;
    }

    public User getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setUser(User user) {
        this.user = user;
    }
}