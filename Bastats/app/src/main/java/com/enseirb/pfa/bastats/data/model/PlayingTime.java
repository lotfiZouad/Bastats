package com.enseirb.pfa.bastats.data.model;

public class PlayingTime {

    private static final int NO_ID = -1;

    private int id;
	private int matchId;
	private int numberQT;
    private String chronometer;
	private int nbFaultTeamA;
    private int nbFaultTeamB;
    private String title;

    //constructor
    public PlayingTime(){
        setId(NO_ID);
        setChronometer("");
        setNumberQT(NO_ID);
        setMatchId(NO_ID);
        setTitle("");
        setNbFaultTeamA(NO_ID);
        setNbFaultTeamB(NO_ID);
    }

	public PlayingTime(int numberQT, String chronometer, int nbFaultTeamA, int nbFaultTeamB) {
        this.setNumberQT(numberQT);
        this.setChronometer(chronometer);
        this.setNbFaultTeamA(nbFaultTeamA);
        this.setNbFaultTeamB(nbFaultTeamB);
	}

    public PlayingTime(int matchId, int numberQT, String chronometer, int nbFaultTeamA, int nbFaultTeamB) {
        this.setNumberQT(numberQT);
        this.setChronometer(chronometer);
        this.setNbFaultTeamA(nbFaultTeamA);
        this.setNbFaultTeamB(nbFaultTeamB);
        this.matchId=matchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getNumberQT() {
        return numberQT;
    }

    public void setNumberQT(int numberQT) {
        this.numberQT = numberQT;
    }

    public int getNbFaultTeamA() {
        return nbFaultTeamA;
    }

    public void setNbFaultTeamA(int nbFaultTeamA) {
        this.nbFaultTeamA = nbFaultTeamA;
    }

    public int getNbFaultTeamB() {
        return nbFaultTeamB;
    }

    public void setNbFaultTeamB(int nbFaultTeamB) {
        this.nbFaultTeamB = nbFaultTeamB;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return "QT: "+ numberQT +"\tChrono: "+ getChronometer()+"\tFautes A: "+ nbFaultTeamA +
                "\tFautes B: "+ getNbFaultTeamB()+"\tMatchId: " +getMatchId()+"\tId: "+ getId();
    }

    public String getChronometer() {
        return chronometer;
    }

    public void setChronometer(String chronometer) {
        this.chronometer = chronometer;
    }
}