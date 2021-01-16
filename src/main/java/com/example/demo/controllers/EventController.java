package com.example.demo.controllers;

import com.example.demo.dto.EventDetails;
import com.example.demo.dto.NewEventForm;
import com.example.demo.dto.NewUserFormDto;
import com.example.demo.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class EventController {
    EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/addEvent")
    public String showEventForm(Model model) {
        final NewEventForm newEventForm = new NewEventForm();
        model.addAttribute("newEventForm", newEventForm);
        return "eventForm";
    }

    @PostMapping("/addEvent")
    public String submitEventForm(@ModelAttribute @Valid NewEventForm newEventForm,
                                     BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()){
            return "eventForm";
        }
        if (principal != null) {
            String email = principal.getName();
            eventService.addNewEvent(newEventForm, email);
            return "redirect:/";
        } return "redirect:/";
    }
    @GetMapping("/events/{eventId}")
    public String showSingleEventPage(@PathVariable Long eventId, Model model) {

        final Optional<EventDetails> eventDetails = eventService.getSingleEventDetails(eventId);

        if (eventDetails.isEmpty()) {
            return "noEventFound";
        }

        model.addAttribute("event", eventDetails.get());

        return "singleEventView";
    }

}
