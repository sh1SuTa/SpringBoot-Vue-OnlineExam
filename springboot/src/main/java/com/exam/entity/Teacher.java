package com.exam.entity;

import lombok.Data;

@Data
public class Teacher {
    private Integer teacherId;

    private String teacherName;

    private String institute;//学院

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;//身份证号

    private String type;

    private String role;//0管理员，1老师，2学生
}