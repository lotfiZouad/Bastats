package com.enseirb.pfa.bastats.data.model.action;


import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.PlayingTime;

public class Action {
    public static final int TYPE_GENERIC= 0;
    public static final int TYPE_SHOOT=1;
    public static final int TYPE_FAULT=2;
    public static final int TYPE_ASSIST=3;
    public static final int TYPE_REBOUND= 4;
    public static final int TYPE_BLOCK= 5;
    public static final int TYPE_STEAL= 6;

    public static final int NO_ID = -1;
	
    private int        id;
    private int        playingTime;
    private int        actorPlayer;
    private int        type;
    private String     comment;


    //constructor
    
    public Action() {
	    setActionId(NO_ID);
        setPlayingTime(NO_ID);
        setActorPlayer(NO_ID);
    }
    

    public Action(Action a){
	    this.id=a.id;
        this.playingTime =a.playingTime;
        this.actorPlayer =a.actorPlayer;
        this.type=a.type;
        this.comment =a.comment;

    }

    public Action(Player actor, PlayingTime playingTime) {
	    this.setActorPlayer(actor.getId());
        this.setPlayingTime(playingTime.getId());
    }


    public int getActionId() {
        return id;
    }

    public void setActionId(int id) {
        this.id = id;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getActorPlayer() {
        return actorPlayer;
    }

    public void setActorPlayer(int actorPlayer) {
        this.actorPlayer = actorPlayer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){
        return "PlayingTimeId: "+ getPlayingTime() +"\tActorPlayerId: "+ getActorPlayer()+"\tId: "+getActionId();
    }
}

