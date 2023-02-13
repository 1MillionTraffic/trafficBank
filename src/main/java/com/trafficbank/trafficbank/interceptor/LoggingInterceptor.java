package com.trafficbank.trafficbank.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("""
                *************************************************
                URL: [{}] {}
                query string: {}
                *************************************************
                """, request.getMethod(), request.getRequestURL(), request.getQueryString()
        );

        return true;
    }
}