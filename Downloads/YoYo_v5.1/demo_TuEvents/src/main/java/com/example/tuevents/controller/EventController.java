package com.example.tuevents.controller;

import com.example.tuevents.model.Event;
import com.example.tuevents.service.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI; 

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"})
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    //พัฒนา API GET__|__events API ดึงกิจกรรมทั้งหมด รองรับ query เช่น page, limit, sort ดึงข้อมูลกิจกรรมแบบมีเงื่อนไขได้!!!
    //เปลี่ยนโค้ดเพื่อ Handle Empty & Error State ตาม task5:US1
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String sort,               
            @RequestParam(defaultValue = "asc") String dir,  
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String title,
            @RequestParam(name = "category", required = false) List<Long> categoryIds, 
            @RequestParam(required = false) String location,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ) {
        try {
            Page<Event> result = service.search(page, limit, sort, dir, title, categoryIds, location, startDate, endDate, search);

            if (result.isEmpty()) {
                // ⚠️ ถ้าไม่มีข้อมูล
                return ResponseEntity.status(HttpStatus.OK)
                        .body("{\"message\": \"ไม่มีข้อมูลกิจกรรม\"}");
            }
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // ⚠️ ถ้าเกิด error ในการเชื่อมต่อหรือ query
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"เกิดข้อผิดพลาดจากเซิร์ฟเวอร์ กรุณาลองใหม่อีกครั้ง\"}");
        }
    }
    
    
   //เรียก service.getById(id)
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // ---- CRUD เดิมของคุณ ----
    @PostMapping("/add")
    public ResponseEntity<Event> add(@Valid @RequestBody Event e) {
        Event created = service.create(e);
        return ResponseEntity
                .created(URI.create("/api/events/" + created.getId()))
                .body(created);
    }

    @PutMapping("/update/{id}")
    public Event update(@PathVariable Long id, @Valid @RequestBody Event e) {
        return service.update(id, e);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

}
