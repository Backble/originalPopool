package kr.co.paymentservice.repository;

import kr.co.paymentservice.domain.entity.ItemMstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMstRepository extends JpaRepository<ItemMstEntity, Long> {

}
