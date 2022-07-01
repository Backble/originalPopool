package kr.co.paymentservice.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@DiscriminatorValue("S")
@Entity
public class SubscribeEntity extends ItemMstEntity{

    @Column(nullable = false)
    private LocalDate payDatePerMonth;
}
