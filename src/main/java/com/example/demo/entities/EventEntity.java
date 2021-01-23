package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Entity
    @Table(name = "events")
    public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinTable(name = "users_evnts")
    private UserEntity userEntity;

    @ManyToMany
    @JoinTable(name = "participants_in_enevts")
    private Set<UserEntity> participants = new HashSet<>();

    public Set<UserEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserEntity> participants) {
        this.participants = participants;
    }

    public void setStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }
    public void setEndDate(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.endDate = LocalDate.parse(endDate, formatter);
    }
        public String getStartDateAsString() {
            return startDate.toString();
        }

        public String getEndDateAsString() {
            return endDate.toString();
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public UserEntity getUserEntity() {
            return userEntity;
        }

        public void setUserEntity(UserEntity userEntity) {
            this.userEntity = userEntity;
        }


    }
