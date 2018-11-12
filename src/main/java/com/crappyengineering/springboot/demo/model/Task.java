package com.crappyengineering.springboot.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskList parentId;

    private String description;

    private TaskStatus status;

    public Task() {
    }

    public Task(TaskList parentId, String description, TaskStatus status) {
        this.parentId = parentId;
        this.description = description;
        this.status = status;
    }

    public TaskList getParentId() {
        return parentId;
    }

    public void setParentId(TaskList parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }
}
