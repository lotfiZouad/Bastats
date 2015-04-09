package com.enseirb.pfa.bastats.data.model;

/**
 * Created by dohko on 12/02/15.
 */
public class FormationPlayer {
    private static int NO_ID = -1;

    private int formationId;
    private int playerId;
    private String number;

    public FormationPlayer(){
        setFormationId(NO_ID);
        setPlayerId(NO_ID);
        setNumber("");

    }

    public FormationPlayer(Formation formation, Player player, String number){
        setNumber(number);
        setPlayerId(player.getId());
        setFormationId(formation.getId());

    }




    public int getFormationId() {
        return formationId;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
