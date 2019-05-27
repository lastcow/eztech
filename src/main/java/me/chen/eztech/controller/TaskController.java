package me.chen.eztech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping(value = "/admin/task")
public class TaskController {


    @GetMapping(value = {"", "/"})
    public String task(){
        return "task";
    }
}
