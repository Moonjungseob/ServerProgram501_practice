package com.busanit501.mjsllunchproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing
public class MjslLunchProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(MjslLunchProjectApplication.class, args);
    }

}
