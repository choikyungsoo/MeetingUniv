package com.example.meetinguniv.login;

public class User {
    private boolean meetingUnivAgreementCheckbox;
    private boolean personalInfoAgreementCheckbox;
    private boolean locationInfoAgreementCheckbox;
    private boolean promotionInfoAgreementCheckbox;

    public User() {
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
}
