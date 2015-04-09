package com.enseirb.pfa.bastats.data.model;


public class GroupCompetitionRanking {
    private static int NO_ID = -1;

    private int groupCompetitionId;
    private int teamId;
    private int points;


    public GroupCompetitionRanking(){
        groupCompetitionId =NO_ID;
        teamId =NO_ID;
        points=0;


    }



    public int getGroupCompetitionId() {
        return groupCompetitionId;
    }

    public void setGroupCompetitionId(int groupCompetitionId) {
        this.groupCompetitionId = groupCompetitionId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
