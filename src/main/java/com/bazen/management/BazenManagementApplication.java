package com.bazen.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BazenManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BazenManagementApplication.class, args);
    }
}