package com.crappyengineering.springboot.demo.repository;

import java.util.List;
import java.util.Optional;

import com.crappyengineering.springboot.demo.model.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasklistRepository extends JpaRepository<Tasklist, Long> {

    public Optional<List<Tasklist>> findByTitle(String title);

    public Optional<List<Tasklist>> findByOwnerStartsWith(String prefix);

    public long countDistinctByOwner(String owner);

}
