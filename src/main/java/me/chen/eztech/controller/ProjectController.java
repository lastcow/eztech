package me.chen.eztech.controller;

import me.chen.eztech.form.Milestone;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/project")
public class ProjectController {

    @GetMapping({"", "/"})
    public String index(){
        return "project/index";
    }


    @PostMapping(value = "/user/add")
    public void addUser(@RequestBody List<String> users){

        //TODO reassign user to project
        System.out.println(users.size());
    }

    @PostMapping(value = "/milestone/add")
    public ResponseEntity<?> addMilestone(@RequestBody Milestone milestone){

        System.out.println("New milestone");

        return ResponseEntity.ok(milestone);
    }
}
