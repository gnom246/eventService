package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.entities.CommentEntity;
import com.example.demo.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class EventController {
    EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/addEvent")
    public String showEventForm(Model model,
                                Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
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

@GetMapping("/find-events")
public String showEventsByTitlePartAndPeriod(Model model,
                                             @RequestParam String titlePart,
                                             @RequestParam String selectedPeriod,
                                             Principal principal) {
model.addAttribute("titlePart", titlePart);
model.addAttribute("selectedPeriod", selectedPeriod);

    if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        List<EventShortInfo> searchedEvents = eventService.getEventsByTitlePartAndPeriod(titlePart, selectedPeriod);
        model.addAttribute("searchedEvents", searchedEvents);

    return "eventSearchingResult1";
}
    @GetMapping("/events/{eventId}")
    public String showSingleEventPage(@PathVariable Long eventId, Model model,
                                      Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        final Optional<EventDetails> eventDetailsOptional = eventService.getSingleEventInfo(eventId);

        if (eventDetailsOptional.isEmpty()) {
            return "noEventFound";
        }
        model.addAttribute("event", eventDetailsOptional.get());

        final CommentFormDto commentFormDto = new CommentFormDto();
        model.addAttribute("commentFormDto", commentFormDto);

       final List<CommentDto> comments = eventService.getCommentsForEvent(eventId);
        model.addAttribute("comments", comments);

        return "singleEventView";
    }


    @PostMapping("/events/{eventId}/comment/add")
    public String handleNewCommentForm(@ModelAttribute @Valid CommentFormDto commentFormDto,
                                       BindingResult bindingResult,
                                       Principal principal,
                                       @PathVariable Long eventId,
                                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("msg", "Description should have max 500 characters");
            return "redirect:/events/" + eventId;
        }
        if (principal != null) {
            String email = principal.getName();
            eventService.addNewComment(eventId, commentFormDto, email);
            return "redirect:/events/" + eventId;
        } return "redirect:/events/" + eventId;
    }
}
