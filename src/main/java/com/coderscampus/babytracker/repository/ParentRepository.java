package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ParentRepository extends JpaRepository <Parent, Long> {
   Optional<Parent> findByEmail(String email);
    Optional<Parent> findById(Long id);


}
