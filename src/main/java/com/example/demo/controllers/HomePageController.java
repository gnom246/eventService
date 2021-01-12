package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String showHomePage(Model model, Principal principal){
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        return "homePage";
    }
}
