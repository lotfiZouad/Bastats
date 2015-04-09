package com.enseirb.pfa.bastats.data.model;

public class PhaseResultsTable {

    public static final int IN_PROGRESS = 2;
    public static final int FINISHED = 3;
    public static final int PENDING =1;


    private int NO_ID = -1;

	private int id;
	private int phaseId;
	private int state;

	public PhaseResultsTable() {
	}

    public PhaseResultsTable(int phaseId){
        setPhaseId(phaseId);
        setId(NO_ID);
        setState(IN_PROGRESS);
    }

	public int getPhaseId() {
		return this.phaseId;
	}

	public int getState() {
		return this.state;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public void setState(int state) {
		this.state = state;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}