package com.taa.teacherlib.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class Appointment {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @ManyToOne
    @JsonIgnoreProperties({"appointments", "availabilities"})
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties("appointments")
    private Student student;

    public Appointment(){}

    public Appointment(String name, String description, Date start, Date end, Teacher teacher, Student student) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.student = student;
    }

    public long calculateAppointmentDuration() {
        long diffInMillies = Math.abs(end.getTime() - start.getTime());
        long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", teacher=" + teacher +
                '}';
    }
}
