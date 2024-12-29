package com.example.mountaineerback.controller;


import com.example.mountaineerback.model.dto.EquipmentDTO;
import com.example.mountaineerback.model.enums.EQUIPMENT_TYPE;
import com.example.mountaineerback.model.request.EquipmentRequest;
import com.example.mountaineerback.model.response.ApiResponse;
import com.example.mountaineerback.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/*
 * WEB REST API
 * ----------------------------------
 * Servlet-Path: /equip
 * ----------------------------------
 * GET              全部裝備
 * GET  /{type}     用類型找裝備
 * POST /register   註冊
 * */
@Slf4j
@RestController
@RequestMapping("/equip")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    // 所有裝備資料
    @GetMapping
    public ResponseEntity<ApiResponse<List<EquipmentDTO>>> getEquipmentAll() {
        List<EquipmentDTO> equipmentDTO = equipmentService.getAllEquipment();
        return ResponseEntity.ok(ApiResponse.success("找全部裝備", equipmentDTO));
    }

    // TODO (目前用不到) 還沒測試過
    // 根據 type 取得裝備資料
    @GetMapping("/{type}")
    public ResponseEntity<ApiResponse<List<EquipmentDTO>>> getEquipmentType(@PathVariable String type) {
        //將傳入的 String 轉換成 Enum type
        EQUIPMENT_TYPE equipmentType = EQUIPMENT_TYPE.valueOf(type.toUpperCase());
        List<EquipmentDTO> equipmentDTO = equipmentService.getEquipmentByType(equipmentType);

        return ResponseEntity.ok(ApiResponse.success("利用type更新裝備", equipmentDTO));
    }

    // TODO 還沒測試過
    // 新增裝備
    @GetMapping("/add")
    public ResponseEntity<ApiResponse<EquipmentDTO>> addEquipment(@RequestBody EquipmentRequest equipmentRequest) {
        EquipmentDTO equipmentDTO =  equipmentService.addEquipment(equipmentRequest);
        return ResponseEntity.ok(ApiResponse.success("新增裝備成功", equipmentDTO));
    }


    // 刪除
    @GetMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteEquipmentId(@RequestBody EquipmentRequest equipmentRequest) {

        Boolean bool = equipmentService.deleteEquipment(equipmentRequest.getId());

        if (bool) {
            return ResponseEntity.ok(ApiResponse.success("刪除裝備成功", "刪除成功"));
        }
        return ResponseEntity.ok(ApiResponse.success("刪除裝備失敗", "刪除失敗"));
    }


    // 修改裝備
    @GetMapping("/change")
    public ResponseEntity<ApiResponse<EquipmentDTO>> deleteEquipment(@RequestBody EquipmentRequest equipmentRequest) {

        Optional<EquipmentDTO> optEquipmentDTO = equipmentService.changeEquipment(equipmentRequest);
        if (optEquipmentDTO.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("登入成功", optEquipmentDTO.get()));
        }
        return ResponseEntity.status(403).body(ApiResponse.error(403, "未知錯誤"));
    }
}
