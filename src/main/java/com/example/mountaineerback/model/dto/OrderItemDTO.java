package com.example.mountaineerback.model.dto;

import com.example.mountaineerback.model.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private EquipmentDTO equipment;
}
