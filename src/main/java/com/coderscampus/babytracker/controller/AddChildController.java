package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.service.ParentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddChildController {

    private final ParentService parentService;

    public AddChildController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/add-child")
    public String addChildPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "redirect:/";
        }
        
        // Get parent information for the form
        String email = null;
        if (principal.getAttribute("email") != null) {
            email = principal.getAttribute("email");
        } else if (principal.getAttribute("login") != null) {
            email = principal.getAttribute("login") + "@github.com";
        }
        
        if (email == null) {
            email = principal.getName();
        }
        
        // Find parent in database
        Parent parent = parentService.findByEmail(email);
        
        if (parent == null) {
            return "redirect:/dashboard";
        }
        
        model.addAttribute("parent", parent);
        return "add-child";
    }
}
