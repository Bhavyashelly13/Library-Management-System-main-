package com.example.libraryManagementSystem.controller;

import com.example.libraryManagementSystem.dto.ChartData;
import com.example.libraryManagementSystem.dto.ResponseWrapper;
import com.example.libraryManagementSystem.entity.Event;
import com.example.libraryManagementSystem.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Event>>> getAll() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Events retrieved", events));
    }

    @GetMapping("/chart")
    public ResponseEntity<ResponseWrapper<ChartData>> getChart() {
        ChartData chartData = eventService.getChartData();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Chart data retrieved", chartData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Event>> getById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Event retrieved", event));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Event>> create(@RequestBody Event event) {
        Event saved = eventService.save(event);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Event created", saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Event>> update(@PathVariable Long id, @RequestBody Event event) {
        Event existing = eventService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        event.setId(id);
        Event updated = eventService.save(event);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Event updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Event deleted", null));
    }
}
