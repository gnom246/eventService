package com.example.demo.services;

import com.example.demo.dao.EventRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.EventDetails;
import com.example.demo.dto.EventShortInfo;
import com.example.demo.dto.NewEventForm;
import com.example.demo.entities.EventEntity;
import com.example.demo.entities.RoleEntity;
import com.example.demo.entities.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void addNewEvent(NewEventForm newEventForm, String email) {

        final EventEntity eventEntity = new EventEntity();

        eventEntity.setTitle(newEventForm.getTitle());
        eventEntity.setBody(newEventForm.getBody());

        eventEntity.setStartDate(newEventForm.getStartDate());
        eventEntity.setEndDate(newEventForm.getEndDate());

        UserEntity userEntity = userRepository
                .findUserByEmail(email).orElse(null);

        eventEntity.setUserEntity(userEntity);

//        final String roleName = "OWNER";
//        RoleEntity roleEntity = roleRepository
//                .findRolesEntityByRoleName(roleName)
//                .orElseGet(() -> roleRepository.save(new RoleEntity(roleName)));
//
//        userEntity.addRole(roleEntity);
        eventRepository.save(eventEntity);
    }

    public List<EventShortInfo> getAllEventsSortedByNearest() {

        return eventRepository.findAll(Sort.by("endDate"))
                .stream()
                .filter(eventEntity -> eventEntity.getEndDate().isAfter(LocalDate.now()))
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
}
