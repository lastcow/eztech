package me.chen.eztech.dto;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionDto {

    private String question;
    private Timestamp timestamp;
    private String timeStr;
    private String username;
    private String userImgHash;
}
