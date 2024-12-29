package com.example.mountaineerback.model.dto;

import com.example.mountaineerback.model.enums.EQUIPMENT_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {
    private Long id;
    private String name;
    private Integer price;
    private EQUIPMENT_TYPE type;
    private String description;

    private String url;
}
