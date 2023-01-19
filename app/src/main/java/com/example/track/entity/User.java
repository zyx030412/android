package com.example.track.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private String birthday;
    private String address;
    private String last_login;
    private String date_joined;
    private int type;
    private String integral;
    private String sign_in;
    private String head;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getSign_in() {
        return sign_in;
    }

    public void setSign_in(String sign_in) {
        this.sign_in = sign_in;
    }

    public String getUsername() {
        return phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", phone:'" + phone + '\'' +
                ", sex:'" + sex + '\'' +
                ", birthday:'" + birthday + '\'' +
                ", address:'" + address + '\'' +
                ", last_login:'" + last_login + '\'' +
                ", date_joined:'" + date_joined + '\'' +
                ", type:" + type +
                ", integral:'" + integral + '\'' +
                ", sign_in:'" + sign_in + '\'' +
                ", head:'" + head + '\'' +
                '}';
    }
}