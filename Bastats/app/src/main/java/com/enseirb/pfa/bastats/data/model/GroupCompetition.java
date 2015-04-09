package com.enseirb.pfa.bastats.data.model;

public class GroupCompetition {
    private static final int NO_ID = -1;

    public static final int WAITING_STATE =1;
    public static final int CURRENT_STATE = 2;
    public static final int FINISHED_STATE =3;

	private int id;
	private String title;
	private int phaseGroupCompetitionId;
	private int state;
	private int victoryPoints;
	private int defeatPoints;
	private int drawPoints;
	private int goalAverageMaxDifference;


    //constructor
	public GroupCompetition() {

	}

    public GroupCompetition(GroupCompetition groupCompetition){
        setTitle(groupCompetition.getTitle());
        setPhaseGroupCompetitionId(groupCompetition.getPhaseGroupCompetitionId());
        setVictoryPoints(groupCompetition.getVictoryPoints());
        setDrawPoints(groupCompetition.getDrawPoints());
        setDefeatPoints(groupCompetition.getDefeatPoints());
        setState(groupCompetition.getState());
        setGoalAverageMaxDifference(groupCompetition.getGoalAverageMaxDifference());
        setId(groupCompetition.getId());
    }

    public GroupCompetition(String name, int phaseGroupCompetitionId, int victoryPoints, int drawPoints, int defaitPoints, int state){
        setTitle(name);
        setPhaseGroupCompetitionId(phaseGroupCompetitionId);
        setVictoryPoints(victoryPoints);
        setDrawPoints(drawPoints);
        setDefeatPoints(defaitPoints);
        setState(state);
        setGoalAverageMaxDifference(NO_ID);
        setId(NO_ID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhaseGroupCompetitionId() {
        return phaseGroupCompetitionId;
    }

    public void setPhaseGroupCompetitionId(int phaseGroupCompetitionId) {
        this.phaseGroupCompetitionId = phaseGroupCompetitionId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getDefeatPoints() {
        return defeatPoints;
    }

    public void setDefeatPoints(int defeatPoints) {
        this.defeatPoints = defeatPoints;
    }

    public int getDrawPoints() {
        return drawPoints;
    }

    public void setDrawPoints(int drawPoints) {
        this.drawPoints = drawPoints;
    }

    public int getGoalAverageMaxDifference() {
        return goalAverageMaxDifference;
    }

    public void setGoalAverageMaxDifference(int goalAverageMaxDifference) {
        this.goalAverageMaxDifference = goalAverageMaxDifference;
    }

    @Override
    public String toString(){
        return "Name: "+ getTitle()+" id:"+getId()+" state:"+ getState()
                +"\n V:"+ getVictoryPoints()+ " N:"+ getDrawPoints()+" D:"+ getDefeatPoints()
                +"\n phase: "+ getPhaseGroupCompetitionId();
    }
}