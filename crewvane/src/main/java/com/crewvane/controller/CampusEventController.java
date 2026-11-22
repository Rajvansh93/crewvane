package com.crewvane.controller;

import com.crewvane.dto.CampusEventResponse;
import com.crewvane.entity.CampusEvent;
import com.crewvane.exception.EventNotFoundException;
import com.crewvane.service.CampusEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.crewvane.dto.CampusEventRequest;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.crewvane.dto.CampusEventResponse;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class CampusEventController {

    private final CampusEventService campusEventService;

    public CampusEventController(CampusEventService campusEventService) {
        this.campusEventService = campusEventService;
    }

    @PostMapping
    public ResponseEntity<CampusEventResponse> createEvent(
            @Valid @RequestBody CampusEventRequest request) {

        CampusEvent event = new CampusEvent();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());

        CampusEvent createdEvent =
                campusEventService.createEvent(event);

        CampusEventResponse response = new CampusEventResponse(
                createdEvent.getId(),
                createdEvent.getTitle(),
                createdEvent.getDescription(),
                createdEvent.getLocation()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<CampusEventResponse> getAllEvents() {
        return campusEventService.getAllEvents()
                .stream()
                .map(event -> new CampusEventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getLocation()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampusEventResponse> getEventById(
            @PathVariable Long id) {

        CampusEvent event = campusEventService.getEventById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found"));

        CampusEventResponse response = new CampusEventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation()
        );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CampusEventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody CampusEventRequest request) {

        CampusEvent existingEvent = campusEventService
                .getEventById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found"));

        existingEvent.setTitle(request.getTitle());
        existingEvent.setDescription(request.getDescription());
        existingEvent.setLocation(request.getLocation());

        CampusEvent updatedEvent =
                campusEventService.updateEvent(existingEvent);

        CampusEventResponse response = new CampusEventResponse(
                updatedEvent.getId(),
                updatedEvent.getTitle(),
                updatedEvent.getDescription(),
                updatedEvent.getLocation()
        );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {

        campusEventService.getEventById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found"));

        campusEventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search/title")
    public List<CampusEventResponse> searchByTitle(
            @RequestParam String title) {

        return campusEventService.searchByTitle(title)
                .stream()
                .map(event -> new CampusEventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getLocation()))
                .toList();
    }

    @GetMapping("/search/location")
    public List<CampusEventResponse> searchByLocation(
            @RequestParam String location) {

        return campusEventService.searchByLocation(location)
                .stream()
                .map(event -> new CampusEventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getLocation()))
                .toList();
    }
}
