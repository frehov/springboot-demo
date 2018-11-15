package com.crappyengineering.springboot.demo.controller;


import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.crappyengineering.springboot.demo.model.Tasklist;
import com.crappyengineering.springboot.demo.repository.TasklistRepository;
import com.crappyengineering.springboot.demo.service.TasklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/tasklist")
public class TasklistController {

    private final static Logger logger = LoggerFactory.getLogger(TasklistRepository.class);

    private TasklistService tasklistService;

    public TasklistController(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    @GetMapping
    public ResponseEntity getAllTaskLists() {
        logger.warn("Entered getAllTasklists");

        Optional<List<Tasklist>> tl = tasklistService.getAllLists();

        if (!tl.isPresent()) {
            return noContent().build();
        }

        return ok(tasklistService.getAllLists());
    }

    @PostMapping
    public ResponseEntity createTaskList(@RequestBody @Valid Tasklist tasklist) {
        Tasklist tl = tasklistService.save(tasklist).orElseThrow(InputMismatchException::new);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tl.getId()).toUri();

        return created(location).body(tl);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskListById(@PathVariable("id") Long id) {
        logger.warn("Got ID={}, looking up tasklist, found: {}", id, tasklistService.find(id));

        Optional<Tasklist> tl = tasklistService.find(id);

        if (!tl.isPresent()) {
            return notFound().build();
        }

        return ok(tasklistService.find(id));
    }

}
