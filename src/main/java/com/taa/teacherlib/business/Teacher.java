package com.taa.teacherlib.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Entity
public class Teacher {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String mail;

    @OneToMany(mappedBy = "teacher", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade=CascadeType.ALL)
    @JsonBackReference
    @JsonIgnoreProperties("teacher")
    private List<Appointment> appointments = new ArrayList<>();

    public Teacher(){}

    public Teacher(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.availabilities = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment a){
        this.appointments.add(a);
    }

    public void addAvailability(Availability a) {
        if(!this.availabilities.contains(a)){
            this.availabilities.add(a);
        }
    }

    public void removeAvailability(Availability a) {
        this.availabilities.remove(a);
        for (Availability av : this.availabilities){
            System.out.print(av);
        }
    }

    public void removeOutDatedAvailabilities(){
        Iterator i = this.availabilities.iterator() ;
        while(i.hasNext()){
            Availability a = (Availability) i.next();
            if(!a.isValid()){
                i.remove();
            }
        }
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        availabilities = availabilities;
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
