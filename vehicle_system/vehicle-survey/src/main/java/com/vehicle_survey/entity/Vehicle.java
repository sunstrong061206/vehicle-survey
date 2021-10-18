package com.vehicle_survey.entity;

public class Vehicle {
    private String license;
    private Integer status;
    private Long begin;
    private Long end;
    private Integer type;
    private Integer peopleNum;
    private String emission;
    private String manageNo;
    private String manageName;
    private String imgPath;
    private Double price;
    private Long lastProtectTime;
    private String endMsg;

    public Vehicle(String license, long lastProtectTime) {
        this.license = license;
        this.lastProtectTime = lastProtectTime;
    }

    public Vehicle(String license, int type, int peopleNum, String emission, String manageNo, String imgPath, double price) {
        this.license = license;
        this.type = type;
        this.peopleNum = peopleNum;
        this.emission = emission;
        this.manageNo = manageNo;
        this.imgPath = imgPath;
        this.price = price;
        this.lastProtectTime = System.currentTimeMillis();
        this.status = 1;
        this.begin = System.currentTimeMillis();

    }

    public Vehicle(String license, Integer status, Long begin, Long end, Integer type, Integer peopleNum, String emission, String manageNo, String manageName, String imgPath, Double price, Long lastProtectTime, String endMsg) {
        this.license = license;
        this.status = status;
        this.begin = begin;
        this.end = end;
        this.type = type;
        this.peopleNum = peopleNum;
        this.emission = emission;
        this.manageNo = manageNo;
        this.manageName = manageName;
        this.imgPath = imgPath;
        this.price = price;
        this.lastProtectTime = lastProtectTime;
        this.endMsg = endMsg;
    }

    public String getEndMsg() {
        return endMsg;
    }

    public void setEndMsg(String endMsg) {
        this.endMsg = endMsg;
    }

    public Vehicle(String license, Integer status, Integer peopleNum, String emission, String manageName, Long lastProtectTime) {
        this.license = license;
        this.status = status;
        this.peopleNum = peopleNum;
        this.emission = emission;
        this.manageName = manageName;
        this.lastProtectTime = lastProtectTime;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getEmission() {
        return emission;
    }

    public void setEmission(String emission) {
        this.emission = emission;
    }

    public String getManageNo() {
        return manageNo;
    }

    public void setManageNo(String manageNo) {
        this.manageNo = manageNo;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getLastProtectTime() {
        return lastProtectTime;
    }

    public void setLastProtectTime(Long lastProtectTime) {
        this.lastProtectTime = lastProtectTime;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }
}
