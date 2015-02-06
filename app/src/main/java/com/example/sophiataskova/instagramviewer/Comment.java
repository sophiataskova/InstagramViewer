package com.example.sophiataskova.instagramviewer;

public class Comment {

    public String username;
    public String commentString;

    public Comment(String commenter, String comment) {
        username = commenter;
        commentString = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentString() {
        return commentString;
    }
}
