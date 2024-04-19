package com.ipact.ipact_23;

public class User {

    String paperTitle, author, date, status, paperID, mode, sessionTitle, venue, time;

    public User() {
    }

    public User(String paperTitle, String paperID, String author, String date, String status, String mode, String sessionTitle, String venue, String time) {
        this.paperTitle = paperTitle;
        this.author = author;
        this.date = date;
        this.status = status;
        this.paperID = paperID;
        this.mode = mode;
        this.sessionTitle = sessionTitle;
        this.venue = venue;
        this.time = time;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }


    public String getPaperID() {
        return paperID;
    }

    public String getMode() {
        return mode;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }
}
