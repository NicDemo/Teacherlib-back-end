package com.taa.teacherlib.dto;

import java.util.Date;

public class AppointmentDTO {


    private Long teacherId;
    private String name;
    private String description;
    private Date start;
    private Date end;


    public AppointmentDTO(Long teacherId, String name, String description, Date start, Date end) {
        this.teacherId = teacherId;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return start;
    }

    public void setStartDate(Date startDate) {
        this.start = startDate;
    }

    public Date getEndDate() {
        return end;
    }

    public void setEndDate(Date endDate) {
        this.end = endDate;
    }
}
