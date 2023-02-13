package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @RequestMapping("/test")
    public String test() {
        System.out.println("[TestController] thread: " + Thread.currentThread().getId());
        testService.test();
        testService.test3();
        return "test";
    }

    @RequestMapping("/test2")
    public String test2() throws SQLException {
        System.out.println("[TestController] thread: " + Thread.currentThread().getId());
        testService.test2a();
        testService.test2b();
        testService.test4();
        return "test2";
    }

}
