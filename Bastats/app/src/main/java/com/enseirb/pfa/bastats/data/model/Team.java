package com.enseirb.pfa.bastats.data.model;

public class Team {
    private static int    NO_ID = -1;

    private int    id;
    private String acronym;
    private String name;
    private String color;
    private String photo;
    

    //constructor
    public Team() {
        setId(NO_ID);
        setColor("");
        setPhoto("");
        setColor("");
        setAcronym("");
    }


    public Team(Team team){
        setId(team.getId());
        setColor(team.getColor());
        setPhoto(team.getPhoto());
        setAcronym(team.getAcronym());
        setName(team.getName());
    }

    public Team(String name) {
        setId(NO_ID);
        setName(name);
        setAcronym("");
        setColor("");
        setPhoto("");
    }

    public Team(String name, String color) {
        setId(NO_ID);
        setName(name);

        setColor(color);
        setPhoto("");
    }

    public Team(String name, String color, String photo) {
	    this.name = name;
	    this.color = color;
	    this.photo = photo;
    }

    //getter
    public String getName() {
	return this.name;
    }

    public    String getColor() {
	return this.color;
    }

    public String getPhoto() {
	return this.photo;
    }

    //setter
    public void setName(String name) {
	this.name = name;
    }

    public void setColor(String color) {
	this.color = color;
    }

    public void setPhoto(String photo) {
	this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int i){
	this.id = i;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public String toString(){
        return "Team: "+ getName()+" ("+ getAcronym()+")";
    }


}
