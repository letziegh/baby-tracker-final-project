package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.service.ChildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ActivityPageController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityPageController.class);
    private final ChildService childService;

    public ActivityPageController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/add-activity/{childId}")
    public String addActivityPage(@PathVariable Long childId, Model model) {
        logger.info("Rendering add activity page for child ID: {}", childId);
        Child child = childService.getChild(childId);
        if (child == null) {
            logger.error("Child not found with ID: {}", childId);
            return "redirect:/dashboard";
        }
        logger.info("Found child: {} (ID: {})", child.getName(), child.getId());
        model.addAttribute("child", child);
        return "add-activity";
    }
}
