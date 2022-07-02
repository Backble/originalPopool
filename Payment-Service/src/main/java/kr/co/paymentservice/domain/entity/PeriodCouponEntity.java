package kr.co.paymentservice.domain.entity;

import kr.co.paymentservice.domain.shared.enums.CouponPeriod;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("P")
@Entity
public class PeriodCouponEntity extends ItemMstEntity {

    @Column(nullable = false)
    private CouponPeriod period;
}
