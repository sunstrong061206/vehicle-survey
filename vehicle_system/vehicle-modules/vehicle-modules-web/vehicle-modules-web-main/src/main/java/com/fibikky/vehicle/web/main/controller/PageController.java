package com.fibikky.vehicle.web.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * html页面控制器
 *
 * @author 16861
 */
@Controller
public class PageController {
    @RequestMapping(value = "/")
    public String index(HttpSession session) {
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/control")
    public String controlPage() {return "control";}

    @RequestMapping(value = "/data")
    public String dataPage() {
        return "data";
    }

    @RequestMapping(value = "/monitor")
    public String monitorPage() {
        return "monitor";
    }

    @RequestMapping(value = "/manage")
    public String managePage() {
        return "manage";
    }

    @GetMapping("/websocket")
    public String websocketTest() {
        return "WebSocketTest";
    }
}

