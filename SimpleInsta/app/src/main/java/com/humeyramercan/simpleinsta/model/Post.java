package com.humeyramercan.simpleinsta.model;

public class Post {
    private String dowloandUrl;
    private String userComment;
    private String userEmail;

    public Post(String dowloandUrl,  String userEmail,String userComment) {
        this.dowloandUrl = dowloandUrl;
        this.userComment = userComment;
        this.userEmail = userEmail;
    }

    public String getDowloandUrl() {
        return dowloandUrl;
    }

    public String getUserComment() {
        return userComment;
    }

    public String getUserEmail() {
        return userEmail;
    }

}
