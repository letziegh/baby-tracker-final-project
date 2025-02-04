package com.coderscampus.babytracker.repository;

import com.coderscampus.babytracker.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ActivityRepository extends JpaRepository <Activity, Long> {

    List<Activity> findByChildId(Long childId);







}
