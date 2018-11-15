package com.crappyengineering.springboot.demo.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "tasklist")
public class Tasklist {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "parentId",
            fetch = FetchType.LAZY
    )
    @JsonInclude(NON_EMPTY)
    private List<Task> tasks;

    private String owner;

    private String title;

    public Tasklist() {
    }

    public Tasklist(String owner, String title) {
        this.owner = owner;
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Tasklist{" +
                "id=" + id +
                ", tasks=" + tasks +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
