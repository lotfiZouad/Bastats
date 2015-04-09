package com.enseirb.pfa.bastats.data.model;

/**
 * Created by dohko on 13/02/15.
 */
public class GroupCompetitionMatch {
    private static int NO_ID = -1;

    private int groupCompetitionId;
    private int matchId;


    public GroupCompetitionMatch(){
        groupCompetitionId =NO_ID;
        matchId=NO_ID;


    }





    public int getGroupCompetitionId() {
        return groupCompetitionId;
    }

    public void setGroupCompetitionId(int groupCompetitionId) {
        this.groupCompetitionId = groupCompetitionId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
}
