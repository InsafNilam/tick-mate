package com.tickmate.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String showHomePage() {
        // Return the name of the HTML file (index.html)
        return "index";
    }
}
