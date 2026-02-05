package com.example.libraryManagementSystem.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ChartData {
    private Map<String, Long> typeCount;
    private Map<String, Long> statusCount;
    private Long totalEvents;
}
