package me.chen.eztech.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String event;
    private String comment;
    private Timestamp eventTime;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String eventType;   // Subject

}
