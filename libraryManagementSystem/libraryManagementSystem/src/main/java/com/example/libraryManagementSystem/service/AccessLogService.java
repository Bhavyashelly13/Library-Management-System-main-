package com.example.libraryManagementSystem.service;


import com.example.libraryManagementSystem.entity.AccessLog;
import com.example.libraryManagementSystem.repo.AccessLogRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public List<AccessLog> findAll() {
        return accessLogRepository.findAll();
    }

    public AccessLog findById(Long id) {
        return accessLogRepository.findById(id).orElse(null);
    }

    public AccessLog save(AccessLog log1) {
        log.info("Access logged: {} -> {} ({})", log1.getUsername(), log1.getModule(), log1.getAction());
        return accessLogRepository.save(log1);
    }

    public List<AccessLog> findByUsername(String username) {
        return accessLogRepository.findByUsername(username);
    }

    public List<AccessLog> findByModule(String module) {
        return accessLogRepository.findByModule(module);
    }

    public List<AccessLog> findByAction(String action) {
        return accessLogRepository.findByAction(action);
    }
}
