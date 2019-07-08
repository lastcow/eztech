package me.chen.eztech.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Only professor can create topic, student can enroll only.
 */
@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;
    private String description;
    private Date duedate;
    private String status;

    /**
     * One professor can have multiple project
     */
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @OneToMany(mappedBy = "project")
    private List<ProjectMembers> members;

    /**
     * Project has tasks and milestone
     */
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
