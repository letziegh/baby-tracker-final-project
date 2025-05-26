package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<Activity>> getActivitiesByChildId(@PathVariable Long childId) {
        return ResponseEntity.ok(activityService.getActivitiesByChildId(childId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.updateActivity(id, activity));
    }

    //page for handling Tools: feeding tracker, diaper tracker, sleep tracker
    //add method for deleting activity record
}
