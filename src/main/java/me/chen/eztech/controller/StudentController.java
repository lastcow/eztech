package me.chen.eztech.controller;

import me.chen.eztech.dto.EventDto;
import me.chen.eztech.model.Event;
import me.chen.eztech.model.User;
import me.chen.eztech.service.MD5Util;
import me.chen.eztech.service.UserService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, ModelMap modelMap){
        Optional<User> userOptional = userService.getUserByUsername(principal.getName());
        User user;
        PrettyTime prettyTime = new PrettyTime();
        if(userOptional.isPresent()){
            user = userOptional.get();
            // Get all events;
            List<Event> events = user.getEvents();
            // Sort by event date desc
            events.sort(Comparator.comparing(Event::getEventTime).reversed());
            List<EventDto> eventDtos = new ArrayList<>();

            events.forEach(event -> {
                EventDto eventDto = new EventDto();
                eventDto.setUsername(principal.getName());
                eventDto.setEventType(event.getEventType());
                eventDto.setEventDesc(event.getEvent());
                eventDto.setTimeStr(prettyTime.format(event.getEventTime()));
                eventDto.setUserImgHash(MD5Util.md5Hex(user.getEmail()));

                eventDtos.add(eventDto);
            });

            modelMap.addAttribute("events", eventDtos);
        }
        // Loading the event data

        return "student/dashboard";
    }


    @GetMapping("/filelib")
    public String fileLib(){
        return "student/filelib";
    }
}
