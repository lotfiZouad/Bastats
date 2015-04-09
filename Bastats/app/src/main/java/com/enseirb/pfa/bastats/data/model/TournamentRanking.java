package com.enseirb.pfa.bastats.data.model;


public class TournamentRanking {
    public static final int NO_ID=-1;

    private int id;
    private int teamId;
    private int place;
    private String ruleTeam;
    private int tournamentId;
    private int points;

    public TournamentRanking(){

    }

    public TournamentRanking(int teamId, int tournamentId, int place, int points){
        setId(NO_ID);
        setTeamId(teamId);
        setTournamentId(tournamentId);
        setPlace(place);
        setPoints(points);
        setRuleTeam("");
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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getRuleTeam() {
        return ruleTeam;
    }

    public void setRuleTeam(String ruleTeam) {
        this.ruleTeam = ruleTeam;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString(){
        return "Place: "+getPlace()+" Team: "+ getTeamId()+" Points:"+getPoints();
    }
}
