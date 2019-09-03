package me.chen.eztech.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;

    @Column(unique = true)
    private String email;
    private String contactNumber;



    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * Professor 1 : M Topics
     */
    @OneToMany(mappedBy = "member")
    private List<ProjectMembers> projects;

    @OneToMany(mappedBy = "tutor")
    private List<User> students;

    @ManyToOne
    @JoinColumn(name = "turor_id")
    private User tutor;

    @OneToMany(mappedBy = "owner")
    private List<Event> events;

    /**
     * I have many questions
     */
    @OneToMany(mappedBy = "owner")
    private List<Question> questions;

    /**
     * I can answer many questions
     */
    @OneToMany(mappedBy = "answer")
    private List<Question> questionsAnswered;


}
