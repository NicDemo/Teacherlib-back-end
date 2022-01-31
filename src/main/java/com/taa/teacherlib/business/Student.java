package com.taa.teacherlib.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)

    private Long id;
    private String name;
    private String mail;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("student")
    private List<Appointment> appointments = new ArrayList<>() ;

    public Student(){}

    public Student(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.appointments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", appointments=" + appointments +
                '}';
    }

    public void addAppointment(Appointment a){
        this.appointments.add(a);
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
