package com.enseirb.pfa.bastats.data.model;


public class Player {

    public static final int NO_ID = -1;
    public static final String NO_NUM = "";

    private int     id;
    private String surname;
    private String firstName;
    private String  pseudo;
    private String number;


    public Player(){
        this.id = NO_ID;
        this.surname = "";
        this.firstName = "";

        setNumber("");
    }

    public Player(Player player){
        setId(player.getId());
        setSurname(player.getSurname());
        setFirstName(player.getFirstName());
        setNumber(player.getNumber());
        setPseudo(player.getPseudo());
    }



    public Player(String surname, String firstName, String number) {
        this.id = NO_ID;
        this.surname = surname;
        this.firstName = firstName;
        this.pseudo = "";

        setNumber(number);
    }

    public Player(String surname, String firstName, String pseudo, String number) {
        this.id = NO_ID;
        this.surname = surname;
        this.firstName = firstName;
        this.pseudo = pseudo;

        setNumber(number);
    }

    public Player(String surname, String firstName) {
        this.id = NO_ID;
        this.surname = surname;
        this.firstName = firstName;
        this.pseudo = "";

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString(){
        return "Number: "+ getNumber()+ "\tSurname: "+ surname +"\tFirstName: "+ firstName +"\tPseudo :"+getPseudo()+"\tId: "+id;

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
