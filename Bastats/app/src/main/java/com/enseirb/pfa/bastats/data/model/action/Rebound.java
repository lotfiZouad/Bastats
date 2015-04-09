package com.enseirb.pfa.bastats.data.model.action;

import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.PlayingTime;

public class Rebound extends Action{
    public static int NO_ID = -1;
    public static final int OFFENSIVE = 1;
    public static final int DEFENSIVE = 2;

    private int     id;
    private int nature; // OFF or DEF

    public Rebound(){
        setNature(NO_ID);
        setComment("");
    }

    public Rebound(PlayingTime time, Player player, int nature){
        setPlayingTime(time.getId());
        setActorPlayer(player.getId());
        setNature(nature);
        setComment("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    @Override
    public String toString(){
        return "Time:"+ getPlayingTime()+"\tPlayer: "+ getActorPlayer()+"\tNature: "+ getNature()+
                "\tId: "+getId();
    }
}
