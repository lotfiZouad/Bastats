package com.enseirb.pfa.bastats.data.model;

public class Phase {
    public static final int TYPE_GROUP_COMPETITION = 1;
    public static final int TYPE_RESULTS_TABLE = 2;
    public static final int TYPE_FRIENDLY_MATCH = 3;

	private int id;
	private int tournamentId;
	private String title;
	private String date;
	private int temporalOrder;
	private int type; // Group competition or results table


    //constructor
	public Phase() {

	}

    public Phase(int tournamentId, int temporalOrder, String phaseName, int type){
        setTournamentId(tournamentId);
        setTemporalOrder(temporalOrder);
        setTitle(phaseName);
        setType(type);
        setDate("00/00/00");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTemporalOrder() {
        return temporalOrder;
    }

    public void setTemporalOrder(int temporalOrder) {
        this.temporalOrder = temporalOrder;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return getTitle();
    }
}