package com.trafficbank.trafficbank.persistence.config;

import com.trafficbank.trafficbank.persistence.routing.RoutingDataSource;
import com.trafficbank.trafficbank.persistence.routing.ShardKey;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "couponEntityManagerFactory",
        transactionManagerRef = "couponTransactionManager",
        basePackages = {"com.trafficbank.trafficbank.persistence.coupon"}
)
public class CouponConfig {
    @Bean("couponDataSource1")
    public DataSource couponDatasource1(@Value("${spring.datasource.coupon1.driver-class-name}") String driverClassName,
                                        @Value("${spring.datasource.coupon1.url}") String dataSourceUrl,
                                        @Value("${spring.datasource.coupon1.username}") String username,
                                        @Value("${spring.datasource.coupon1.password}") String password) {
        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class)
                .driverClassName(driverClassName)
                .url(dataSourceUrl)
                .username(username)
                .password(password)
                .build();

        dataSource.setConnectionInitSql("SET NAMES utf8mb4");
        return dataSource;
    }

    @Bean("couponDataSource2")
    public DataSource couponDatasource2(@Value("${spring.datasource.coupon2.driver-class-name}") String driverClassName,
                                        @Value("${spring.datasource.coupon2.url}") String dataSourceUrl,
                                        @Value("${spring.datasource.coupon2.username}") String username,
                                        @Value("${spring.datasource.coupon2.password}") String password) {
        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class)
                .driverClassName(driverClassName)
                .url(dataSourceUrl)
                .username(username)
                .password(password)
                .build();

        dataSource.setConnectionInitSql("SET NAMES utf8mb4");
        return dataSource;
    }

    private Map<String, Object> jpaProperties() {
        return new HashMap<>() {{
            put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
            put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        }};
    }

    @Bean("couponRoutingDataSource")
    public DataSource couponRoutingDataSource(
            @Qualifier("couponDataSource1") DataSource couponDataSource1,
            @Qualifier("couponDataSource2") DataSource couponDataSource2) {
        RoutingDataSource routingDataSource = new RoutingDataSource(ShardKey.COUPON);

        HashMap<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(0, couponDataSource1);
        targetDataSources.put(1, couponDataSource2);
        routingDataSource.setTargetDataSources(targetDataSources);

        routingDataSource.setDefaultTargetDataSource(couponDataSource1);

        return routingDataSource;
    }


    @Bean("couponEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("couponRoutingDataSource") DataSource couponDataSource) {
        return builder
                .dataSource(couponDataSource)
                .packages("com.trafficbank.trafficbank.persistence.coupon.entity")
                .persistenceUnit("bank_coupon")
                .properties(jpaProperties())
                .build();
    }

    @Bean("couponTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("couponEntityManagerFactory") LocalContainerEntityManagerFactoryBean couponEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(couponEntityManagerFactory.getObject()));
    }
}
