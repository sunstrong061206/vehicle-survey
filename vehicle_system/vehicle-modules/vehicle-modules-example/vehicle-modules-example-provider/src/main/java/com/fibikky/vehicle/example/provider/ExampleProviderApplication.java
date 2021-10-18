package com.fibikky.vehicle.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Aminor_z
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ExampleProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleProviderApplication.class, args);
    }
}
