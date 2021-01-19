package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.entities.CommentEntity;
import com.example.demo.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/find-events-by-title-part")
    public String showEventsByTitlePart(@RequestParam String titlePart,
                                        @RequestParam String period,
                                        Model model) {

        List<EventShortInfo> searchedEvents = eventService.getEventsByTitlePartAndPeriod(titlePart, period);
//        Period period1;
        model.addAttribute("searchedEvents", searchedEvents);
        model.addAttribute("titlePart", titlePart);
        model.addAttribute("period", period);

        return "eventSearchingResult";
    }
    @GetMapping("/events/{eventId}")
    public String showSingleEventPage(@PathVariable Long eventId, Model model) {

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

//    @GetMapping("/events/{eventId}/comment/add")
//    public String commentAddingValidation(@PathVariable Long eventId, Model model) {
//        final CommentFormDto commentFormDto = new CommentFormDto();
//        model.addAttribute("commentFormDto", commentFormDto);
//        return "singleEventView";
//    }

    @PostMapping("/events/{eventId}/comment/add")
    public String handleNewCommentForm(@ModelAttribute @Valid CommentFormDto commentFormDto,
                                       BindingResult bindingResult,
                                       Principal principal,
                                       @PathVariable Long eventId) {
        if (bindingResult.hasErrors()) {
            return "redirect:/events/" + eventId;
        }
        if (principal != null) {
            String email = principal.getName();
            eventService.addNewComment(eventId, commentFormDto, email);

            return "redirect:/events/" + eventId;
        } return "redirect:/events/" + eventId;
    }
}
