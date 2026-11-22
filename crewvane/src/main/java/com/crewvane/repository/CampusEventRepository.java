package com.crewvane.repository;

import com.crewvane.entity.CampusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CampusEventRepository
        extends JpaRepository<CampusEvent, Long> {
    List<CampusEvent> findByTitleContainingIgnoreCase(String title);

    List<CampusEvent> findByLocationContainingIgnoreCase(String location);

}
