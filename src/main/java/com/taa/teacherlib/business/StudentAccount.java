package com.taa.teacherlib.business;

import javax.persistence.*;

@Entity
public class StudentAccount {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    public StudentAccount() {}

    public StudentAccount(String username, String password, Student student) {
        this.username = username;
        this.password = password;
        this.student = student;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
