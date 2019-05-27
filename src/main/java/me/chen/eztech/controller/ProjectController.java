package me.chen.eztech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/project")
public class ProjectController {

    @GetMapping({"", "/"})
    public String index(){
        return "project/index";
    }
}
