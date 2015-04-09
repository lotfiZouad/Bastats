package com.enseirb.pfa.bastats.data.model;

public class Tournament {
    private static int    NO_ID = -1;

    private int    id;
    private String title;
    private String place;
    private int nbTeamMax;
    

    //constructor
    public Tournament() {
        setId(NO_ID);
        setPlace("");
        setNbTeamMax(NO_ID);
        setPlace("");
    }

    public Tournament(String title, String place) {
        setId(NO_ID);
        setTitle(title);
        setPlace(place);
        setNbTeamMax(NO_ID);
    }


    public Tournament(String title, String place, int nbTeamMax) {
        setId(NO_ID);
        setTitle(title);
        setPlace(place);
        setNbTeamMax(nbTeamMax);
    }

    public Tournament(Tournament t){
        setId(t.getId());
        setTitle(t.getTitle());
        setPlace(t.getPlace());
        setNbTeamMax(t.getNbTeamMax());
    }

    //getter
    public String getTitle() {
	return this.title;
    }

    public String getPlace() {
	return this.place;
    }

    public int getNbTeamMax() {
	return this.nbTeamMax;
    }

    //setter
    public void setTitle(String title) {
	this.title = title;
    }

    public void setPlace(String place) {
	this.place = place;
    }

    public void setNbTeamMax(int nbTeamMax) {
	this.nbTeamMax = nbTeamMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int i){
	this.id = i;
    }


    @Override
    public String toString(){
        return "Tournament: "+ getTitle()+" ("+ getPlace()+")"+"\tId: "+getId();
    }


}
