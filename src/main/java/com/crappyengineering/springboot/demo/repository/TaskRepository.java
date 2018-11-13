package com.crappyengineering.springboot.demo.repository;

import java.util.List;
import java.util.Optional;

import com.crappyengineering.springboot.demo.model.Task;
import com.crappyengineering.springboot.demo.model.Tasklist;
import com.crappyengineering.springboot.demo.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public Optional<List<Task>> findAllByStatusAndParentId(TaskStatus status, Tasklist parent);

    public Optional<Task> findAllById(long id);

    public long countByStatus(TaskStatus status);

}
