package com.enseirb.pfa.bastats.data.model;


public class Stat {

    private int idJoueur;
    private String playerName;
    private int nbAssist;
    private int nbFaultOff;
    private int nbFaultDef;
    private int nbBlock;
    private int nbReboundOff;
    private int nbReboundDef;
    private int nbSteal;
    private int nbTurnOver;
    private int nbFtS;
    private int nbFtF;
    private int nbShoot2S;
    private int nbShoot2F;
    private int nbShoot3S;
    private int nbShoot3F;

    public Stat(int id) {
        this.idJoueur = id;
    }


    // Getters

    public int getIdJoueur() {
        return this.idJoueur;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getNbAssist() {
        return this.nbAssist;
    }

    public int getNbFaultOff() {
        return this.nbFaultOff;
    }

    public int getNbFaultDef() {
        return this.nbFaultDef;
    }

    public int getNbBlock() {
        return this.nbBlock;
    }

    public int getNbReboundOff() {
        return this.nbReboundOff;
    }

    public int getNbReboundDef() {
        return this.nbReboundDef;
    }

    public int getNbSteal() {
        return this.nbSteal;
    }

    public int getNbTurnOver() {
        return this.nbTurnOver;
    }

    public int getNbFtS() { return this.nbFtS; }

    public int getNbFtF() { return this.nbFtF; }

    public int getNbShoot2S() {
        return this.nbShoot2S;
    }

    public int getNbShoot2F() {
        return this.nbShoot2F;
    }

    public int getNbShoot3S() {
        return this.nbShoot3S;
    }

    public int getNbShoot3F() {
        return this.nbShoot3F;
    }



    //Setters

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public void setNbAssist(int nb) {
        this.nbAssist = nb;
    }

    public void setNbFaultOff(int nb) {
        this.nbFaultOff = nb;
    }

    public void setNbFaultDef(int nb) {
        this.nbFaultDef = nb;
    }

    public void setNbBlock(int nb) {
        this.nbBlock = nb;
    }

    public void setNbReboundOff(int nb) {
        this.nbReboundOff = nb;
    }

    public void setNbReboundDef(int nb) {
        this.nbReboundDef = nb;
    }

    public void setNbSteal(int nb) {
        this.nbSteal = nb;
    }

    public void setNbTurnOver(int nb) {
        this.nbTurnOver = nb;
    }

    public void setNbFtS(int nb) { this.nbFtS = nb; }

    public void setNbFtF(int nb) { this.nbFtF = nb; }

    public void setNbShoot2S(int nb) {
        this.nbShoot2S = nb;
    }

    public void setNbShoot2F(int nb) {
        this.nbShoot2F = nb;
    }

    public void setNbShoot3S(int nb) {
        this.nbShoot3S = nb;
    }

    public void setNbShoot3F(int nb) {
        this.nbShoot3F = nb;
    }

    @Override
    public String toString(){
        return getPlayerName()+", 2s:"+getNbShoot2S()+", 2f:"+getNbShoot2F()+", "+getNbShoot3S()+", "
                +getNbShoot3F()+", blk:"+ getNbBlock()+", fts"+ getNbFaultDef()
                +", ast:"+ getNbAssist()+", stl:"+ getNbSteal();
    }
}
