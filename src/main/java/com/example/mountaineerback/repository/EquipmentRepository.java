package com.example.mountaineerback.repository;

import com.example.mountaineerback.model.enums.EQUIPMENT_TYPE;
import com.example.mountaineerback.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findById(Long id);

    List<Equipment> findByType(EQUIPMENT_TYPE type);
}
