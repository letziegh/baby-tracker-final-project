package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.service.ChildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/children")
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/{id}/activities")
    public String getChildActivities(@PathVariable Long id, Model model) {
        Child child = childService.getChild(id);
        if (child == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("child", child);
        return "child-activities";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Child getChild(@PathVariable Long id) {
        return childService.getChild(id);
    }

    @GetMapping("/parent/{parentId}")
    @ResponseBody
    public List<Child> getChildrenByParent(@PathVariable Long parentId) {
        return childService.getChildrenByParent(parentId);
    }

    @PostMapping
    @ResponseBody
    public Child addChild(@RequestBody Child child) {
        return childService.addChild(child);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteChild(@PathVariable Long id) {
        childService.deleteChild(id);
    }
}
