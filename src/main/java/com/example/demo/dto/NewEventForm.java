package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Setter
@Getter
public class NewEventForm {
    public NewEventForm() {
    }

    @NotBlank(message = "Title should not be empty and should not have only white spaces")
    private String title;

    @Size(min = 20, message = "Description should have min 20 letters")
    private String body;

    @NotEmpty(message = "Please choose start date")
    private String startDate;

    @NotEmpty(message = "Please choose end date")
    private String endDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", NewEventForm.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
