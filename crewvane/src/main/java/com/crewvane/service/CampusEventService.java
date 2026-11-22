package com.crewvane.service;

import com.crewvane.entity.CampusEvent;
import com.crewvane.repository.CampusEventRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class CampusEventService {

    private final CampusEventRepository campusEventRepository;

    public CampusEventService(CampusEventRepository campusEventRepository) {
        this.campusEventRepository = campusEventRepository;
    }

    public CampusEvent createEvent(CampusEvent event) {
        return campusEventRepository.save(event);
    }

    public List<CampusEvent> getAllEvents() {
        return campusEventRepository.findAll();
    }
    public Optional<CampusEvent> getEventById(Long id){
        return campusEventRepository.findById(id);
    }
    public CampusEvent updateEvent(CampusEvent event) {
        return campusEventRepository.save(event);
    }
    public void deleteEvent(Long id) {
        campusEventRepository.deleteById(id);
    }
    public List<CampusEvent> searchByTitle(String title) {
        return campusEventRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<CampusEvent> searchByLocation(String location) {
        return campusEventRepository.findByLocationContainingIgnoreCase(location);
    }
}
