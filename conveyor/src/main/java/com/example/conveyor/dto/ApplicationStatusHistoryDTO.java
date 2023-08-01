package com.example.conveyor.dto;

import com.example.conveyor.dto.enums.ChangeType;

import java.time.LocalDateTime;
public class ApplicationStatusHistoryDTO {
    private String status;
    private LocalDateTime time;
    private ChangeType changeType;
}
