package com.enseirb.pfa.bastats.data.model.action;

import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.PlayingTime;

public class Shoot extends Action{

    public static int NO_ID = -1;
    public static final int SUCCESSFUL = 1;
    public static final int FAILED = 0;

    private int     id;




    private int     pts; // 1pts, 2pts or 3pts
    private int successful;

    public Shoot(){
        init();

    }

    public Shoot(Action a){
       super(a);
        init();

    }

    private void init(){

        setPts(NO_ID);
        setSuccessful(NO_ID);
        setComment("");

    }



    public Shoot(PlayingTime playingTime1, Player player, int pts, int successful){
        setPlayingTime(playingTime1.getId());
        setActorPlayer(player.getId());
        setPts(pts);
        setSuccessful(successful);
        setComment("");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int value) {
        this.pts = value;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }



    @Override
    public String toString(){
        return "actionId" + getActionId()+ "\tPlayer: "+ getActorPlayer()+"\tShoot: "+getPts()+"\tSuccessful: "
                + getSuccessful()+"\tId: "+getId();
    }
}
