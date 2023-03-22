package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.model.dto.CouponDTO;
import com.trafficbank.trafficbank.persistence.bank.entity.Coupon;
import com.trafficbank.trafficbank.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/make")
    public CouponDTO makeCoupon(@RequestParam("coupon") String couponValue) {
        Coupon coupon = couponService.makeCoupon(couponValue);
        return new CouponDTO(coupon.getId(), coupon.getCoupon());
    }

    @PostMapping
    public CouponDTO giveCoupon(@RequestParam("coupon_id") Long couponId, @RequestParam("user_id") Long userId) {
        Coupon coupon = couponService.giveCoupon(couponId, userId);
        return new CouponDTO(coupon.getId(), coupon.getCoupon());
    }

    @GetMapping
    public CouponDTO getCoupon(@RequestParam("user_id") Long userId) {
        Coupon coupon = couponService.getCoupon(userId);
        return new CouponDTO(coupon.getId(), coupon.getCoupon());
    }

}
