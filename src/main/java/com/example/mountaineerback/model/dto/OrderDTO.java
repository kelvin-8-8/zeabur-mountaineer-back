package com.example.mountaineerback.model.dto;

import com.example.mountaineerback.model.entity.OrderItem;
import com.example.mountaineerback.model.entity.User;
import com.example.mountaineerback.model.enums.ORDER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDate startDate;
    private Integer duration;
    private LocalDateTime createdAt;
    private ORDER_STATUS status;
    private UserDTO user;
    private List<OrderItemDTO> items;
}
