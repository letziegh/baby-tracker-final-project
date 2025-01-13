package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository <Parent, Long> {

}
