package com.fibikky.vehicle.example.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aminor_z
 */
@RestController
public class ProviderController {
    @GetMapping()
    public String index() {
        return "hello, this is provider.";
    }

    @GetMapping("/test")
    public String test() {
        return "this is the page provider/test.";
    }
}
