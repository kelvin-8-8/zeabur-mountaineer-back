package com.example.mountaineerback.service.impl;

import com.example.mountaineerback.model.dto.OrderDTO;
import com.example.mountaineerback.model.dto.OrderItemDTO;
import com.example.mountaineerback.model.entity.Equipment;
import com.example.mountaineerback.model.entity.Order;
import com.example.mountaineerback.model.entity.OrderItem;
import com.example.mountaineerback.model.entity.User;
import com.example.mountaineerback.model.request.OrderRequest;
import com.example.mountaineerback.repository.EquipmentRepository;
import com.example.mountaineerback.repository.OrderItemRepository;
import com.example.mountaineerback.repository.OrderRepository;
import com.example.mountaineerback.repository.UserRepository;
import com.example.mountaineerback.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.mountaineerback.model.enums.ORDER_STATUS.*;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<OrderDTO> findOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllOrder() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO addOrder(Long userId, OrderRequest orderRequest) {
        // 1. 找 User
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) return null;


        // 2. 建立訂單並設置關聯
        Order order = new Order();
        order.setUser(optUser.get());
        order.setStartDate(orderRequest.getStartDate());
        order.setDuration(orderRequest.getDuration());
        order.setStatus(STATUS_WAIT);
        System.out.println("orderRequest" + orderRequest);

        // 3. 建立 OrderItems 並設置雙向關聯
        List<OrderItemDTO> items = orderRequest.getItems();
        System.out.println("items" + items);
        items.forEach(itemDTO -> {
            Optional<Equipment> optEquipment = equipmentRepository.findById(itemDTO.getId());
            if (optEquipment.isPresent()) {
                // 創建 OrderItem 並關聯
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setEquipment(optEquipment.get());
                orderItem.setOrder(order);
                order.addItem(orderItem); // 使用輔助方法設置關聯
            } else {
                System.out.println("Equipment not found with id: " + itemDTO.getId());
            }
        });

        // 4. 保存 Order（會級聯保存 OrderItems）
        orderRepository.save(order);

        // 5. 返回 DTO
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(Long id) {
        Optional<Order> optOrder = orderRepository.findById(id);

        if (optOrder.isEmpty()) return null;
        Order order = optOrder.get();
        if (optOrder.get().getStatus() == STATUS_WAIT) {
            order.setStatus(STATUS_SUCCESS);
        } else if (optOrder.get().getStatus() == STATUS_SUCCESS) {
            order.setStatus(STATUS_BORROW);
        } else if (optOrder.get().getStatus() == STATUS_BORROW) {
            order.setStatus(STATUS_COMPLETE);
        }
        orderRepository.save(order);

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO cancelOrder(Long id) {
        Optional<Order> optOrder = orderRepository.findById(id);

        if (optOrder.isEmpty()) return null;
        Order order = optOrder.get();
        order.setStatus(STATUS_CANCEL);

        OrderDTO orderdto = modelMapper.map(optOrder.get(), OrderDTO.class);
        orderRepository.save(order);

        return orderdto;
    }
}
