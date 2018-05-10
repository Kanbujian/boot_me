package com.kanbujian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "logs")
@Inheritance
@DiscriminatorColumn(name = "owner_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Log implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventType;
    @Column(columnDefinition = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date happendAt;
    @Column(columnDefinition = "CURRENT_TIMESTAMP")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getHappendAt() {
        return happendAt;
    }

    public void setHappendAt(Date happendAt) {
        this.happendAt = happendAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    private void onCreated(){
        Date currentTime = Calendar.getInstance().getTime();
        createdAt = currentTime;
        happendAt = currentTime;
    }

    @PreUpdate
    private void onUpdate(){
        happendAt = Calendar.getInstance().getTime();
    }
}
