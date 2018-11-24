package com.crappyengineering.springboot.demo.controller;


import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.crappyengineering.springboot.demo.model.Tasklist;
import com.crappyengineering.springboot.demo.repository.TasklistRepository;
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
@RequestMapping("/api/tasklist")
public class TasklistController {

    private final static Logger logger = LoggerFactory.getLogger(TasklistController.class);

    private TasklistRepository tasklistRepository;

    public TasklistController(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @GetMapping
    public ResponseEntity getAllTaskLists() {
        logger.warn("Entered getAllTasklists");

        List<Tasklist> tl = tasklistRepository.findAll();

        if (tl.isEmpty()) {
            return noContent().build();
        }

        return ok(tl);
    }

    @PostMapping
    public ResponseEntity createTaskList(@RequestBody @Valid Tasklist tasklist) {
        Tasklist tl = tasklistRepository.save(tasklist);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tl.getId()).toUri();

        return created(location).body(tl);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskListById(@PathVariable("id") Long id) {
        logger.warn("Got ID={}, looking up tasklist, found: {}", id, tasklistRepository.findById(id));

        Optional<Tasklist> tl = tasklistRepository.findById(id);

        if (!tl.isPresent()) {
            return notFound().build();
        }

        return ok(tl);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTasklistById(@PathVariable("id") long id, @RequestBody Tasklist tasklist) {

        Optional<Tasklist> tl = tasklistRepository.findById(id);

        if (!tl.isPresent()) {
            return notFound().build();
        } else {
            tasklist.setTasks(tl.get().getTasks());
        }

        return ok(tasklistRepository.save(tasklist));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTasklistById(@PathVariable("id") long id) {

        if (!tasklistRepository.existsById(id)) {
            return notFound().build();
        }

        tasklistRepository.deleteById(id);

        return ok().build();
    }


}
