package com.example.libraryManagementSystem.controller;

import com.example.libraryManagementSystem.dto.ResponseWrapper;
import com.example.libraryManagementSystem.entity.AccessLog;
import com.example.libraryManagementSystem.entity.Report;
import com.example.libraryManagementSystem.entity.Transaction;
import com.example.libraryManagementSystem.service.AccessLogService;
import com.example.libraryManagementSystem.service.ReportService;
import com.example.libraryManagementSystem.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaintenanceController {

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private TransactionService transactionService;

    // Access Logs
    @GetMapping("/access")
    public ResponseEntity<ResponseWrapper<List<AccessLog>>> getAccessLogs() {
        List<AccessLog> logs = accessLogService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Access logs retrieved", logs));
    }

    @PostMapping("/access")
    public ResponseEntity<ResponseWrapper<AccessLog>> logAccess(@RequestBody AccessLog log) {
        AccessLog saved = accessLogService.save(log);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Access logged", saved));
    }

    // Reports
    @GetMapping("/reports")
    public ResponseEntity<ResponseWrapper<List<Report>>> getReports() {
        List<Report> reports = reportService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Reports retrieved", reports));
    }

    @PostMapping("/reports")
    public ResponseEntity<ResponseWrapper<Report>> createReport(@RequestBody Report report) {
        Report saved = reportService.save(report);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Report created", saved));
    }

    // Transactions
    @GetMapping("/transactions")
    public ResponseEntity<ResponseWrapper<List<Transaction>>> getTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Transactions retrieved", transactions));
    }

    @PostMapping("/transactions")
    public ResponseEntity<ResponseWrapper<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        transaction.setStatus("COMPLETED");
        Transaction saved = transactionService.save(transaction);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Transaction created", saved));
    }
}
