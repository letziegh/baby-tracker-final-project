package com.coderscampus.babytracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Thank God you made it to the home page!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, you are secured!";
    }



}
