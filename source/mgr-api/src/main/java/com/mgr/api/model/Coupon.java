package com.mgr.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "coupon")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Coupon extends Auditable<String> {

    @Column(unique = true)
    private String code;

    private Double discountValue;
    
    @Column(name = "discount_type")
    private Integer discountType; // 1: Percent, 2: Fixed amount

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "min_order_value")
    private Double minOrderValue;
}
