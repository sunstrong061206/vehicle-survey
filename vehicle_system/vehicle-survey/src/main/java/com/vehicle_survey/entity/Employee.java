package com.vehicle_survey.entity;

public class Employee {
    private String no;
    private String name;
    private String phone;
    private String position;
    private String dept;
    private String facedata;
    private String wechat;
    private String bossNo;
    private Integer age;
    private Integer status;
    private String password;

    public Employee(String no, String name, String position, String wechat, String password) {
        this.no = no;
        this.name = name;
        this.position = position;
        this.wechat = wechat;
        this.password = password;
    }

    public Employee(String no, String name, String phone, String position, String dept, Integer age) {
        this.no = no;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.dept = dept;
        this.age = age;
    }

    public Employee(String no, String name, String phone, String position, String dept, String bossNo) {
        this.no = no;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.dept = dept;
        this.bossNo = bossNo;
    }

    public Employee(String no, String name, String phone, String position, String dept, String facedata, String wechat, String bossNo, Integer age, Integer status, String password) {
        this.no = no;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.dept = dept;
        this.facedata = facedata;
        this.wechat = wechat;
        this.bossNo = bossNo;
        this.age = age;
        this.status = status;
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getFacedata() {
        return facedata;
    }

    public void setFacedata(String facedata) {
        this.facedata = facedata;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getBossNo() {
        return bossNo;
    }

    public void setBossNo(String bossNo) {
        this.bossNo = bossNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPwd() {
        return password;
    }

    public void setPwd(String password) {
        this.password = password;
    }


}
