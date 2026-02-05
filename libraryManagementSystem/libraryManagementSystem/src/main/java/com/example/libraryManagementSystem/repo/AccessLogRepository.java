package com.example.libraryManagementSystem.repo;


import com.example.libraryManagementSystem.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findByUsername(String username);
    List<AccessLog> findByModule(String module);
    List<AccessLog> findByAction(String action);
}
