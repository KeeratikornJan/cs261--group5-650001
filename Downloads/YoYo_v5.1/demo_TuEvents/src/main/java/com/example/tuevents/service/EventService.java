package com.example.tuevents.service;

import com.example.tuevents.model.Event;
import com.example.tuevents.repo.EventRepository;
import com.example.tuevents.repo.EventSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }
    

    public List<Event> getAll() { return repo.findAll(); }
    public Event create(Event e) { return repo.save(e); }
    public Event update(Long id, Event e) { e.setId(id); return repo.save(e); }
    public void delete(Long id) { repo.deleteById(id); }
    
    public Page<Event> search( //เพิ่ม String search
            Integer page, Integer limit, String sort, String dir,
            String title, List<Long> categoryIds,  String location,
            Date startDate, Date endDate ,String search ) {
      
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String sortBy = (sort == null || sort.isBlank()) ? "eventId" : sort;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sortBy));

        Specification<Event> spec = Specification.allOf(
        		EventSpecifications.freeText(search),//เพิ่มsearch
                EventSpecifications.titleContains(title),
                EventSpecifications.locationContains(location),
                EventSpecifications.categoryIdIn(categoryIds),
                EventSpecifications.startDateGte(startDate),
                EventSpecifications.endDateLte(endDate)
        );

        return repo.findAll(spec, pageable);
    }
    //เรียก Repository เพื่อดึงกิจกรรมตาม id ถ้าพบให้คืน Event กลับไป ถ้าไม่พบก็โยน ResponseStatusException(404) ออกไปให้ Controller เห็นทันที
    public Event getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }
    
}

