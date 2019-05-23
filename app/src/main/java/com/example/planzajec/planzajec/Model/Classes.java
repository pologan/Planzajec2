package com.example.planzajec.planzajec.Model;

public class Classes {

    private String name;
    private String place;
    private String teacher;
    private String startTime;
    private String endTime;
    private String classType;
    private String weekDay;
    private int id;

    public Classes() {
    }

    public Classes(String name, String place, String teacher, String startTime, String endTime, String classType, String weekDay, int id) {
        this.name = name;
        this.place = place;
        this.teacher = teacher;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classType = classType;
        this.weekDay = weekDay;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
