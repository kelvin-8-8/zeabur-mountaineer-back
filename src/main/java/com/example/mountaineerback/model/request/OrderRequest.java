package com.example.mountaineerback.model.request;

import com.example.mountaineerback.model.dto.OrderItemDTO;
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
public class OrderRequest {

    private LocalDate startDate;
    private Integer duration;
    private List<OrderItemDTO> items;
}
