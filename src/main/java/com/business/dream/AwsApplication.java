package com.business.dream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class AwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsApplication.class, args);
    }


    @Bean
    CommandLineRunner checkDataSource(ApplicationContext ctx) {
        return args -> {
            log.info("🔍 DataSource 빈 존재 여부: {}", ctx.containsBean("dataSource"));

            String[] beanNames = ctx.getBeanNamesForType(DataSource.class);
            log.info("🔍 DataSource 타입 빈 목록: {}", Arrays.toString(beanNames));

            if (beanNames.length > 0) {
                DataSource ds = ctx.getBean(DataSource.class);
                log.info("🔍 DataSource 클래스: {}", ds.getClass().getName());
            }
        };
    }
}
