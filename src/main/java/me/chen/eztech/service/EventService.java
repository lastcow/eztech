package me.chen.eztech.service;

import me.chen.eztech.model.Event;
import me.chen.eztech.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Event save(Event event){
        return eventRepository.save(event);
    }

}
