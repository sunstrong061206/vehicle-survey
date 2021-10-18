package com.fibikky.vehicle.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Aminor_z
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan("com.fibikky.vehicle.web.main.mappers")
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
