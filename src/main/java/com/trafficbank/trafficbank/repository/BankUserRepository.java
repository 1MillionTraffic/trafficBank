package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
}
