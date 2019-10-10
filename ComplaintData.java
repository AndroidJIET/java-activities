package com.example.grievancesystem;

public class ComplaintData {

    private String title;
    private String description;
    private String date;
    private String upVote;
    private String status;

    public ComplaintData(String title, String description, String date, String upVote, String status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.upVote = upVote;
        this.status = status;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    String getDate() {
        return date;
    }

    String getUpVote() {
        return upVote;
    }

    String getStatus() {
        return status;
    }



}
