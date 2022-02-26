package com.softcon.meetinguniv.main;

import android.net.Uri;

public class ChatRoomRecyclerItem {
    private String date;
    private Uri profileImage;
    private String nickname;
    private String message;
    private String time;
    private String uncheck;
    private int viewType;

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Uri getProfileImage() { return profileImage; }
    public void setProfileImage(Uri profileImage) {this.profileImage = profileImage; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getUncheck() { return uncheck; }
    public void setUncheck(String uncheck) { this.uncheck = uncheck; }

    public int getViewType() {
        return viewType;
    }
    public void setViewType(int viewType) { this.viewType = viewType; }
}
