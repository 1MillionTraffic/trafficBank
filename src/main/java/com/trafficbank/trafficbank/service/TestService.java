package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.persistence.user.repository.BankAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestService {
    private final BankAccountRepository bankAccountRepository;

    private final PlatformTransactionManager transactionManager;
    private final JpaTransactionManager jpaTransactionManager;

    public void test() {
        System.out.println("[TestService] test thread: " + Thread.currentThread().getId());
    }

    public void test2a() throws SQLException {
        System.out.println("[TestService] test2a thread: " + Thread.currentThread().getId());
        bankAccountRepository.findById(1L);

        Connection connection = jpaTransactionManager.getJpaDialect().getJdbcConnection(((EntityManagerHolder) TransactionSynchronizationManager.getResource(jpaTransactionManager.getEntityManagerFactory())).getEntityManager(), true).getConnection();
        System.out.println("connection = " + connection);
        System.out.println("------------------------------------------");
    }

    public void test2b() throws SQLException {
        System.out.println("[TestService] test2b thread: " + Thread.currentThread().getId());
        bankAccountRepository.findById(1L);

        Connection connection = jpaTransactionManager.getJpaDialect().getJdbcConnection(((EntityManagerHolder) TransactionSynchronizationManager.getResource(jpaTransactionManager.getEntityManagerFactory())).getEntityManager(), true).getConnection();
        System.out.println("connection = " + connection);
        System.out.println("------------------------------------------");

        test2c();
    }

    public void test2c() throws SQLException {
        System.out.println("[TestService] test2c thread: " + Thread.currentThread().getId());
        bankAccountRepository.findById(1L);

        Connection connection = jpaTransactionManager.getJpaDialect().getJdbcConnection(((EntityManagerHolder) TransactionSynchronizationManager.getResource(jpaTransactionManager.getEntityManagerFactory())).getEntityManager(), true).getConnection();
        System.out.println("connection = " + connection);
        System.out.println("------------------------------------------");
    }


    @Async
    public void test3() {
        System.out.println("[TestService] test3 thread: " + Thread.currentThread().getId());
        System.out.println("------------------------------------------");
    }

    @Async
    public void test4() throws SQLException {
        System.out.println("[TestService] test4 thread: " + Thread.currentThread().getId());

        EntityManagerFactory entityManagerFactory = jpaTransactionManager.getEntityManagerFactory();
        EntityManagerHolder entityManagerHolder = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory));

        if (entityManagerHolder == null) {
            Map<String, Object> properties = jpaTransactionManager.getJpaPropertyMap();
            EntityManager em = (!CollectionUtils.isEmpty(properties) ? entityManagerFactory.createEntityManager(properties) : entityManagerFactory.createEntityManager());
            EntityManagerHolder emHolder = new EntityManagerHolder(em);

            TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder);

            Connection connection = jpaTransactionManager.getJpaDialect().getJdbcConnection(em, true).getConnection();
            System.out.println("connection = " + connection);
        }

        bankAccountRepository.findById(1L);

        System.out.println("------------------------------------------");
    }

    public void testSync() {
        System.out.println("[TestService] test4 thread: " + Thread.currentThread().getId());
        bankAccountRepository.findById(1L);
    }

    @Async
    public void testAsync() {
        System.out.println("[TestService] test4 thread: " + Thread.currentThread().getId());
        bankAccountRepository.findById(1L);
    }

}
