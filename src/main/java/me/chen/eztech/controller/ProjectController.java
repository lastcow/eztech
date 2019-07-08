package me.chen.eztech.controller;

import me.chen.eztech.form.MilestoneForm;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.Task;
import me.chen.eztech.service.ProjectMemberService;
import me.chen.eztech.service.ProjectService;
import me.chen.eztech.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/project")
public class ProjectController {

    @Autowired
    TaskService taskService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectMemberService projectMemberService;

    @GetMapping({"", "/"})
    public String index(){
        return "project/index";
    }


    @PostMapping(value = "/{id}//user/add")
    public ResponseEntity<?> addUser(@PathVariable String id, @RequestBody List<String> users){

        Optional<Project> project = projectService.getProjectById(id);

        if(!project.isPresent()){
            return null;
        }
        // Make all members inactivated
        projectService.inactivateMembers(id);

        users.forEach(userId -> {
            projectMemberService.create(project.get().getId(), userId);
        });

        return ResponseEntity.ok(null);

    }

    @PostMapping(value = "/milestone/add")
    public ResponseEntity<?> addMilestone(@RequestBody MilestoneForm milestone){

        Optional<Project> project = projectService.getProjectById(milestone.getProjectId());

        if(project.isPresent()){
            Task task = new Task();
            task.setProject(project.get());
            task.setDescription(milestone.getDesc());
            task.setDeadline(milestone.getDeadline());
            task.setMilestone(true);

            taskService.create(task);

            return ResponseEntity.ok(task);
        }

        return (ResponseEntity<?>) ResponseEntity.notFound();

    }
}
