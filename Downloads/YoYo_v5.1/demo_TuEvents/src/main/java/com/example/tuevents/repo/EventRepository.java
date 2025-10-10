package com.example.tuevents.repo;

import com.example.tuevents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository 
         extends JpaRepository<Event, Long> ,
         JpaSpecificationExecutor<Event> {
    // เพิ่มเมธอดค้นหาเพิ่มได้ เช่น Optional<Event> findByTitle(String title);
}