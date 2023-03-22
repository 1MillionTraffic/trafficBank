package com.trafficbank.trafficbank.persistence.bank.repository;

import com.trafficbank.trafficbank.persistence.bank.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
