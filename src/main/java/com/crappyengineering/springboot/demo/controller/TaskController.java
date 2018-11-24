package com.crappyengineering.springboot.demo.controller;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            return noContent().build();
        }

        return ok(tasks);
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task task) {

        logger.info("{}", task);

        Task t = taskRepository.save(task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(t.getId()).toUri();

        return created(location).body(taskRepository.save(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable("id") long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (!task.isPresent()) {
            notFound();
        }
        return ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTaskById(@PathVariable("id") long id, @RequestBody Task task) {
        if (!taskRepository.existsById(id)) {
            return notFound().build();
        }

        return ok(taskRepository.save(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskById(@PathVariable("id") long id) {

        if (!taskRepository.existsById(id)) {
            return notFound().build();
        }

        taskRepository.deleteById(id);

        return ok().build();
    }


}
