package com.example.demo.api;

import com.example.demo.dto.EventDetails;
import com.example.demo.services.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventRestController {

    EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    List<EventDetails> getAllFutureEvents(){
        return  eventService.getFutureEvents();
    }
}
