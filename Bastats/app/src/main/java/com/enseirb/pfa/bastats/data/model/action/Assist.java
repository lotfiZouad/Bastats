package com.enseirb.pfa.bastats.data.model.action;

import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.PlayingTime;


public class Assist extends Action{

    private static int NO_ID = -1;

    private int id;
    private int targetedPlayer;

    public Assist() {
        setPlayingTime(NO_ID);
        setActorPlayer(NO_ID);
        setComment("");
        setTargetedPlayer(NO_ID);
    }

    public Assist(PlayingTime temps, Player playerActor, Player playerTargeted) {
        setPlayingTime(temps.getId());
        setActorPlayer(playerActor.getId());
        setComment("");
        setTargetedPlayer(playerTargeted.getId());
    }

    public Assist(PlayingTime temps, Player playerActor){
        setPlayingTime(temps.getId());
        setActorPlayer(playerActor.getId());
        setComment("");
        setTargetedPlayer(NO_ID);


    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getTargetedPlayer() {
        return targetedPlayer;
    }

    public void setTargetedPlayer(int targetedPlayer) {
        this.targetedPlayer = targetedPlayer;
    }



    @Override
    public String toString(){
        return "actionId" + getActionId()+ "\tPlayer: "+ getActorPlayer()+
                "\tId: "+getId();
    }
}


