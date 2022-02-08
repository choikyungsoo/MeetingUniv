package com.softcon.meetinguniv.main;

import android.net.Uri;

import java.io.Serializable;

public class TeamMemberRecyclerItem implements Serializable {

    private int memProfile;

    public void setMemProfile(int memProfile) {
        this.memProfile = memProfile;
    }

    public int getMemProfile() {
        return memProfile;
    }
}
