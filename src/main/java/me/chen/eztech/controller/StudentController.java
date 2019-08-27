package me.chen.eztech.controller;

import me.chen.eztech.dto.EventDto;
import me.chen.eztech.model.Event;
import me.chen.eztech.model.User;
import me.chen.eztech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
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
