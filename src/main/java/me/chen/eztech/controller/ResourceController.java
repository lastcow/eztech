package me.chen.eztech.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping(value = "/admin/resource")
public class ResourceController {

    @GetMapping(value = {"", "/"})
    public String resource(){
        return "resource";
    }
}
