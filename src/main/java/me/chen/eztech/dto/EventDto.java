package me.chen.eztech.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventDto {

    private String username;
    private String eventType;
    private String eventDesc;
    private Timestamp eventTime;
    private String timeStr;
    private String userImgHash;

}
