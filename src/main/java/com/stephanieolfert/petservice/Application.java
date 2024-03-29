package com.stephanieolfert.petservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
