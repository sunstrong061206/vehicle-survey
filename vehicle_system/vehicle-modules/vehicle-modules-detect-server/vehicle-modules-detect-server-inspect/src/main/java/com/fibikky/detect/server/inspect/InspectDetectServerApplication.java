package com.fibikky.detect.server.inspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Aminor_z
 */
@SpringBootApplication
public class InspectDetectServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(InspectDetectServerApplication.class, args);
    }
}
