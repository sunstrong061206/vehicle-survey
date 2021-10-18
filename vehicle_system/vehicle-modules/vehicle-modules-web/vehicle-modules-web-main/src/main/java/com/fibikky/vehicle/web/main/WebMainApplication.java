package com.fibikky.vehicle.web.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Aminor_z
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WebMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebMainApplication.class, args);
    }
}
