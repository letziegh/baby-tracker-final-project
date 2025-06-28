package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.service.ChildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/children")
public class ChildController {
    private static final Logger logger = LoggerFactory.getLogger(ChildController.class);
    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/{id}/activities")
    public String getChildActivities(@PathVariable Long id, Model model) {
        logger.info("Rendering activities page for child ID: {}", id);
        Child child = childService.getChild(id);
        if (child == null) {
            logger.error("Child not found with ID: {}", id);
            return "redirect:/dashboard";
        }
        logger.info("Found child: {} (ID: {})", child.getName(), child.getId());
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

    @PutMapping("/{id}")
    @ResponseBody
    public Child updateChild(@PathVariable Long id, @RequestBody Child child) {
        return childService.updateChild(id, child);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteChild(@PathVariable Long id) {
        childService.deleteChild(id);
    }
}
