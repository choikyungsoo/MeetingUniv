package com.softcon.meetinguniv.main;

import java.util.ArrayList;

public class chooseteamDataModel {
    private String MatchingState;
    private String TeamMember;
    private String TeamName;

    public chooseteamDataModel(){

    }

    public chooseteamDataModel(String TeamName, String MatchingState, String TeamMember){
        this.TeamName = TeamName;
        this.MatchingState = MatchingState;
        this.TeamMember = TeamMember;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getMatchingState() {
        return MatchingState;
    }

    public void setMatchingState(String matchingState) {
        MatchingState = matchingState;
    }

    public String getTeamMember() {
        return TeamMember;
    }

    public void setTeamMember(String teamMember) {
        TeamMember = teamMember;
    }
}
