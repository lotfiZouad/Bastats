package com.enseirb.pfa.bastats.data.model;

public class PhaseGroupCompetition {

    public static final int NO_ID = -1;

    private int id;

    private int phaseId;
	private int nbGroupCompetition;
	private int nbPeriodMatch;
	private String durationPeriodMatch;

    //constructor
	public PhaseGroupCompetition() {
        setId(NO_ID);
        setPhaseId(NO_ID);
        setDurationPeriodMatch("00:00");
        setNbGroupCompetition(0);
        setNbPeriodMatch(0);
	}

    public PhaseGroupCompetition(int phaseId, int nbGroupCompetition, int nbPeriods, String durationPeriod) {
        setId(NO_ID);
        setNbGroupCompetition(nbGroupCompetition);
        setNbPeriodMatch(nbPeriods);
        setDurationPeriodMatch(durationPeriod);
        setPhaseId(phaseId);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public int getNbGroupCompetition() {
        return nbGroupCompetition;
    }

    public void setNbGroupCompetition(int nbGroupCompetition) {
        this.nbGroupCompetition = nbGroupCompetition;
    }

    public int getNbPeriodMatch() {
        return nbPeriodMatch;
    }

    public void setNbPeriodMatch(int nbPeriodMatch) {
        this.nbPeriodMatch = nbPeriodMatch;
    }

    public String getDurationPeriodMatch() {
        return durationPeriodMatch;
    }

    public void setDurationPeriodMatch(String durationPeriodMatch) {
        this.durationPeriodMatch = durationPeriodMatch;
    }
}