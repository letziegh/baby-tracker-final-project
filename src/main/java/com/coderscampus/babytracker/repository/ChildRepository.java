package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByParentId(Long parentId);

    Optional<Child> findById(Long id);
}

