package com.jonathanfoucher.sharedmdcexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SharedMDCExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharedMDCExampleApplication.class, args);
    }
}
