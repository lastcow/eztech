package me.chen.eztech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {


    @GetMapping("/student/question")
    public String questionStudent(){
        return "student/question";
    }
}
