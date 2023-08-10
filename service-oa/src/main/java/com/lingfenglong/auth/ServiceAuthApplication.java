package com.lingfenglong.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lingfenglong")
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class);
    }
}
