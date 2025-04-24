package com.coderscampus.babytracker.controller;



import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.service.ParentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/parent")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }


//    @PostMapping("/update-username")
//    public Parent updateUsername(@RequestBody Map<String, String> request,
//                                 @AuthenticationPrincipal OAuth2User principal) {
//        String email = request.get("email");
//        String username = request.get("username");
//
//        if (email == null || email.isEmpty()) {
//            throw new RuntimeException("User email is missing from request.");
//        }
//
//        Parent parent = parentService.findByEmail(email);
//        if (parent != null) {
//            parent.setName(username);
//            return parentService.save(parent); // Save updated name
//            //return parentService.saveParent(email);
//        }
//        throw new RuntimeException("Parent not found in database.");
//    }
}

//    @PostMapping("/update-username")
//    public Parent updateUsername(@RequestBody UpdateUsernameRequestDTO request, // Use the DTO
//                                 @AuthenticationPrincipal OAuth2User principal) {
//        String email = request.getEmail(); // Access email from DTO
//        String username = request.getUsername(); // Access username from DTO
//
//        if (email == null || email.isEmpty()) {
//            throw new RuntimeException("User email is missing from request.");
//        }
//
//        Parent parent = parentService.findByEmail(email);
//        if (parent != null) {
//            parent.setName(username);
//            return parentService.save(parent);
//        }
//        throw new RuntimeException("Parent not found in database.");
//    }

