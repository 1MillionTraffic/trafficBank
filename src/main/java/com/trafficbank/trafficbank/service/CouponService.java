package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.persistence.bank.entity.Coupon;
import com.trafficbank.trafficbank.persistence.bank.repository.CouponRepository;
import com.trafficbank.trafficbank.persistence.coupon.entity.CouponHistory;
import com.trafficbank.trafficbank.persistence.coupon.repository.CouponHistoryRepository;
import com.trafficbank.trafficbank.persistence.routing.DatabaseContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;

    public Coupon makeCoupon(String couponValue) {
        Coupon coupon = new Coupon();
        coupon.setCoupon(couponValue);
        return couponRepository.save(coupon);
    }

    public Coupon giveCoupon(Long couponId, Long userId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        DatabaseContextHolder.setCouponShard((int) (userId % 2));

        CouponHistory couponHistory = new CouponHistory();
        couponHistory.setCouponId(couponId);
        couponHistory.setUserId(userId);
        couponHistoryRepository.save(couponHistory);

        return coupon;
    }

    public Coupon getCoupon(Long userId) {
        DatabaseContextHolder.setCouponShard((int) (userId % 2));

        CouponHistory couponHistory = couponHistoryRepository.findByUserId(userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        return couponRepository.findById(couponHistory.getCouponId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
