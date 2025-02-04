package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.service.ParentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final ParentService parentService;

    public HomeController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String email = principal.getAttribute("email");
            Parent parent = parentService.findByEmail(email);
            if (parent != null) {
                model.addAttribute("parent", parent); // Ensure 'parent' is set in the model
            } else {
                model.addAttribute("parent", new Parent("Unknown", "Unknown")); // Avoid null
            }
        }
        return "dashboard";
    }
}
    //old starter code below
//    @GetMapping("/")
//    public String home() {
//        return "Thank God you made it to the home page!";
//    }
//
//    @GetMapping("/secured")
//    public String secured() {
//        return "Hello, you are secured!";
//    }




