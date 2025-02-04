package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.repository.ActivityRepository;

public class ActivityService {

    private ActivityRepository activityRepo;

    public ActivityService(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

//    public void createActivity (String activity) {
//        Activity newActivity = new Activity();
//        newActivity.setName(activity);
//        activityRepo.save(newActivity);
//    }

    public void trackActivity(String activity) {
    }

    public void deleteById(String id) {
        activityRepo.deleteById(Long.parseLong(id));
    }
}
