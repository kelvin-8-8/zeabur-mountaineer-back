package com.example.mountaineerback.controller;

import com.example.mountaineerback.model.dto.OrderDTO;
import com.example.mountaineerback.model.dto.UserDTO;
import com.example.mountaineerback.model.request.OrderRequest;
import com.example.mountaineerback.model.response.ApiResponse;
import com.example.mountaineerback.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * WEB REST API
 * ----------------------------------
 * Servlet-Path: /order
 * ----------------------------------
 * GET   /         查詢該使用者所有商品(多筆)
 * POST  /checkout 該使用者進行結帳
 * */
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 獲取自己的訂單
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrder(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        List<OrderDTO> orderDTO = orderService.findOrderByUserId(userDTO.getId());
        return ResponseEntity.ok(ApiResponse.success("成功取得你的訂單", orderDTO));
    }

    // 獲取所有
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrder() {
        List<OrderDTO> orderDTO = orderService.findAllOrder();
        return ResponseEntity.ok(ApiResponse.success("成功取得所有的訂單", orderDTO));
    }

    // 新增
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody OrderRequest orderRequest, HttpSession session) {
        System.out.println(orderRequest);
        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
        OrderDTO orderDTO = orderService.addOrder(userDTO.getId(),orderRequest);
        return ResponseEntity.ok(ApiResponse.success("新增訂單成功", orderDTO));
    }
}
