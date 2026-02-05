package com.example.libraryManagementSystem.service;


import com.example.libraryManagementSystem.entity.Report;
import com.example.libraryManagementSystem.repo.ReportRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public void delete(Long id) {
        reportRepository.deleteById(id);
    }

    public List<Report> findByUserId(Long userId) {
        return reportRepository.findByUserId(userId);
    }

    public List<Report> findByReportType(String reportType) {
        return reportRepository.findByReportType(reportType);
    }
}
