package com.trafficbank.trafficbank.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    entityManagerFactoryRef = "bankEntityManagerFactory",
    transactionManagerRef = "bankTransactionManager",
    basePackages = {"com.trafficbank.trafficbank.persistence.bank"}
)
public class BankConfig {
    @Primary
    @Bean("bankDataSource")
    public DataSource datasource(@Value("${spring.datasource.bank.driver-class-name}") String driverClassName,
                                 @Value("${spring.datasource.bank.url}") String dataSourceUrl,
                                 @Value("${spring.datasource.bank.username}") String user,
                                 @Value("${spring.datasource.bank.password}") String password) {
        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class)
            .driverClassName(driverClassName)
            .url(dataSourceUrl)
            .username(user)
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

    @Primary
    @Bean("bankEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("bankDataSource") DataSource bankDataSource) {
        return builder
            .dataSource(bankDataSource)
            .packages("com.trafficbank.trafficbank.persistence.bank.entity")
            .persistenceUnit("bank")
            .properties(jpaProperties())
            .build();
    }

    @Primary
    @Bean("bankTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("bankEntityManagerFactory") LocalContainerEntityManagerFactoryBean bankEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(bankEntityManagerFactory.getObject()));
    }
}
