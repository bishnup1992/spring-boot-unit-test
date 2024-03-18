package com.bishnu.codewithme.task;

public class Task {
    private String name;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public Task(String name, String startDate, String endDate, String startTime, String endTime) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return name + " - Start Date: " + startDate + ", End Date: " + endDate +
                ", Start Time: " + startTime + ", End Time: " + endTime;
    }
}
