package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        logger.info("Creating new activity: {}", activity);
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<Activity>> getActivitiesByChildId(@PathVariable Long childId) {
        logger.info("Fetching activities for childId: {}", childId);
        List<Activity> activities = activityService.getActivitiesByChildId(childId);
        logger.info("Found {} activities for childId: {}", activities.size(), childId);
        return ResponseEntity.ok(activities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        logger.info("Deleting activity with id: {}", id);
        activityService.deleteActivity(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        logger.info("Updating activity with id: {}", id);
        return ResponseEntity.ok(activityService.updateActivity(id, activity));
    }

    
}
