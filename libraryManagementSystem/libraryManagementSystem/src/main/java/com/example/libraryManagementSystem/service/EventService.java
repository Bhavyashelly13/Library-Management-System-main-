package com.example.libraryManagementSystem.service;

import com.example.libraryManagementSystem.dto.ChartData;
import com.example.libraryManagementSystem.entity.Event;
import com.example.libraryManagementSystem.repo.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event save(Event event) {
        log.info("Event saved: {} (Type: {})", event.getTitle(), event.getType());
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
        log.info("Event deleted: {}", id);
    }

    public List<Event> findByType(String type) {
        return eventRepository.findByType(type);
    }

    public List<Event> findByStatus(String status) {
        return eventRepository.findByStatus(status);
    }

    public ChartData getChartData() {
        List<Event> allEvents = eventRepository.findAll();
        Map<String, Long> typeCount = allEvents.stream()
                .collect(Collectors.groupingBy(Event::getType, Collectors.counting()));

        Map<String, Long> statusCount = allEvents.stream()
                .collect(Collectors.groupingBy(Event::getStatus, Collectors.counting()));

        ChartData data = new ChartData();
        data.setTypeCount(typeCount);
        data.setStatusCount(statusCount);
        data.setTotalEvents((long) allEvents.size());
        return data;
    }
}
