package com.coderscampus.babytracker.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get the error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
            
            // Customize error message based on status code
            if (statusCode == 404) {
                model.addAttribute("message", "The page you're looking for doesn't exist.");
            } else if (statusCode == 403) {
                model.addAttribute("message", "You don't have permission to access this resource.");
            } else if (statusCode == 500) {
                model.addAttribute("message", "Something went wrong on our end. Please try again later.");
            } else {
                model.addAttribute("message", message != null ? message.toString() : "An unexpected error occurred.");
            }
        } else {
            model.addAttribute("status", "Unknown");
            model.addAttribute("message", "An unexpected error occurred.");
        }
        
        // Return the error view
        return "error";
    }
} 