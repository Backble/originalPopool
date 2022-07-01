package kr.co.paymentservice.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("C")
@Entity
public class CouponEntity extends ItemMstEntity {

    @Column(nullable = false)
    private int amount;
}
