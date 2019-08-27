package me.chen.eztech.dto;

import lombok.Data;
import me.chen.eztech.model.Event;

@Data
public class EventDto {

    private String username;
    private String eventType;
    private String eventDesc;

}
