package com.tickmate.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping("/")
    public String showHomePage() {
        // Return the name of the HTML file (index.html)
        return "index";
    }
}
