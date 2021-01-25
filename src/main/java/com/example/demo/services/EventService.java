package com.example.demo.services;

import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.EventRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.*;
import com.example.demo.entities.CommentEntity;
import com.example.demo.entities.EventEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.dto.Period.FUTURE;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;


    public EventService(EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void addNewEvent(NewEventForm newEventForm, String email) {

        final EventEntity eventEntity = new EventEntity();

        eventEntity.setTitle(newEventForm.getTitle());
        eventEntity.setBody(newEventForm.getBody());

        eventEntity.setStartDate(newEventForm.getStartDate());
        eventEntity.setEndDate(newEventForm.getEndDate());

        UserEntity userEntity = userRepository
                .findUserByEmail(email).orElse(null);

        eventEntity.setUserEntity(userEntity);

        eventRepository.save(eventEntity);
    }

    public List<EventShortInfo> getAllEventsSortedByNearest() {

        return eventRepository.findAll(Sort.by("endDate"))
                .stream()
                .filter(eventEntity -> eventEntity.getEndDate().isAfter(LocalDate.now().minusDays(1)))
                .map(eventEntity -> new EventShortInfo(eventEntity.getId(),
                        eventEntity.getTitle(),
                        eventEntity.getBody(),
                        eventEntity.getStartDateAsString(),
                        eventEntity.getEndDateAsString()))
                .collect(Collectors.toList());
    }

    public Optional<EventDetails> getSingleEventDetails(Long eventId) {
        return eventRepository.findById(eventId)
                .map(eventEntity -> new EventDetails(eventEntity.getId(),
                        eventEntity.getTitle(),
                        eventEntity.getBody(),
                        eventEntity.getStartDateAsString(),
                        eventEntity.getEndDateAsString()));
    }

    public List<EventShortInfo> getEventsByTitlePartAndPeriod(String titlePart, Period period) {
        if (FUTURE == period) {
            return eventRepository.findByTitleContaining(titlePart, Sort.by("endDate"))
                    .stream()
                    .filter(eventEntity -> eventEntity.getEndDate().isAfter(LocalDate.now()))
                    .map(eventEntity -> new EventShortInfo(eventEntity.getId(),
                            eventEntity.getTitle(),
                            eventEntity.getBody(),
                            eventEntity.getStartDateAsString(),
                            eventEntity.getEndDateAsString()))
                    .collect(Collectors.toList());

        } else if (Period.PRESENT_AND_FUTURE == period) {
            return eventRepository.findByTitleContaining(titlePart, Sort.by("endDate"))
                    .stream()
                    .filter(eventEntity -> eventEntity.getEndDate().isAfter(LocalDate.now().minusDays(1)))
                    .map(eventEntity -> new EventShortInfo(eventEntity.getId(),
                            eventEntity.getTitle(),
                            eventEntity.getBody(),
                            eventEntity.getStartDateAsString(),
                            eventEntity.getEndDateAsString()))
                    .collect(Collectors.toList());
        } else {
            return eventRepository.findByTitleContaining(titlePart, Sort.by("endDate"))
                    .stream()
                    .map(eventEntity -> new EventShortInfo(eventEntity.getId(),
                            eventEntity.getTitle(),
                            eventEntity.getBody(),
                            eventEntity.getStartDateAsString(),
                            eventEntity.getEndDateAsString()))
                    .collect(Collectors.toList());
        }
    }

    public Optional<EventDetails> getSingleEventInfo(Long eventId) {
        return eventRepository.findById(eventId)
                .map(eventEntity -> new EventDetails(eventEntity.getId(),
                        eventEntity.getTitle(),
                        eventEntity.getBody(),
                        eventEntity.getStartDateAsString(),
                        eventEntity.getEndDateAsString()));
    }

    @Transactional
    public void addNewComment(Long eventId, CommentFormDto commentFormDto, String email) {

        final EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        UserEntity userEntity = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        final CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentText(commentFormDto.getBody());
        commentEntity.setEventEntity(eventEntity);

        commentEntity.setUserEntity(userEntity);

        commentRepository.save(commentEntity);
    }

    public List<CommentDto> getCommentsForEvent(Long eventId) {
        return commentRepository
                .findByEventEntity_Id(eventId, Sort.by("added").descending())
                .stream()
                .map(commentEntity -> new CommentDto(commentEntity.getId(),
                        commentEntity.getCommentText(),
                        commentEntity.getAdded(),
                        commentEntity.getUserEntity().getEmail()))
                .collect(Collectors.toList());
    }

    public List<EventDetails> getFutureEvents() {
        return eventRepository.findAll()
                .stream()
                .filter(eventEntity -> eventEntity.getEndDate().isAfter(LocalDate.now()))
                .map(eventEntity -> new EventDetails(eventEntity.getId(),
                        eventEntity.getTitle(),
                        eventEntity.getBody(),
                        eventEntity.getStartDateAsString(),
                        eventEntity.getEndDateAsString()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void signUp(Long eventId, String email) {

        final Optional<EventEntity> eventEntity = eventRepository.findById(eventId);
        final Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        if (!eventEntity.isEmpty() && !userEntity.isEmpty()) {
            Set<UserEntity> participants = eventEntity.get().getParticipants();
            participants.add(userEntity.get());
            eventEntity.get().setParticipants(participants);
            eventRepository.save(eventEntity.get());
        }
    }

    @Transactional
    public List<UsersDto> getUsersForEvent(Long eventId) {
        return eventRepository.findById(eventId).get().getParticipants()
                .stream()
                .map(userEntity -> new UsersDto(userEntity.getEmail()))
                .collect(Collectors.toList());
    }
    @Transactional
    public List<String> getUsersEmailsForEvent(Long eventId) {
        return eventRepository.findById(eventId).get().getParticipants()
                .stream()
                .map(userEntity -> userEntity.getEmail())
                .collect(Collectors.toList());
    }

}


