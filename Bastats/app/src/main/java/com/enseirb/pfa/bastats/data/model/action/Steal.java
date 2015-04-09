package com.enseirb.pfa.bastats.data.model.action;

import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.PlayingTime;


public class Steal extends Action{
    private static int NO_ID = -1;

    private int id;
    private int targetedPlayer;

    public Steal() {
        setPlayingTime(NO_ID);
        setActorPlayer(NO_ID);
        setComment("");
        setTargetedPlayer(NO_ID);
    }

    public Steal(PlayingTime time, Player playerActor, Player playerTargeted) {
        setPlayingTime(time.getId());
        setActorPlayer(playerActor.getId());
        setComment("");
        setTargetedPlayer(playerTargeted.getId());
    }

    public Steal(PlayingTime temps, Player playerActor) {
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

