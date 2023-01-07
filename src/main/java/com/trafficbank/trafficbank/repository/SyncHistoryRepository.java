package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncHistoryRepository extends JpaRepository<SyncHistory, Long> {
}
