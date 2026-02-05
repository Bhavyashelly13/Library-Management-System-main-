package com.example.libraryManagementSystem.repo;


import com.example.libraryManagementSystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByType(String type);
    List<Event> findByStatus(String status);

    @Query("SELECT e FROM Event e GROUP BY e.type ORDER BY COUNT(e) DESC")
    List<Event> findEventsByTypeGrouped();
}
