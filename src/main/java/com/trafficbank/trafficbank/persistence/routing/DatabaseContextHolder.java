package com.trafficbank.trafficbank.persistence.routing;

import java.util.HashMap;
import java.util.Map;

public class DatabaseContextHolder {
    private static final ThreadLocal<Map<ShardKey, Integer>> context = ThreadLocal.withInitial(() -> {
        HashMap<ShardKey, Integer> shardMap = new HashMap<>();
        shardMap.put(ShardKey.COUPON, 0);
        return shardMap;
    });

    public static void setCouponShard(int shardNumber) {
        context.get().put(ShardKey.COUPON, shardNumber);
    }

    public static Map<ShardKey, Integer> getShardMap() {
        return context.get();
    }

}
