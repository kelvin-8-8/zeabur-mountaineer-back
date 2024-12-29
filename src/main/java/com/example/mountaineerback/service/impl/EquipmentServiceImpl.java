package com.example.mountaineerback.service.impl;

import com.example.mountaineerback.model.dto.EquipmentDTO;
import com.example.mountaineerback.model.entity.Equipment;
import com.example.mountaineerback.model.entity.EquipmentImage;
import com.example.mountaineerback.model.enums.EQUIPMENT_TYPE;
import com.example.mountaineerback.model.request.EquipmentRequest;
import com.example.mountaineerback.repository.EquipmentImageRepository;
import com.example.mountaineerback.repository.EquipmentRepository;
import com.example.mountaineerback.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentImageRepository equipmentImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentRepository.findAll().stream()
                .map(equipment -> {
                    EquipmentDTO equipmentDTO = modelMapper.map(equipment, EquipmentDTO.class);
                    if (equipment.getEquipmentImage() != null) {
                        equipmentDTO.setUrl(equipment.getEquipmentImage().getUrl());
                    }
                    return equipmentDTO;} )
                .collect(Collectors.toList());
    }

    // TODO 還沒測試過
    @Override
    public List<EquipmentDTO> getEquipmentByType(EQUIPMENT_TYPE type) {
        return equipmentRepository.findByType(type).stream()
                .map(equipment -> modelMapper.map(equipment, EquipmentDTO.class))
                .collect(Collectors.toList());
    }

    // 新增
    @Override
    public EquipmentDTO addEquipment(EquipmentRequest equipmentRequest) {
        // DTO 轉 entity
        Equipment equipment = modelMapper.map(equipmentRequest, Equipment.class);

        // 跟 image 關聯
        EquipmentImage equipmentImage = new EquipmentImage();
        equipmentImage.setUrl(equipmentRequest.getUrl());
        System.out.println(equipmentImage);
        equipment.setEquipmentImage(equipmentImage);

        equipment = equipmentRepository.save(equipment);

        EquipmentDTO equipmentDTO = modelMapper.map(equipment, EquipmentDTO.class);
        equipmentDTO.setUrl(equipmentRequest.getUrl());
        return equipmentDTO;
    }

    // 刪除
    @Override
    public Boolean deleteEquipment(Long id) {

        Optional<Equipment> optEquipment= equipmentRepository.findById(id);

        if (optEquipment.isPresent()) {
            equipmentRepository.delete(optEquipment.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    // 修改
    @Override
    public Optional<EquipmentDTO> changeEquipment(EquipmentRequest equipmentRequest) {

        Optional<Equipment> optEquipment= equipmentRepository.findById(equipmentRequest.getId());

        if (optEquipment.isPresent()) {

            EQUIPMENT_TYPE enumType = EQUIPMENT_TYPE.valueOf(equipmentRequest.getType());
            // 修改資料
            optEquipment.get().setName(equipmentRequest.getName());
            optEquipment.get().setDescription(equipmentRequest.getDescription());
            optEquipment.get().setPrice(equipmentRequest.getPrice());
            optEquipment.get().setType(enumType);

            equipmentRepository.save(optEquipment.get());

            EquipmentDTO equipmentDTO = modelMapper.map(optEquipment.get(), EquipmentDTO.class);
            equipmentDTO.setUrl(optEquipment.get().getEquipmentImage().getUrl());

            return Optional.of(equipmentDTO);
        }

        return Optional.empty();
    }
}
