package com.wahak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@EntityScan(basePackages = "com.wahak.entity")
public class WahakApplication {

    public static void main(String[] args) {
        SpringApplication.run(WahakApplication.class, args);
    }
}
