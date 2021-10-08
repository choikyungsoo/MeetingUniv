package com.softcon.meetinguniv.login;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private long userID;

    private boolean meetingUnivAgreementCheckbox;
    private boolean personalInfoAgreementCheckbox;
    private boolean locationInfoAgreementCheckbox;
    private boolean promotionInfoAgreementCheckbox;

    private String schoolName;
//    private studentCard;

//    private profileImage;
    private String nickname;

    private String inviteCode;


    public UserInfo() {
//        this.meetingUnivAgreementCheckbox = false;
//        this.personalInfoAgreementCheckbox = false;
//        this.locationInfoAgreementCheckbox = false;
//        this.promotionInfoAgreementCheckbox = false;
    }

    public long getUserID() { return userID; }
    public void setUserID(long userID) {
        this.userID = userID;
    }

    public boolean isMeetingUnivAgreementCheckbox() {
        return meetingUnivAgreementCheckbox;
    }
    public void setMeetingUnivAgreementCheckbox(boolean meetingUnivAgreementCheckbox) {
        this.meetingUnivAgreementCheckbox = meetingUnivAgreementCheckbox;
    }

    public boolean isPersonalInfoAgreementCheckbox() {
        return personalInfoAgreementCheckbox;
    }
    public void setPersonalInfoAgreementCheckbox(boolean personalInfoAgreementCheckbox) {
        this.personalInfoAgreementCheckbox = personalInfoAgreementCheckbox;
    }

    public boolean isLocationInfoAgreementCheckbox() {
        return locationInfoAgreementCheckbox;
    }
    public void setLocationInfoAgreementCheckbox(boolean locationInfoAgreementCheckbox) {
        this.locationInfoAgreementCheckbox = locationInfoAgreementCheckbox;
    }

    public boolean isPromotionInfoAgreementCheckbox() {
        return promotionInfoAgreementCheckbox;
    }
    public void setPromotionInfoAgreementCheckbox(boolean promotionInfoAgreementCheckbox) {
        this.promotionInfoAgreementCheckbox = promotionInfoAgreementCheckbox;
    }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
