package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.domain.ChildWithLastActivity;
import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.service.ChildService;
import com.coderscampus.babytracker.service.ParentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/children")
public class ChildController {
    private static final Logger logger = LoggerFactory.getLogger(ChildController.class);
    private final ChildService childService;
    private final ParentService parentService;

    public ChildController(ChildService childService, ParentService parentService) {
        this.childService = childService;
        this.parentService = parentService;
    }



    @GetMapping("/{id}/edit")
    public String editChildPage(@PathVariable Long id, Model model) {
        logger.info("Rendering edit page for child ID: {}", id);
        Child child = childService.getChild(id);
        if (child == null) {
            logger.error("Child not found with ID: {}", id);
            return "redirect:/dashboard";
        }
        logger.info("Found child: {} (ID: {})", child.getName(), child.getId());
        model.addAttribute("child", child);
        return "edit-child";
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

    @GetMapping("/parent/{parentId}/with-last-activity")
    @ResponseBody
    public List<ChildWithLastActivity> getChildrenByParentWithLastActivity(@PathVariable Long parentId) {
        return childService.getChildrenByParentWithLastActivity(parentId);
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
