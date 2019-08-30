package me.chen.eztech.controller;

import me.chen.eztech.dto.EventDto;
import me.chen.eztech.model.Event;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.ProjectMembers;
import me.chen.eztech.model.User;
import me.chen.eztech.service.MD5Util;
import me.chen.eztech.service.ProjectMemberService;
import me.chen.eztech.service.UserService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.provider.MD5;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    UserService userService;
    @Autowired
    ProjectMemberService projectMemberService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, ModelMap modelMap){
        Optional<User> userOptional = userService.getUserByUsername(principal.getName());
        User user;
        PrettyTime prettyTime = new PrettyTime();
        if(userOptional.isPresent()){
            user = userOptional.get();
            // Get all events;
            List<Event> events = user.getEvents();

            List<EventDto> eventDtos = new ArrayList<>();

            events.forEach(event -> {
                EventDto eventDto = new EventDto();
                eventDto.setUsername(principal.getName());
                eventDto.setEventType(event.getEventType());
                eventDto.setEventDesc(event.getEvent());
                eventDto.setEventTime(event.getEventTime());
                eventDto.setTimeStr(prettyTime.format(event.getEventTime()));
                eventDto.setUserImgHash(MD5Util.md5Hex(user.getEmail()));

                eventDtos.add(eventDto);
            });

            // Get Project related information, includes advisor, project details.
            List<ProjectMembers> projects = projectMemberService.getProjectByActiveAndUserName(true, user.getUsername());
            // TODO student should only have one project at any time.
            if(projects.size()>0){
                // User has project and always get first one
                ProjectMembers pm = projects.get(0);
                Project project = pm.getProject();
                // New add project creating date
                EventDto eventDto = new EventDto();
                eventDto.setUsername(project.getProfessor().getUsername());
                eventDto.setEventType("projectcreat");
                eventDto.setEventDesc("创建课题");
                eventDto.setEventTime(project.getCreatedat());
                eventDto.setTimeStr(prettyTime.format(project.getCreatedat()));
                eventDto.setUserImgHash(MD5Util.md5Hex(project.getProfessor().getEmail()));

                eventDtos.add(eventDto);

                // Join event
                eventDto = new EventDto();
                eventDto.setUsername(user.getUsername());
                eventDto.setEventType("joinproject");
                eventDto.setEventDesc("加入课题");
                eventDto.setEventTime(pm.getJoined());
                eventDto.setTimeStr(prettyTime.format(eventDto.getEventTime()));
                eventDto.setUserImgHash(MD5Util.md5Hex(user.getEmail()));

                eventDtos.add(eventDto);

                modelMap.addAttribute("professorAvatar", MD5Util.md5Hex(project.getProfessor().getEmail()));

            }

            // Sort by event date desc
            eventDtos.sort(Comparator.comparing(EventDto::getEventTime).reversed());
            modelMap.addAttribute("events", eventDtos);


            // Get my avatar
            modelMap.addAttribute("myAvatar", MD5Util.md5Hex(user.getEmail()));
        }
        // Loading the event data

        return "student/dashboard";
    }


    @GetMapping("/filelib")
    public String fileLib(){
        return "student/filelib";
    }
}
