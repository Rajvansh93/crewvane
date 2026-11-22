package com.crewvane;

import com.crewvane.controller.CampusEventController;
import com.crewvane.entity.CampusEvent;
import com.crewvane.service.CampusEventService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampusEventController.class)
class CampusEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CampusEventService campusEventService;

    @Test
    void createEvent_shouldReturn201() throws Exception {

        CampusEvent event = new CampusEvent();
        event.setTitle("Test Event");
        event.setDescription("Testing integration");
        event.setLocation("IET Lucknow");

        when(campusEventService.createEvent(any(CampusEvent.class)))
                .thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Test Event",
                                  "description": "Testing integration",
                                  "location": "IET Lucknow"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.location").value("IET Lucknow"));
    }
    @Test
    void getAllEvents_shouldReturnEvents() throws Exception {
        CampusEvent event = new CampusEvent();
        event.setTitle("Test Event");
        event.setDescription("Testing GET endpoint");
        event.setLocation("IET Lucknow");

        when(campusEventService.getAllEvents())
                .thenReturn(java.util.List.of(event));

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Event"))
                .andExpect(jsonPath("$[0].location").value("IET Lucknow"));
    }
    @Test
    void getEventById_shouldReturnEvent() throws Exception {
        CampusEvent event = new CampusEvent();
        event.setTitle("Test Event");
        event.setDescription("Testing GET by ID");
        event.setLocation("IET Lucknow");

        when(campusEventService.getEventById(1L))
                .thenReturn(java.util.Optional.of(event));

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.location").value("IET Lucknow"));
    }
    @Test
    void getEventById_whenNotFound_shouldReturn404() throws Exception {

        when(campusEventService.getEventById(999L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/api/events/999"))
                .andExpect(status().isNotFound());
    }
    @Test
    void updateEvent_shouldReturnUpdatedEvent() throws Exception {
        CampusEvent existingEvent = new CampusEvent();
        existingEvent.setId(1L);
        existingEvent.setTitle("Old Event");
        existingEvent.setDescription("Old description");
        existingEvent.setLocation("Old location");

        CampusEvent updatedEvent = new CampusEvent();
        updatedEvent.setId(1L);
        updatedEvent.setTitle("Updated Event");
        updatedEvent.setDescription("Updated description");
        updatedEvent.setLocation("IET Lucknow");

        when(campusEventService.getEventById(1L))
                .thenReturn(java.util.Optional.of(existingEvent));

        when(campusEventService.updateEvent(any(CampusEvent.class)))
                .thenReturn(updatedEvent);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/api/events/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "title": "Updated Event",
                              "description": "Updated description",
                              "location": "IET Lucknow"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Event"))
                .andExpect(jsonPath("$.location").value("IET Lucknow"));
    }
    @Test
    void deleteEvent_shouldReturn204() throws Exception {
        CampusEvent event = new CampusEvent();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setDescription("Test description");
        event.setLocation("IET Lucknow");

        when(campusEventService.getEventById(1L))
                .thenReturn(java.util.Optional.of(event));

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/api/events/1"))
                .andExpect(status().isNoContent());

        org.mockito.Mockito.verify(campusEventService).deleteEvent(1L);
    }
    @Test
    void createEvent_whenTitleIsBlank_shouldReturn400() throws Exception {
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "title": "",
                              "description": "Test description",
                              "location": "IET Lucknow"
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Title is required"));
    }
}
