package com.coderscampus.babytracker.controller;


import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.service.ChildService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }
//Add method for deleting a child
    @GetMapping("/children")
    public List<Child> getChildren() {
        return childService.getChildren();
    }

    @GetMapping("/parent/{parentId}")
    public List<Child> getChildrenByParent(@PathVariable Long parentId){
        return childService.getChildrenByParent(parentId);
    }


    @GetMapping("/child/{id}/activities")
    public Set<Activity> getChildActivities(@PathVariable Long id) {
        Child child = childService.getChild(id);
        return child.getActivities();
    }

    @PostMapping("/children")
    public Child addChild(@RequestBody Child child) {
        return childService.addChild(child);
    }
    @GetMapping("/child/{id}")
    public Child getChild(@PathVariable Long id) {
        return childService.getChild(id);
    }

    @DeleteMapping("/child/{id}")
    public void deleteChild(@PathVariable Long id) {
        childService.deleteChild(id);
    }


}
