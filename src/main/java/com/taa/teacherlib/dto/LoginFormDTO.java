package com.taa.teacherlib.dto;

public class LoginFormDTO {
    private String name;
    private String mail;
    private String username;
    private String password;


    public LoginFormDTO(String name, String mail, String username, String password) {
        this.name = name;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
}
