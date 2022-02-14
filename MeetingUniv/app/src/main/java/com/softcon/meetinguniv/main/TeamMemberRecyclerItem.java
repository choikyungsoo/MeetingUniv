package com.softcon.meetinguniv.main;

import android.net.Uri;

import java.io.Serializable;

public class TeamMemberRecyclerItem implements Serializable {

    private Uri memProfile;

    public void setMemProfile(Uri memProfile) {
        this.memProfile = memProfile;
    }

    public Uri getMemProfile() {
        return memProfile;
    }
}
