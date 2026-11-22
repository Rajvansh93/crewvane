package com.crewvane.service;

import com.crewvane.entity.CampusEvent;
import com.crewvane.repository.CampusEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampusEventServiceTest {

    @Mock
    private CampusEventRepository campusEventRepository;

    @InjectMocks
    private CampusEventService campusEventService;

    @Test
    void getEventByIdReturnsEventWhenFound() {
        CampusEvent event = new CampusEvent();
        event.setId(1L);
        event.setTitle("IET Tech Fest");

        when(campusEventRepository.findById(1L))
                .thenReturn(Optional.of(event));

        Optional<CampusEvent> result =
                campusEventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals("IET Tech Fest", result.get().getTitle());
        verify(campusEventRepository).findById(1L);
    }
    @Test
    void createEvent_shouldSaveEvent() {
        CampusEvent event = new CampusEvent();
        event.setTitle("Test Event");
        event.setDescription("Testing event creation");
        event.setLocation("IET Lucknow");

        when(campusEventRepository.save(event)).thenReturn(event);

        CampusEvent result = campusEventService.createEvent(event);

        assertNotNull(result);
        assertEquals("Test Event", result.getTitle());
        verify(campusEventRepository).save(event);
    }
    @Test
    void getAllEvents_shouldReturnAllEvents() {
        CampusEvent event1 = new CampusEvent();
        event1.setTitle("Event 1");

        CampusEvent event2 = new CampusEvent();
        event2.setTitle("Event 2");

        List<CampusEvent> events = Arrays.asList(event1, event2);

        when(campusEventRepository.findAll()).thenReturn(events);

        List<CampusEvent> result = campusEventService.getAllEvents();

        assertEquals(2, result.size());
        verify(campusEventRepository).findAll();
    }
    @Test
    void updateEvent_shouldUpdateEvent() {
        CampusEvent event = new CampusEvent();
        event.setTitle("Updated Event");
        event.setDescription("Updated description");
        event.setLocation("IET Lucknow");

        when(campusEventRepository.save(event)).thenReturn(event);

        CampusEvent result = campusEventService.updateEvent(event);

        assertEquals("Updated Event", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        verify(campusEventRepository).save(event);
    }
    @Test
    void deleteEvent_shouldDeleteEvent() {
        Long id = 1L;

        doNothing().when(campusEventRepository).deleteById(id);

        campusEventService.deleteEvent(id);

        verify(campusEventRepository).deleteById(id);
    }
}