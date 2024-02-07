package com.tryagain.tryagain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TryagainApplication {
    public static void main(String[] args) {
        SpringApplication.run(TryagainApplication.class, args);
    }

}
