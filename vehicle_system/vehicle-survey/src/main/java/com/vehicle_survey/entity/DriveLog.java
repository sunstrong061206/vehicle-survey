package com.vehicle_survey.entity;

import java.util.ArrayList;

public class DriveLog {
    private Integer driveId;
    private Integer assignId;
    private String driveLicense;
    private Long time;
    private Long driveBegin;
    private Long driveEnd;
    private String surveyNo;
    private String surveyName;
    private String surveyPoint;
    private ArrayList<Place> route;
    // 路径的存储方式 待确认
    private Place place;

    public DriveLog(Integer driveId, String driveLicense, Long driveBegin, Long driveEnd, String surveyName, String surveyPoint) {
        this.driveId = driveId;
        this.driveLicense = driveLicense;
        this.driveBegin = driveBegin;
        this.driveEnd = driveEnd;
        this.surveyName = surveyName;
        this.surveyPoint = surveyPoint;
    }

    public DriveLog(Integer driveId, Long time) {
        this.driveId = driveId;
        this.time = time;
    }


    public DriveLog(Integer assignId, String driveLicense, Long time, String surveyNo, Place place) {
        this.assignId = assignId;
        this.driveLicense = driveLicense;
        this.time = time;
        this.surveyNo = surveyNo;
        this.place = place;
    }

    public DriveLog(Integer driveId, Integer assignId, String driveLicense, Long driveBegin, Long driveEnd, String surveyNo, String surveyName, String surveyPoint, ArrayList<Place> route) {
        this.driveId = driveId;
        this.assignId = assignId;
        this.driveLicense = driveLicense;
        this.driveBegin = driveBegin;
        this.driveEnd = driveEnd;
        this.surveyNo = surveyNo;
        this.surveyName = surveyName;
        this.surveyPoint = surveyPoint;
        this.route = route;
    }

    public Integer getDriveId() {
        return driveId;
    }

    public void setDriveId(Integer driveId) {
        this.driveId = driveId;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getDriveBegin() {
        return driveBegin;
    }

    public void setDriveBegin(Long driveBegin) {
        this.driveBegin = driveBegin;
    }

    public ArrayList<Place> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Place> route) {
        this.route = route;
    }

    public Long getDriveEnd() {
        return driveEnd;
    }

    public void setDriveEnd(Long driveEnd) {
        this.driveEnd = driveEnd;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public String getDriveLicense() {
        return driveLicense;
    }

    public void setDriveLicense(String driveLicense) {
        this.driveLicense = driveLicense;
    }


    public Long gettime() {
        return time;
    }

    public void settime(Long time) {
        this.time = time;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getSurveyPoint() {
        return surveyPoint;
    }

    public void setSurveyPoint(String surveyPoint) {
        this.surveyPoint = surveyPoint;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
}
