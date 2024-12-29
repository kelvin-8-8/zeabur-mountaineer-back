package com.example.mountaineerback.service;

import com.example.mountaineerback.model.dto.EquipmentDTO;
import com.example.mountaineerback.model.entity.Equipment;
import com.example.mountaineerback.model.enums.EQUIPMENT_TYPE;
import com.example.mountaineerback.model.request.EquipmentRequest;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    List<EquipmentDTO> getAllEquipment();

    List<EquipmentDTO> getEquipmentByType(EQUIPMENT_TYPE type);

    EquipmentDTO addEquipment(EquipmentRequest equipmentRequest);

    Boolean deleteEquipment(Long id);

    Optional<EquipmentDTO> changeEquipment( EquipmentRequest equipmentRequest);
}
