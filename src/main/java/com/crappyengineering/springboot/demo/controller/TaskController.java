package com.crappyengineering.springboot.demo.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.Optional;

import com.crappyengineering.springboot.demo.model.Task;
import com.crappyengineering.springboot.demo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);


    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity getAllTasks() {
        return Optional.of(taskRepository.findAll())
                .filter(tasks -> !tasks.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(noContent()::build);
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task task) {
        return Optional.of(taskRepository.save(task))
                .map(task1 -> fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(task1.getId())
                        .toUri())
                .map(uri -> created(uri).build())
                .orElseGet(badRequest()::build);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable("id") long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(notFound()::build);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTaskById(@PathVariable("id") long id, @RequestBody Task task) {
        return taskRepository.findById(id)
                .map(task1 -> {
                    taskRepository.save(task);
                    return ok().build();
                })
                .orElseGet(notFound()::build);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskById(@PathVariable("id") long id) {
        return taskRepository.findById(id)
                .map(task1 -> {
                    taskRepository.delete(task1);
                    return ok().build();
                })
                .orElseGet(notFound()::build);
    }


}
