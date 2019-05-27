package me.chen.eztech.dto;


import lombok.Data;

import java.sql.Date;

@Data
public class ProjectDto {

    private String id;
    private String projectName;
    private String status;
    private Date deadLine;
    private String projectDesc;
    private String students;

    private int todos;
}
