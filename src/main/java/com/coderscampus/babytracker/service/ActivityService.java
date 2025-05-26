package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.repository.ActivityRepository;
import com.coderscampus.babytracker.repository.ChildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ChildRepository childRepository;

    public ActivityService(ActivityRepository activityRepository, ChildRepository childRepository) {
        this.activityRepository = activityRepository;
        this.childRepository = childRepository;
    }

    public Activity createActivity(Activity activity) {
        // Validate child exists
        Optional<Child> child = childRepository.findById(activity.getChild().getId());
        if (child.isEmpty()) {
            throw new IllegalArgumentException("Child not found");
        }
        activity.setChild(child.get());
        return activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByChildId(Long childId) {
        return activityRepository.findByChildId(childId);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public Activity updateActivity(Long id, Activity updatedActivity) {
        return activityRepository.findById(id)
                .map(existingActivity -> {
                    existingActivity.setActivityType(updatedActivity.getActivityType());
                    existingActivity.setStartTime(updatedActivity.getStartTime());
                    existingActivity.setEndTime(updatedActivity.getEndTime());
                    existingActivity.setNotes(updatedActivity.getNotes());
                    return activityRepository.save(existingActivity);
                })
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
    }
}
