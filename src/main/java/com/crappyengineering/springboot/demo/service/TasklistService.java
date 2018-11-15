package com.crappyengineering.springboot.demo.service;

import java.util.List;
import java.util.Optional;

import com.crappyengineering.springboot.demo.model.Tasklist;

public interface TasklistService {

    Optional<List<Tasklist>> getListByTitle(String title);

    Optional<List<Tasklist>> getAllLists();

    Optional<Tasklist> find(long id);

    Optional<Tasklist> save(Tasklist tasklist);
}
