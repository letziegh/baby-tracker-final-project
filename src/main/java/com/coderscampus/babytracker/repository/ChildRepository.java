package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {


}

