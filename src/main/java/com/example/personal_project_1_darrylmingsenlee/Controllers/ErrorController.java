package com.example.personal_project_1_darrylmingsenlee.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    // Handle root path (/)
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/home";
    }

    // Catch-all for unknown paths
    @RequestMapping("/**")
    public String handleUnknownPaths() {
        return "redirect:/home";
    }
}
