
package com.example.tuevents.repo;

import com.example.tuevents.model.Event;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Date;

//สร้างSpecification เพื่อช่วยทำให้ search / filter / query ใน Spring Boot ง่ายขึ้น ยืดหยุ่นขึ้น และไม่ต้องเขียน method ยาว ๆ ใน Repo
public class EventSpecifications {

    public static Specification<Event> titleContains(String title) {
        return (root, query, cb) ->
                title == null || title.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Event> locationContains(String location) {
        return (root, query, cb) ->
                location == null || location.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<Event> categoryId(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null
                ? cb.conjunction()
                : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Event> startDateGte(Date start) {
        return (root, query, cb) ->
                start == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("startDate"), start);
    }

    public static Specification<Event> endDateLte(Date end) {
        return (root, query, cb) ->
                end == null
                ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get("endDate"), end);
    }
    //เพิ่มสspecification เพื่อ ค้นหาแบบ free-text
    public static Specification<Event> freeText(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("title")),       like),
                cb.like(cb.lower(root.get("description")), like),
                cb.like(cb.lower(root.get("location")),    like),
                cb.like(cb.lower(root.get("organizer")),   like)
            );
        };
    }
    
 //รองรับการเลือกหลายหมวดหมู่
public static Specification<Event> categoryIdIn(List<Long> ids) {
    return (root, query, cb) ->
        (ids == null || ids.isEmpty())
        ? cb.conjunction()
        		: root.get("category").get("categoryId").in(ids);
}
}