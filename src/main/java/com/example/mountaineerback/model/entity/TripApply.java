package com.example.mountaineerback.model.entity;

import com.example.mountaineerback.model.enums.ORDER_STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trip_apply")
public class TripApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ORDER_STATUS status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 申請的用戶
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 申請的隊伍
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
