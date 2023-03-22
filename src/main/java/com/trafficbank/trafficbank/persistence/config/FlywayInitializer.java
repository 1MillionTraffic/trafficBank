package com.trafficbank.trafficbank.persistence.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayInitializer {
    @Bean(name = "bankFlyway", initMethod = "migrate")
    public Flyway bankFlyway(@Qualifier("bankDataSource") DataSource bankDataSource) {
        return Flyway.configure()
                .dataSource(bankDataSource)
                .locations("db/migration/bank")
                .baselineOnMigrate(true)
                .load();
    }

    @Bean(name = "userFlyway", initMethod = "migrate")
    public Flyway userFlyway(@Qualifier("userDataSource") DataSource userDataSource) {
        return Flyway.configure()
                .dataSource(userDataSource)
                .locations("db/migration/user")
                .baselineOnMigrate(true)
                .load();
    }

    @Bean(name = "couponFlyway1", initMethod = "migrate")
    public Flyway couponFlyway1(@Qualifier("couponDataSource1") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/coupon")
                .baselineOnMigrate(true)
                .load();
    }

    @Bean(name = "couponFlyway2", initMethod = "migrate")
    public Flyway couponFlyway2(@Qualifier("couponDataSource2") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/coupon")
                .baselineOnMigrate(true)
                .load();
    }

}
