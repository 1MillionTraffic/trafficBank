package com.trafficbank.trafficbank.persistence.coupon.repository;

import com.trafficbank.trafficbank.persistence.coupon.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

    Optional<CouponHistory> findByUserId(Long userId);
}
