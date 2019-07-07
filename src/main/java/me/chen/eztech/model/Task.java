package me.chen.eztech.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;
    private boolean completed;

    private boolean milestone;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
