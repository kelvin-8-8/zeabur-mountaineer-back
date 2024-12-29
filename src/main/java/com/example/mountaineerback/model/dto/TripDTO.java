package com.example.mountaineerback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private Integer duration;
    private LocalDateTime createdAt;
}
