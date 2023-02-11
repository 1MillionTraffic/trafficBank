package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.dto.response.ActivityResponseDto;
import com.trafficbank.trafficbank.entity.Account;
import com.trafficbank.trafficbank.repository.AccountRepository;
import com.trafficbank.trafficbank.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
public class GetActivitiesServiceTest {
    @BeforeAll
    public void beforeEach(){

    }

    @Test
    public void test(){
        System.out.println("1");
    }
}
