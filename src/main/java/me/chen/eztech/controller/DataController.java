package me.chen.eztech.controller;


import me.chen.eztech.dto.ProjectDto;
import me.chen.eztech.dto.UserDto;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.Task;
import me.chen.eztech.model.User;
import me.chen.eztech.service.ProjectService;
import me.chen.eztech.service.TaskService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class DataController {

    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @PostMapping(value = "/data/projects", produces = "application/json")
    @ResponseBody
    public List<ProjectDto> getMyProjects(@AuthenticationPrincipal Principal principal){
        List<ProjectDto> projectDtos = new ArrayList<>();

        List<Project> projects = projectService.getProjects();
        projects.forEach(project -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(project.getId());
            projectDto.setProjectName(project.getName());
            projectDto.setDeadLine(project.getDuedate());
            projectDto.setStatus(project.getStatus());
            projectDto.setProjectDesc(project.getDescription());

            // Get students
            List<User> students = project.getStudents();
            students.forEach(student->{
                projectDto.setStudents(projectDto.getStudents() + " / " + student.getFirstName() + " " + student.getLastName());
            });

            // Get tasks
            List<Task> todos = taskService.getTaskByProjectAndStatus(project.getId(), false);
            projectDto.setTodos(todos.size());

            // Get users
            List<User> users = project.getStudents();
            List<UserDto> userDtos = new ArrayList<>();
            users.forEach(user -> {
                UserDto userDto = new UserDto();
                userDto.setName(user.getFirstName() + " " + user.getLastName());
                userDto.setEmail(user.getEmail());
                userDtos.add(userDto);
            });

            // Add 2 dummy data
            userDtos.add(new UserDto("1","Zhijiang", "a@chen.me"));
            userDtos.add(new UserDto("2", "Fei", "b@chen.me"));

            projectDto.setUsers(userDtos);

            projectDtos.add(projectDto);
        });

        return projectDtos;

    }

    /**
     * New project
     * @param projectDto
     * @return
     */
    @PostMapping(value = "/data/project/new")
    @ResponseStatus(HttpStatus.OK)
    public String newProject(ProjectDto projectDto){

        Project project = projectService.newProjectWithDto(projectDto);
        return project.getId();
    }

    @PutMapping(value = "/data/project/update/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateProject(@PathVariable String projectId, ProjectDto projectDto){

        Project project = projectService.updateProjectWithDto(projectId, projectDto);

        return project == null ? "" : projectId;
    }


    @GetMapping(value = "/data/users/all", produces = "application/json")
    public List<UserDto> getUsers(){

        List<UserDto> userDtos = new ArrayList<>();
        // Add 2 dummy data
        userDtos.add(new UserDto("1","Zhijiang", "a@chen.me"));
        userDtos.add(new UserDto("2", "Fei", "b@chen.me"));

        return userDtos;
    }
}
