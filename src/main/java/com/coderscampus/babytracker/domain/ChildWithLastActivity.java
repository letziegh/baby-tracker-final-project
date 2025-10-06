package com.coderscampus.babytracker.domain;

import java.time.LocalDate;

public class ChildWithLastActivity {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String gender;
    private String age;
    private String lastActivityType;
    private String lastActivityTime;
    private String lastActivityDescription;

    public ChildWithLastActivity() {}

    public ChildWithLastActivity(Child child, Activity lastActivity) {
        this.id = child.getId();
        this.name = child.getName();
        this.birthdate = child.getBirthdate();
        this.gender = child.getGender();
        this.age = child.getAge();
        
        if (lastActivity != null) {
            this.lastActivityType = lastActivity.getActivityType();
            this.lastActivityTime = lastActivity.getStartTime();
            this.lastActivityDescription = formatLastActivity(lastActivity);
        } else {
            this.lastActivityType = null;
            this.lastActivityTime = null;
            this.lastActivityDescription = "No activities yet";
        }
    }

    private String formatLastActivity(Activity activity) {
        if (activity.getStartTime() != null) {
            return activity.getActivityType() + " - " + formatTimeAgo(activity.getStartTime());
        }
        return activity.getActivityType();
    }

    private String formatTimeAgo(String timeString) {
        // Simple time formatting - you might want to improve this
        // For now, just return the time as is
        return timeString;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLastActivityType() {
        return lastActivityType;
    }

    public void setLastActivityType(String lastActivityType) {
        this.lastActivityType = lastActivityType;
    }

    public String getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(String lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public String getLastActivityDescription() {
        return lastActivityDescription;
    }

    public void setLastActivityDescription(String lastActivityDescription) {
        this.lastActivityDescription = lastActivityDescription;
    }
}

