package com.enseirb.pfa.bastats.data.model;


public class Formation {
    private static int NO_ID = -1;

    public static final int DEFAULT_FORMATION = 1;
    public static final int FORMATION_MATCH = 0;
    public static final int LAST = 2;

    private int id;
    private int teamId;
    private int defaultValue;

    public Formation(){
        setTeamId(NO_ID);
        setId(NO_ID);
        setDefaultValue(FORMATION_MATCH);
    }

    public Formation(int teamId, int isDefaultValue){
        setTeamId(teamId);
        setDefaultValue(isDefaultValue);
        setId(NO_ID);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }
}
