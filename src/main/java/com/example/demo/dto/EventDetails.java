package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class EventDetails {
    private Long id;
    private String title;
    private String body;
    private String startDate;
    private String endDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", EventDetails.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .add("startDate='" + startDate + "'")
                .add("endDate='" + endDate + "'")
                .toString();
    }

    public EventDetails() {
    }

    public EventDetails(Long id, String title, String body, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
