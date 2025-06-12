package com.exam.entity;

import lombok.Data;

@Data
public class Login {

    private Integer username;

    public String getPassword() {
        return password;
    }

    public Login(Integer username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    private String password;


}
