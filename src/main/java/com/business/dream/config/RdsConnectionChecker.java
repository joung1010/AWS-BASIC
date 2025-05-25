package com.business.dream.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@ConditionalOnBean(DataSource.class)

@Slf4j
public class RdsConnectionChecker {
    private final DataSource dataSource;

    // 생성자 주입으로 변경하여 빈 생성 확인
    public RdsConnectionChecker(@Autowired(required = false) DataSource dataSource) {
        this.dataSource = dataSource;
        log.info("🔍 RdsConnectionChecker 생성됨. DataSource: {}", dataSource);
    }
    
    @PostConstruct
    public void checkConnection() {
        try (Connection connection = dataSource.getConnection()) {
            log.info("✅ RDS 연결 성공!");
            log.info("Connection Class: {}", connection.getClass().getName());
            log.info("Database: {}", connection.getCatalog());
            
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                log.info("Pool Name: {}", hikariDataSource.getPoolName());
                log.info("Maximum Pool Size: {}", hikariDataSource.getMaximumPoolSize());
            }
            
        } catch (SQLException e) {
            log.error("❌ RDS 연결 실패: {}", e.getMessage());
        }
    }
}