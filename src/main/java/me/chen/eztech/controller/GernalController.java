package me.chen.eztech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GernalController {

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/admin/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}
