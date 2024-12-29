package com.example.mountaineerback.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRequest {

    private Long id;
    private String name;
    private Integer price;
    private String type;
    private String description;

    private String url;
}
