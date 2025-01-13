package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository <Activity, Long> {


}
