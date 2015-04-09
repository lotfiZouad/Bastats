package com.enseirb.pfa.bastats.data.model;

/**
 * Created by rchabot on 25/01/15.
 */
public class Match {
    public static int NO_ID = -1;
    public static int FRIENDLY = -2;

    public static final int RESULT_DRAW = 0;
    public static final int MATCH_NOT_PLAYED = -1;

    private int id;
    private String title;
    private String date;
    private int formationTeamAId;
    private int formationTeamBId;
    private String ruleFormationA;
    private String ruleFormationB;
    private int result;
    private int scoreTeamA;
    private int scoreTeamB;
    private int phaseId;
    private String refereeField;
    private String refereeAssistant;

    //Constructor
    public Match(){

    }



    public Match(Match match){
        setId(match.getId());
        setTitle(match.getTitle());
        setDate(match.getDate());
        setFormationTeamA(match.getFormationTeamA());
        setFormationTeamB(match.getFormationTeamB());
        setPhaseId(match.getPhaseId());
        setResult(match.getResult());
        setScoreTeamA(match.getScoreTeamA());
        setScoreTeamB(match.getScoreTeamB());
    }



    public Match(String title, String date, int fA, int fB, int phaseId){
        setId(NO_ID);
        setTitle(title);
        setDate(date);
        setFormationTeamA(fA);
        setFormationTeamB(fB);
        setPhaseId(phaseId);
        setResult(MATCH_NOT_PLAYED);
        setScoreTeamA(0);
        setScoreTeamB(0);
    }

    public Match(int formationTeamAId, int formationTeamBId){
        setFormationTeamA(formationTeamAId);
        setFormationTeamB(formationTeamBId);
    }

    public Match(String title){
        setTitle(title);
        setFormationTeamA(NO_ID);
        setFormationTeamB(NO_ID);
    }
    public Match(String title, int phaseId){
        setId(NO_ID);
        setTitle(title);
        setDate(date);
        setFormationTeamA(NO_ID);
        setFormationTeamB(NO_ID);
        setPhaseId(phaseId);
        setResult(MATCH_NOT_PLAYED);
        setScoreTeamA(0);
        setScoreTeamB(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFormationTeamA() {
        return formationTeamAId;
    }

    public void setFormationTeamA(int formationTeamA) {
        this.formationTeamAId = formationTeamA;
    }

    public int getFormationTeamB() {
        return formationTeamBId;
    }

    public void setFormationTeamB(int formationTeamB) {
        this.formationTeamBId = formationTeamB;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScoreTeamA() {
        return scoreTeamA;
    }

    public void setScoreTeamA(int scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    public int getScoreTeamB() {
        return scoreTeamB;
    }

    public void setScoreTeamB(int scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public String getRuleFormationA() {
        return ruleFormationA;
    }

    public void setRuleFormationA(String ruleFormationA) {
        this.ruleFormationA = ruleFormationA;
    }

    public String getRuleFormationB() {
        return ruleFormationB;
    }

    public void setRuleFormationB(String ruleFormationB) {
        this.ruleFormationB = ruleFormationB;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString(){
        return "MatchId: "+getId()+" Phase:"+getPhaseId()+" TeamAid: "+ getFormationTeamA()+" scoreA "+ getScoreTeamA()
        +" vs "+ getScoreTeamB()+" TeamBid: "+ getFormationTeamB()+" Result: "+ getResult()+"\n";
    }

    public String getRefereeField() {
        return refereeField;
    }

    public void setRefereeField(String refereeField) {
        this.refereeField = refereeField;
    }

    public String getRefereeAssistant() {
        return refereeAssistant;
    }

    public void setRefereeAssistant(String refereeAssistant) {
        this.refereeAssistant = refereeAssistant;
    }
}
