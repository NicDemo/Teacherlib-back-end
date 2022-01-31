package com.taa.teacherlib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taa.teacherlib.business.Student;

import java.util.Date;

public class TeacherAppointmentDTO {

    private Long id;
    private String name;
    private String description;
    private Date start;
    private Date end;

    @JsonIgnoreProperties("appointments")
    private Student student;

    public TeacherAppointmentDTO(){}

    public TeacherAppointmentDTO(Long id, String name, String description, Date start, Date end, Student student) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
