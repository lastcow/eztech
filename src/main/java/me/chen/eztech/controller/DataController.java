package me.chen.eztech.controller;


import me.chen.eztech.dto.ProjectDto;
import me.chen.eztech.dto.UserDto;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.ProjectMembers;
import me.chen.eztech.model.Task;
import me.chen.eztech.model.User;
import me.chen.eztech.service.ProjectService;
import me.chen.eztech.service.TaskService;
import me.chen.eztech.service.UserService;
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
    @Autowired
    UserService userService;

    @PostMapping(value = "/data/projects", produces = "application/json")
    @ResponseBody
    public List<ProjectDto> getMyProjects(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();

        List<ProjectDto> projectDtos = new ArrayList<>();

        List<Project> projects = projectService.getMyProject(username);
        projects.forEach(project -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(project.getId());
            projectDto.setProjectName(project.getName());
            projectDto.setDeadLine(project.getDuedate());
            projectDto.setStatus(project.getStatus());
            projectDto.setProjectDesc(project.getDescription());

            // Get students
            List<ProjectMembers> students = project.getMembers();
            students.forEach(student->{
                if(student.isActive()) {
                    projectDto.setStudents(projectDto.getStudents() + " / " + student.getMember().getFirstName() + " " + student.getMember().getLastName());
                }
            });

            // Get tasks
            List<Task> todos = taskService.getTaskByProjectAndStatus(project.getId(), false);
            projectDto.setTodos(todos.size());

            // Get users
            List<ProjectMembers> users = project.getMembers();
            List<UserDto> userDtos = new ArrayList<>();
            users.forEach(user -> {
                if(user.isActive()) {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getMember().getId());
                    userDto.setName(user.getMember().getFirstName() + " " + user.getMember().getLastName());
                    userDto.setEmail(user.getMember().getEmail());
                    userDtos.add(userDto);
                }
            });

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
    public String newProject(ProjectDto projectDto, Principal principal){

        String owner = principal.getName();
        Project project = projectService.newProjectWithDto(projectDto, owner);
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

        // Get users from data
        List<User> users = userService.getAllUsers();
        users.forEach(user -> {
            userDtos.add(new UserDto(user.getId(), user.getFirstName()+ " " + user.getLastName(), user.getEmail()));
        });

        return userDtos;
    }
}
