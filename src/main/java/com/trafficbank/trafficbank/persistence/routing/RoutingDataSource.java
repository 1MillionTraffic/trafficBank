package com.trafficbank.trafficbank.persistence.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@RequiredArgsConstructor
public class RoutingDataSource extends AbstractRoutingDataSource {
    private final ShardKey key;

    @Override
    protected Integer determineCurrentLookupKey() {
        return DatabaseContextHolder.getShardMap().get(key);
    }
}
