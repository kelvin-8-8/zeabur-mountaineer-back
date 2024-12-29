package com.example.mountaineerback.service;

import com.example.mountaineerback.model.dto.OrderDTO;
import com.example.mountaineerback.model.dto.OrderItemDTO;
import com.example.mountaineerback.model.request.OrderRequest;

import java.util.List;

public interface OrderService {

    // 找到 根據使用者id
    public List<OrderDTO> findOrderByUserId(Long userId);

    // 找到 所有
    public List<OrderDTO> findAllOrder();

    // 新增
    public OrderDTO addOrder(Long userid, OrderRequest orderRequest);

    // 刪除
    // TODO

    //

}
