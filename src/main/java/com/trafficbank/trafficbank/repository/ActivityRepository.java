package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query(value = "select * from activities a where a.account_number = :accountNumber", nativeQuery = true)
    List<Activity> findAllByAccountNumber(@Param("accountNumber") String accountNumber);
}
