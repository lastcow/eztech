package me.chen.eztech.dto;


import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ProjectDto {

    private String id;
    private String projectName;
    private String status;
    private Date deadLine;
    private String projectDesc;
    private String students;

    private List<UserDto> users;

    private int todos;
}
