package com.example.demo.controllers;

import com.example.demo.dto.EventShortInfo;
import com.example.demo.dto.Period;
import com.example.demo.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomePageController {
    final EventService eventService;

    public HomePageController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showHomePage(Model model, Principal principal){
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }

        List<EventShortInfo> events = eventService.getAllEventsSortedByNearest();
        model.addAttribute("events", events);

        return "homePage";
    }

}
