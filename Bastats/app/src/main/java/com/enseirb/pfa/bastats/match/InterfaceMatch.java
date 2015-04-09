package com.enseirb.pfa.bastats.match;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.enseirb.pfa.bastats.R;


import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTeamDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBAssistDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBBlockDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBFaultDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBReboundDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBStealDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBTurnOverDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.Assist;
import com.enseirb.pfa.bastats.data.model.action.Block;
import com.enseirb.pfa.bastats.data.model.action.Fault;
import com.enseirb.pfa.bastats.data.model.action.Rebound;
import com.enseirb.pfa.bastats.data.model.action.Steal;
import com.enseirb.pfa.bastats.data.model.action.TurnOver;
import com.enseirb.pfa.bastats.menu.CheckDB;
import com.enseirb.pfa.bastats.data.DAO.DBFormationDAO;
import com.enseirb.pfa.bastats.data.DAO.DBMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBActionDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBShootDAO;
import com.enseirb.pfa.bastats.data.model.Match;
import com.enseirb.pfa.bastats.data.model.PlayingTime;
import com.enseirb.pfa.bastats.data.model.action.Shoot;
import com.enseirb.pfa.bastats.stat.ViewStatsMatch;
import com.enseirb.pfa.bastats.tournament.KeyTable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class InterfaceMatch extends ActionBarActivity implements FragmentMenuMatch.menuMatch, FragmentTeamMatch.TeamMatch, FragmentAction2Clicks.Action2clicks {
    private Context mCtx;


    private int scoreTeamA = 0;
    private int scoreTeamB = 0;
    private TextView faultTeamA;
    private TextView faultTeamB;
    private FragmentMenuMatch fragmentMenu;
    private FragmentTeamMatch fragmentTeamA;
    private FragmentTeamMatch fragmentTeamB;
    private ChronoFragment fragmentChrono;

    int TeamOff;

    // the tables needed for the activity
    private DBPlayerDAO tablePlayer;
    private DBShootDAO tableShoot;
    private DBBlockDAO tableBlock;
    private DBFaultDAO tableFault;
    private DBStealDAO tableSteal;
    private DBPlayingTimeDAO tablePlayingTime;
    private DBReboundDAO tableRebound;
    private DBAssistDAO tableAssist;
    private DBTurnOverDAO tableTurnOver;
    private DBActionDAO tableAction;
    private DBMatchDAO tableMatch;

    private int idA;
    private int idB;

    private int matchId;

    private Boolean oneClick;

    private ListView mHistoryListView; //listView for the actions put in the history
    private ArrayList<HashMap<String,String>> listH;
    private SimpleAdapter adaptH;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_match);

        mCtx = this;
        fragmentMenu = (FragmentMenuMatch) getFragmentManager().findFragmentById(R.id.menuMatch);
        fragmentTeamA = (FragmentTeamMatch) getFragmentManager().findFragmentById(R.id.EquipeA);
        fragmentTeamB = (FragmentTeamMatch) getFragmentManager().findFragmentById(R.id.EquipeB);
        fragmentChrono =  (ChronoFragment)     getFragmentManager().findFragmentById(R.id.chronometer);
        faultTeamA  =  (TextView) findViewById(R.id.faults_tm1);
        faultTeamB  =  (TextView) findViewById(R.id.faults_tm2);
        oneClick=false;
        initDAO(mCtx);
        initHistory();
        Intent intent = getIntent();
        initTeamLayouts(intent);



    }



    private void twoClicksAction(String history,String action) {
        //inserts the action in the database
        if (!oneClick) {
            PlayingTime tps = new PlayingTime(matchId, -1, ChronoFragment.getTime(), -1, -1);
            tablePlayingTime.insert(tps);

            onSelectedActions(tps.getChronometer() + history, action);
            oneClick = true;

        }

    }


    public void shoot3Click() {
        twoClicksAction("\t3pt shot from\t","shoot3");

        if (fragmentTeamB.getTeamOff()) {
            scoreTeamA += 3;
        } else {
            scoreTeamB += 3;
        }
        updateScore();
    }

    public void shoot3FClick() {
        twoClicksAction("\t3 pt shot missed from\t","shoot3f");
    }

    public void freeThrowFClick() {
        twoClicksAction("\tFree throw missed from\t", "ftf");
    }

    public void freeThrowClick() {
        twoClicksAction("\tFree throw made  from\t", "ft");

            if (fragmentTeamA.getTeamOff()) {
                scoreTeamA += 1;
            } else {
                scoreTeamB += 1;
            }
            updateScore();

    }

    public void assistClick() {
        twoClicksAction("\tassist from\t", "assist");
    }

    public void onSelectedActions(String msg,String action){
        fragmentTeamA.onSelectedActions(msg, action);
        fragmentTeamB.onSelectedActions(msg,action);

    }


    private void initTeamLayouts(Intent intent) {
        DBFormationDAO tableFormation = new DBFormationDAO(mCtx);
        DBMatchDAO tableMatch = new DBMatchDAO(mCtx);
        DBTeamDAO tableTeam = new DBTeamDAO(mCtx);

        matchId = intent.getIntExtra(KeyTable.MATCH_ID,-1);

        //instantiating a match object from the id got with the previous activity
        Match match = new Match(tableMatch.getWithId(matchId));
        int formationA = match.getFormationTeamA();
        int formationB = match.getFormationTeamB();
        idA = tableFormation.getWithId(formationA).getTeamId();
        idB = tableFormation.getWithId(formationB).getTeamId();
        fragmentMenu.setTeamA(tableTeam.getWithId(idA).getName());
        fragmentMenu.setTeamB(tableTeam.getWithId(idB).getName());

        //setting the color corresponding to the team
        int colorA=getRgb(tableTeam.getWithId(idA).getColor());
        int colorB=getRgb(tableTeam.getWithId(idB).getColor());
        fragmentTeamA.setRgb(colorA);
        fragmentTeamB.setRgb(colorB);
        fragmentMenu.setColorA(colorA);
        fragmentMenu.setColorB(colorB);
        fragmentTeamA.setTeamA(true);
        fragmentTeamB.setTeamOff(true);

        TeamOff = idB;

        //setting the list of players for both teams
        List<Player> tmpA = new ArrayList<Player>(tablePlayer.getFormationMatch(formationA));
        List<Player> tmpB = new ArrayList<Player>(tablePlayer.getFormationMatch(formationB));
        Player playerA = new Player(getNeutralPlayer(tmpA));
        Player playerB = new Player(getNeutralPlayer(tmpB));
        tmpA.add(0, playerA);
        tmpB.add(0, playerB);
        fragmentTeamA.setListPlayers(tmpA);
        fragmentTeamB.setListPlayers(tmpB);


    }



    //setting the rgb from the color given with a string
    public int getRgb(String color) {
        int rgb=0;
        switch (color){
            case "Red":
                rgb= Color.rgb(255, 50, 50);
                break;
            case "Blue":
                rgb=Color.rgb(0, 0, 255);
                break;
            case "Green":
                rgb=Color.GREEN;
                break;
            case "Orange":
                rgb=Color.rgb(255,105,0);
                break;
            case "Yello":
                rgb=Color.rgb(255,255,0);
                break;
            case "Brown":
                rgb=Color.rgb(115,74,18);
                break;
            case "Black":
                rgb=Color.rgb(0,0,0);
                break;
            case "White":
                rgb=Color.rgb(255,255,255);
                break;
            case "Grey":
                rgb=Color.rgb(150,150,150);
                break;
            case "Purple":
                rgb=Color.rgb(75,0,130);
                break;
            case "Pink":
                rgb=Color.rgb(239,89,123);
                break;
        }
            return rgb;
    }







    public void tableRowAction(View v,int index,String message,String action) {
        //inserts the action in the history view

        Player actor;
        if (fragmentTeamA.getTeamOff())
            actor = fragmentTeamA.getListPlayers().get(index);
        else
            actor = fragmentTeamB.getListPlayers().get(index);

        switch(action){
            case "shoot3":
                shootAction(message,actor,3,1);
                break;

            case "shoot3f":
                shootAction(message,actor,3,0);
                break;

            case "ft":
                shootAction(message,actor,1,1);
                break;

            case "ftf":
                shootAction(message,actor,1,0);
                break;

            case "assist":
                Assist assist =new Assist(tablePlayingTime.getLast(),actor);
                tableAssist.insert(assist);
                addHistory(message + actor.getPseudo(), "ASSIST", tableAssist.getLast().getId());

                break;

        }


        initFragmentTeam();
        setOneClick();
    }

    private void shootAction(String message,Player actor,int pts,int success){
        Shoot tir = new Shoot(tablePlayingTime.getLast(), actor, pts, success);
        tableShoot.insert(tir);
        addHistory(message + actor.getPseudo(), "SHOOT", tableShoot.getLast().getId());


    }


    public void setOneClick(){
        oneClick=false;

    }


    public void action(View v) {
        //callback on a 2 clicks action
        ViewGroup p = (ViewGroup) v.getParent();
        LinearLayout l=(LinearLayout) p.findViewById(R.id.layoutFaults);

        TextView textView = (TextView) p.getChildAt(0);
        int index = Integer.parseInt(textView.getText().toString());

        PlayingTime tps = new PlayingTime(matchId,-1, ChronoFragment.getTime(), -1, -1);
        tablePlayingTime.insert(tps);

        switch (v.getId()) {
            case R.id.shoot_2:

                shootMade(tps, getActorOff(index), 2);
                if (fragmentTeamA.getTeamOff()) {
                    scoreTeamA += 2;
                } else {
                    scoreTeamB += 2;
                }
                updateScore();
                switchPossession();
                break;

            case R.id.shoot_2f:


                shootFailed(tps, getActorOff(index), 2);
                break;

            case R.id.fault_off:
                if(fragmentTeamA.getTeamOff())
                    fragmentTeamA.addFaults(index, fragmentChrono.getnbQuarter());
                else
                    fragmentTeamB.addFaults(index, fragmentChrono.getnbQuarter());

                offensiveFault(tps, getActorOff(index), index);
                updateFault();
                switchPossession();
                break;

            case R.id.rebound_off:

                offensiveRebound(tps, getActorOff(index));
                break;

            case R.id.turnover:

                turnover(tps, getActorOff(index));
                switchPossession();
                break;

            case R.id.block:

                block(tps, getActorDef(index));
                break;

            case R.id.rebound_def:

                defensiveRebound(tps, getActorDef(index));
                switchPossession();
                break;

            case R.id.fault_def:
                if(!fragmentTeamA.getTeamOff()) {
                    fragmentTeamA.addFaults(index, fragmentChrono.getnbQuarter());
                    fragmentTeamA.createPoint(l);
                }
                else {
                    fragmentTeamB.createPoint(l);
                    fragmentTeamB.addFaults(index, fragmentChrono.getnbQuarter());
                }

                defensiveFault(tps, getActorDef(index), index);
                updateFault();
                break;

            case R.id.steal:

                steal(tps, getActorDef(index));
                switchPossession();

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interface_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.see_db) {
            Intent intent = new Intent(this, CheckDB.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.End) {
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            DBMatchDAO tableMatch = new DBMatchDAO(mCtx);


            Match match = new Match(tableMatch.getWithId(matchId));

            match.setResult(idA);
            match.setScoreTeamA(65);
            match.setScoreTeamB(50);
            tableMatch.update(match.getId(), match);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void endMatch(View v) {

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        DBMatchDAO tableMatch = new DBMatchDAO(mCtx);

        Match match = new Match(tableMatch.getWithId(matchId));
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        int pYear = cal.get(Calendar.YEAR);
        int pMonth = cal.get(Calendar.MONTH);
        int pDay = cal.get(Calendar.DAY_OF_MONTH);
        String date = pDay+"/"+pMonth+"/"+pYear;
        match.setDate(date);
        if (scoreTeamA > scoreTeamB) {
            match.setResult(idA);
        } else if (scoreTeamA < scoreTeamB) {
            match.setResult(idB);
        } else {
            match.setResult(Match.RESULT_DRAW);
        }
        match.setScoreTeamA(scoreTeamA);
        match.setScoreTeamB(scoreTeamB);
        tableMatch.update(match.getId(), match);


        finish();
    }

    public void displayStats(View v){
        Intent i = new Intent(mCtx, ViewStatsMatch.class);
        i.putExtra(KeyTable.MATCH_ID, matchId);
        startActivity(i);
    }

    public void initDAO(Context context){
        tablePlayer = new DBPlayerDAO(context);
        tableShoot = new DBShootDAO(context);
        tableBlock = new DBBlockDAO(context);
        tableFault = new DBFaultDAO(context);
        tableSteal = new DBStealDAO(context);
        tableAssist = new DBAssistDAO(context);
        tableTurnOver = new DBTurnOverDAO(context);
        tableRebound = new DBReboundDAO(context);
        tablePlayingTime = new DBPlayingTimeDAO(context);
        tableAction  =  new DBActionDAO(context);
        tableMatch   =  new DBMatchDAO(context);
    }


    //Inserts the actions in the database and the history view

    public void shootMade(PlayingTime tps, Player actor, int pts){
        Shoot tir = new Shoot(tablePlayingTime.getLast(), actor, pts, Shoot.SUCCESSFUL);
        tableShoot.insert(tir);
        addHistory(tps.getChronometer() + "\t2 pt shot from \t" + actor.getPseudo(), "SHOOT", tableShoot.getLast().getId());

    }

    public void shootFailed(PlayingTime tps, Player actor, int pts){
        Shoot tir = new Shoot(tablePlayingTime.getLast(), actor, pts, Shoot.FAILED);
        tableShoot.insert(tir);
        addHistory(tps.getChronometer() + "\t2pt shot missed from \t" + actor.getPseudo(), "SHOOT", tableShoot.getLast().getId());

    }

    public void offensiveRebound(PlayingTime tps, Player actor){
        tableRebound.insert(new Rebound(tablePlayingTime.getLast(), actor, Rebound.OFFENSIVE));
        addHistory(tps.getChronometer() + "\toffensive rebound from\t" + actor.getPseudo(), "REBOND", tableRebound.getLast().getId());


    }

    public void defensiveRebound(PlayingTime tps, Player actor){
        tableRebound.insert(new Rebound(tablePlayingTime.getLast(), actor, Rebound.DEFENSIVE));
        addHistory(tps.getChronometer() + "\tdefensive rebound from \t" + actor.getPseudo(), "REBOND", tableRebound.getLast().getId());

    }

    public void offensiveFault(PlayingTime tps, Player actor, int index){
        tableFault.insert(new Fault(tablePlayingTime.getLast(), actor));
        String teamFault="FAULTA";
        if (fragmentTeamB.getTeamOff())
            teamFault="FAULTB";
        addHistory(tps.getChronometer() + "\tfoul from \t" + actor.getPseudo(), teamFault, tableFault.getLast().getId(), index);

    }

    public void defensiveFault(PlayingTime tps, Player acteur, int index){
        tableFault.insert(new Fault(tablePlayingTime.getLast(), acteur));
        String teamFault="FAULTA";
        if (!fragmentTeamB.getTeamOff())
            teamFault="FAULTB";
        addHistory(tps.getChronometer() + "\tfoul from\t" + acteur.getPseudo(), teamFault, tableFault.getLast().getId(), index);
    }

    public void steal(PlayingTime tps, Player acteur){
        tableSteal.insert(new Steal(tablePlayingTime.getLast(), acteur));
        addHistory(tps.getChronometer() + "\tsteal from \t" + acteur.getPseudo(), "STEAL", tableSteal.getLast().getId());

    }

    public void turnover(PlayingTime tps, Player actor){
        tableTurnOver.insert(new TurnOver(tablePlayingTime.getLast(), actor));
        addHistory(tps.getChronometer() + "\tturnover from \t" + actor.getPseudo(), "TURNOVER", tableTurnOver.getLast().getId());
    }

    public void block(PlayingTime tps, Player actor){
        tableBlock.insert(new Block(tablePlayingTime.getLast(), actor));
        addHistory(tps.getChronometer() + "\tblock from\t" + actor.getPseudo(), "BLOCK", tableBlock.getLast().getId());
    }

    public Player getActorOff(int index){
        if (fragmentTeamA.getTeamOff())
            return fragmentTeamA.getListPlayers().get(index);
        else
            return fragmentTeamB.getListPlayers().get(index);

    }

    public Player getActorDef(int index){

        if (!fragmentTeamA.getTeamOff())
            return fragmentTeamA.getListPlayers().get(index);
        else
            return fragmentTeamB.getListPlayers().get(index);


    }

    public void switchPossession(){
        if (!oneClick) {
            fragmentTeamA.setTeamOff(!fragmentTeamA.getTeamOff());
            fragmentTeamB.setTeamOff(!fragmentTeamB.getTeamOff());
            if (fragmentTeamA.getTeamOff())
                fragmentMenu.setBallA();
            else
                fragmentMenu.setBallB();

            initFragmentTeam();
        }
    }

    public void initFragmentTeam(){
        fragmentTeamA.clearLayouts();
        fragmentTeamB.clearLayouts();
        fragmentTeamA.fillTeamLayouts();
        fragmentTeamB.fillTeamLayouts();



    }


    public void initHistory(){
        mHistoryListView = (ListView) findViewById(R.id.history_listview);

        listH = new ArrayList<HashMap<String,String>>();
        adaptH=new SimpleAdapter(InterfaceMatch.this, listH,R.layout.history,new String[]{"event","button","action","playerIndex"},new int[]{R.id.action,R.id.button}) {

            public View getView(int position, View convertView, ViewGroup parent) {
                final int x=position;
                View v = super.getView(position, convertView, parent);
                ImageButton b=(ImageButton)v.findViewById(R.id.deleteAction); //button to delete an action from the view
                b.setOnClickListener(new View.OnClickListener() {
                    //deletes the action from the database
                    @Override
                    public void onClick(View v) {
                        String action= listH.get(x).get("action");

						if (action.equals("SHOOT")) {
							Shoot sh = tableShoot.getWithId(Integer.parseInt(listH.get(x).get("button")));

							if (sh.getSuccessful() == Shoot.SUCCESSFUL) {
                                   //removes the points from the score
								Boolean shootTeamA = false;
								int actor = sh.getActorPlayer();

								List<Player> list = fragmentTeamA.getListPlayers();
								for(Player j: list) {

									if (j.getId() == actor) {
										scoreTeamA -= sh.getPts();
										shootTeamA = true;
									}
								}
								if (!shootTeamA) {
									scoreTeamB -= sh.getPts();
								}
								updateScore();
							}
						}

                        if (action.equals("FAULTA")){
                            //removes the orange ball for the fault
                            int index=Integer.parseInt(listH.get(x).get("playerIndex"));
                            fragmentTeamA.removeFaults(index, fragmentChrono.getnbQuarter());

                            initFragmentTeam();
                            action="FAULT";

                        }

                        if (action.equals("FAULTB")){
                            int index=Integer.parseInt(listH.get(x).get("playerIndex"));
                            fragmentTeamB.removeFaults(index, fragmentChrono.getnbQuarter());
                            initFragmentTeam();
                            action="FAULT";

                        }
                        tableAction.removeWithId(action, Integer.parseInt(listH.get(x).get("button")));
                        listH.remove(x);
                        adaptH.notifyDataSetChanged();
                        updateFault();

                    }
                });
                return v;
            }
        }
        ;

        mHistoryListView.setAdapter(adaptH);
    }


    private HashMap<String,String> add(String str, String action, int id){
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("event",str);
        map.put("action", action);
        map.put("button",Integer.toString(id));
        return map;
    }


    public void addHistory(String str, String action, int id){
        HashMap<String,String> map=add(str,action,id);
        listH.add(0, map);
        adaptH.notifyDataSetChanged();
        mHistoryListView.setAdapter(adaptH);
    }

    public void addHistory(String str, String action, int id, int index){
        HashMap<String,String> map=add(str,action,id);
        map.put("playerIndex",Integer.toString(index));
        listH.add(0, map);
        adaptH.notifyDataSetChanged();
        mHistoryListView.setAdapter(adaptH);

    }



    public void updateScore() {
        TextView scoreATextView = (TextView) findViewById(R.id.score_team_1);
        TextView scoreBTextView = (TextView) findViewById(R.id.score_team_2);

        scoreATextView.setText(String.valueOf(scoreTeamA));
        scoreBTextView.setText(String.valueOf(scoreTeamB));
    }

    public void updateFault(){
        faultTeamA.setText(String.valueOf(fragmentTeamA.getNbFaultsQt(fragmentChrono.getnbQuarter())));
        faultTeamB.setText(String.valueOf(fragmentTeamB.getNbFaultsQt(fragmentChrono.getnbQuarter())));



    }



    private Player getNeutralPlayer(List<Player> mListPlayer){
        for (Player cmp : mListPlayer){
            if (cmp.getSurname().equals("NeutralPlayer") && cmp.getFirstName().equals("NeutralPlayer")){
                mListPlayer.remove(cmp);

                return cmp;
            }
        }
        return null;
    }
}
