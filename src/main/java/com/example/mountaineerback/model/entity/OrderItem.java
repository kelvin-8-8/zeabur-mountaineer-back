package com.example.mountaineerback.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 商品數量
    private int quantity;

    // order_item 與 equipment 的關係是多對一關聯
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    // order_item 與 order 的關係是多對一關聯
    @ManyToOne
    @JoinColumn(name = "order_id")
    // 阻止lombok改toString，為了sout() debug用
    @ToString.Exclude
    private Order order;

}




