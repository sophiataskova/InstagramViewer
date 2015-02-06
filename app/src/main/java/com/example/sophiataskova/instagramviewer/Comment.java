package com.example.sophiataskova.instagramviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {

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

    public Comment(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.username = data[0];
        this.commentString = data[1];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.username,
                this.commentString});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
