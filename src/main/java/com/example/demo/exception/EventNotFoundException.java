package com.example.demo.exception;

public class EventNotFoundException extends RuntimeException{

    private Long eventId;

    public EventNotFoundException(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getMessage() {
        return String.format("Event not found: %d", eventId);
    }

}
