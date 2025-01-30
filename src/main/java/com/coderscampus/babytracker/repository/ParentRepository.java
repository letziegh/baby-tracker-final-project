package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository <Parent, Long> {
    Parent findBy(String email);
    Optional<Parent> findById(Long id);


}
