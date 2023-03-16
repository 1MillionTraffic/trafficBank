package com.trafficbank.trafficbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = {FlywayAutoConfiguration.class})
public class TrafficBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficBankApplication.class, args);
    }

}
