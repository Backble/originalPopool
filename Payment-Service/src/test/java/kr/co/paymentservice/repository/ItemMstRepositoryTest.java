package kr.co.paymentservice.repository;

import kr.co.paymentservice.domain.entity.CouponEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemMstRepositoryTest {

    @Autowired
    ItemMstRepository itemMstRepository;

    @Test
    void save() {
        CouponEntity coupon = new CouponEntity();
        itemMstRepository.save(coupon);
    }

}