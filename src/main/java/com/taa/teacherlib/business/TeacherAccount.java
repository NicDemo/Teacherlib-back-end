package com.taa.teacherlib.business;

import javax.persistence.*;

@Entity
public class TeacherAccount {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    public TeacherAccount() {}

    public TeacherAccount(String username, String password, Teacher teacher) {
        this.username = username;
        this.password = password;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
