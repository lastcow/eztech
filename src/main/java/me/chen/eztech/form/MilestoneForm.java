package me.chen.eztech.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class MilestoneForm {

    private String projectId;
    @NotBlank(message = "description can't empty!")
    private String desc;
    private Date deadline;

}
