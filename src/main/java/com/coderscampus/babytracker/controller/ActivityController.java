package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.service.ActivityService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @DeleteMapping("/activity/{id}")
    public void deleteActivityRecord(@PathVariable String id) {
        activityService.deleteById(id);

    }



    //page for handling Tools: feeding tracker, diaper tracker, sleep tracker
    //add method for deleting activity record
}
