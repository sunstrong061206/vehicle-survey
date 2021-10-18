package com.fibikky.vehicle.example.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Aminor_z
 */
@RestController
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/")
    public String index() {
        return restTemplate.getForObject("http://localhost:8898/", String.class);
    }

    @GetMapping("/test")
    public String test() {
        List<ServiceInstance> instances = discoveryClient.getInstances("provider");
        ServiceInstance instance = instances.get(0);
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());

        return restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/test", String.class);
    }
}
