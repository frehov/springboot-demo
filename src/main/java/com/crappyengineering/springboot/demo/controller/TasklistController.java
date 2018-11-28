package com.crappyengineering.springboot.demo.controller;


import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.crappyengineering.springboot.demo.model.Tasklist;
import com.crappyengineering.springboot.demo.repository.TasklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tasklist")
public class TasklistController {

    private TasklistRepository tasklistRepository;

    public TasklistController(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @GetMapping
    public ResponseEntity getAllTaskLists() {
        return Optional.of(tasklistRepository.findAll())
                .filter(tasklists -> !tasklists.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(noContent()::build);
    }

    @PostMapping
    public ResponseEntity createTaskList(@RequestBody @Valid Tasklist tasklist) {
        return Optional.of(tasklistRepository.save(tasklist))
                .map(tasklist1 -> fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(tasklist1.getId())
                        .toUri())
                .map(uri -> created(uri).build())
                .orElseGet(badRequest()::build);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskListById(@PathVariable("id") long id) {
        return tasklistRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(notFound()::build);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTasklistById(@PathVariable("id") long id, @RequestBody Tasklist tasklist) {
        return tasklistRepository.findById(id)
                .map(tasklist1 -> {
                    tasklist.setTasks(tasklist1.getTasks());
                    tasklistRepository.save(tasklist);
                    return ok().build();
                })
                .orElseGet(notFound()::build);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTasklistById(@PathVariable("id") long id) {
        return tasklistRepository.findById(id)
                .map(tasklist -> {
                    tasklistRepository.delete(tasklist);
                    return ok().build();
                })
                .orElseGet(notFound()::build);
    }


}
