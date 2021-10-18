package com.fibikky.vehicle.web.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @ResponseBody
    @PostMapping("/api/user/login")
    public Object login(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/user/login", entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/user/logout")
    public Object logout(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/user/logout", Object.class);
    }

    @ResponseBody
    @PostMapping("/api/except/queryException")
    public Object queryException(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/except/queryException",entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/except/export")
    public Object export(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity("http://" + instance.getHost() + ":" + instance.getPort() + "/api/except/export",entity, byte[].class);
    }

    @ResponseBody
    @PostMapping("/api/except/delete")
    public Object delete(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/except/delete",entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/equipment/queryEquipment")
    public Object queryEquipment(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/equipment/queryEquipment",entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/stat/week/getExceptionCount")
    public Object getExceptionCount(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/stat/week/getExceptionCount",entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/pro/queryProcessing")
    public Object queryProcessing(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/pro/queryProcessing",entity, Object.class);
    }

    @ResponseBody
    @PostMapping("/api/pro/saveOrUpdate")
    public Object saveOrUpdate(@RequestHeader MultiValueMap<String, String> headers, @RequestBody Map<String, String> body) {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-web-api");
        ServiceInstance instance = instances.get(0);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/api/pro/saveOrUpdate",entity, Object.class);
    }

}

