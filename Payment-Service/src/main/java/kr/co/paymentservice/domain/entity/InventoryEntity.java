package kr.co.paymentservice.domain.entity;

import kr.co.paymentservice.domain.shared.BaseEntity;
import kr.co.paymentservice.domain.shared.enums.CouponPeriod;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Table(name = "tbl_inventory")
@Entity
public class InventoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId; //기업 회원과 일대일 관계

    @Column(nullable = false)
    private int remainCouponCnt;

    @Column(nullable = false)
    private LocalDate endPeriodDate;

    @Column(nullable = false)
    private boolean isSubscription;


    //== coupon ==//
    public void useRemainCoupon(int usedCouponNum) {
        if (this.remainCouponCnt - usedCouponNum < 0)
            throw new RuntimeException("쿠폰이 충분하지 않습니다.");

        this.remainCouponCnt -= usedCouponNum;
    }

    public void addCoupon(int addCouponNum) {
        this.remainCouponCnt += addCouponNum;
    }


    //== period ==//
    public boolean isPeriodValid() {
        if (endPeriodDate.isAfter(LocalDate.now()) || endPeriodDate.isEqual(LocalDate.now()))
            return true;
        return false;
    }

    public void addPeriod(CouponPeriod couponPeriod) {
        if (isPeriodValid()) {
            this.endPeriodDate = this.endPeriodDate.plusMonths(couponPeriod.getPeriod());
        }
        else {
            this.endPeriodDate = LocalDate.now().plusMonths(couponPeriod.getPeriod());
        }
    }

    //== subscription ==//
    public void doSubscription() {
        if (this.isSubscription)
            throw new RuntimeException("이미 구독중입니다.");

        if (isPeriodValid())
            throw new RuntimeException("기간권 사용중입니다.");

        this.isSubscription = true;
    }

    public void cancelSubscription() {
        this.isSubscription = false;
    }
}
