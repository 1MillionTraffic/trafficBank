package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
}
