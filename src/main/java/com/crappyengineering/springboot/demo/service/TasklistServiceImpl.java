package com.crappyengineering.springboot.demo.service;

import java.util.List;
import java.util.Optional;

import com.crappyengineering.springboot.demo.model.Tasklist;
import com.crappyengineering.springboot.demo.repository.TasklistRepository;
import org.springframework.stereotype.Service;

@Service
public class TasklistServiceImpl implements TasklistService {

    private TasklistRepository tasklistRepository;

    public TasklistServiceImpl(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public Optional<List<Tasklist>> getListByTitle(String title) {
        return tasklistRepository.findByTitle(title);
    }

    @Override
    public Optional<List<Tasklist>> getAllLists() {
        return Optional.ofNullable(tasklistRepository.findAll());
    }

    @Override
    public Optional<Tasklist> find(long id) {
        return tasklistRepository.findById(id);
    }

    @Override
    public Optional<Tasklist> save(Tasklist tasklist) {
        return Optional.of(tasklistRepository.save(tasklist));
    }
}
