package com.enseirb.pfa.bastats.match;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.enseirb.pfa.bastats.R;
import com.enseirb.pfa.bastats.data.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentTeamMatch extends Fragment {

    public Boolean getTeamOff() {
        return teamOff;
    }


    public void setTeamA(Boolean teamA) {
        this.teamA = teamA;
    }

    public HashMap getFaults() {
        return faults;
    }



    public int getNbFaultsQt(int qt){
        return nbFaults[qt];
    }


    public void addFaults(int index, int qt){
        int fault=(int) getFaults().get(index);
        fault++;
        getFaults().put(index,fault);
        nbFaults[qt]++;


    }

    public void removeFaults(int index, int qt){
        int fault=(int) getFaults().get(index);
        fault--;
        getFaults().put(index,fault);
        nbFaults[qt]--;



    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setRgb(int rgb) {
    this.rgb=rgb;

    }



    public interface TeamMatch {
        public void action(View v);
        public void tableRowAction(View v,int index,String msg,String action);
    }



    private String color;
    private int rgb;
    private int[] nbFaults=new int[4];
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int GRAY = 2;
    private Context Ctx;
    private List<Player> listPlayers = new ArrayList<Player>();

    private TableLayout table;
    private Boolean teamOff =false;
    private Boolean teamA =false;
    private TeamMatch callBackAction;
    private HashMap faults =new HashMap<Integer,Integer>();

    private int[] row_buttons_str_off = {
            R.string.shoot_2pts,
            R.string.shoot_2pts,
            R.string.rebound_off,
            R.string.fault_off,
            R.string.turnover
    };

    private int[] row_buttons_str_def = {
            R.string.block ,
            R.string.rebound_def,
            R.string.fault_def,
            R.string.steal
    };

    private int[] row_buttons_id_off = {
            R.id.shoot_2,
            R.id.shoot_2f,
            R.id.rebound_off,
            R.id.fault_off,
            R.id.turnover
    };

    private int[] row_buttons_id_def = {
            R.id.block,
            R.id.rebound_def,
            R.id.fault_def,
            R.id.steal
    };

    private int[] row_buttons_color_off = {
            GREEN,
            RED,
            GRAY,
            GRAY,
            GRAY
    };

    private int[] row_buttons_color_def = {
            GRAY,
            GRAY,
            GRAY,
            GRAY
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_team_match,container,false);
        table= (TableLayout) v.findViewById(R.id.interface_match_left_table);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Ctx=getActivity();
        for (int i=0;i< listPlayers.size();i++)
            getFaults().put(i, 0);

        fillTeamLayouts();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackAction=(TeamMatch) activity;
    }


       //fills the TeamLayouts containing the buttons for each player
       public void fillTeamLayouts(){
        if (getTeamOff()) {
            createOffenseLayout(table, getListPlayers());

        }
        else {

            createDefenseLayout(table, getListPlayers());
        }
    }


    // fills the TeamLayout for the offensive team
    private void createOffenseLayout(TableLayout tl, List<Player> listPlayers) {
        for (int j = 0; j < listPlayers.size(); j++) {
            Player tmp = listPlayers.get(j);
            createTableRow(tl, j, tmp.getNumber() ,tmp.getPseudo(),
                    row_buttons_id_off, row_buttons_str_off, row_buttons_color_off);
        }
    }

    // fills the TeamLayout for the defensive team
    private void createDefenseLayout(TableLayout tl, List<Player> listPlayers) {
        for (int j = 0; j < listPlayers.size(); j++) {
            Player tmp = listPlayers.get(j);
            createTableRow(tl, j, tmp.getNumber(),tmp.getPseudo(),
                    row_buttons_id_def, row_buttons_str_def, row_buttons_color_def);
        }
    }


    //fills a row containing a player and the buttons for the actions
    private void createTableRow(TableLayout tl, int index, String number,String playerName, int[] id_array, int[] str_array, int[] colors_array) {
        TableRow tableRow=createTable(tl,index,number,playerName);
        for (int j = 0; j < id_array.length; j++) {
            createButton(tableRow, str_array[j], id_array[j], colors_array[j]);
        }

        tl.addView(tableRow);
        View v=new View(Ctx);
        TableRow.LayoutParams rowParam= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,5);

        tl.addView(v,rowParam);
    }


    private TableRow createTable(TableLayout tl, int index, String number,String playerName){
        float dp = tl.getResources().getDisplayMetrics().density;
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        TableRow tableRow = new TableRow(Ctx);

        //indexView to identify the index of the line
        TextView indexView = new TextView(Ctx);
        indexView.setText(String.valueOf(index));
        indexView.setVisibility(View.GONE);

        //creates a border with the team color
        GradientDrawable gd=new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(getResources().getColor(R.color.background_material_light));
        gd.setStroke((int) (4* dp),rgb);
        tableRow.setBackground(gd);
        tableRow.addView(indexView);
        createText(tableRow, number,playerName,index);
        View spacerColumn = new View(Ctx);
        tableRow.addView(spacerColumn, new TableRow.LayoutParams(1,(int) (60*dp)));
        return tableRow;


    }

    // creates a row specific to the 2 clicks action
    private void createOnSelectedTableRow(TableLayout tl, int index, String number,String playerName, String msg,String action) {
        final TableRow tableRow=createTable(tl,index,number,playerName);
        final String message = msg;
        final String Action = action;

        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView textView = (TextView) tableRow.getChildAt(0);
                int index = Integer.parseInt(textView.getText().toString());
                callBackAction.tableRowAction(v,index,message,Action);
            }
        });
        tl.addView(tableRow);
        View v=new View(Ctx);
        TableRow.LayoutParams rowParam= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,5);
        tl.addView(v,rowParam);

    }



        //creates the part with the pseudo, the name and the faults
      private void createText(TableRow parent, String number,String text,int index) {
        float dp = parent.getResources().getDisplayMetrics().density;

        TextView textView = new TextView(Ctx);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, (int)(5 * dp), (int)(5 * dp), 0);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textView.setHeight((int) (40 * dp));
        textView.setWidth((int) (40 * dp));
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setText(truncate(number,3));
        textView.setPadding((int)(5*dp),0,0,0);


        parent.addView(textView,lp);
        View v=new View(Ctx);
        TableRow.LayoutParams rowParam= new TableRow.LayoutParams(5,TableRow.LayoutParams.MATCH_PARENT);


        v.setBackgroundColor(rgb);
        parent.addView(v,rowParam);
        View v2=View.inflate(Ctx,R.layout.player_display,parent);
        TextView player=(TextView) v2.findViewById(R.id.namePlayer);
        player.setText(truncate(text,8));
        View separator=new View(Ctx);
        TableRow.LayoutParams rowParam2= new TableRow.LayoutParams(2,TableRow.LayoutParams.MATCH_PARENT);
        LinearLayout l=(LinearLayout) v2.findViewById(R.id.layoutFaults);
        for(int i=0;i<(int) faults.get(index);i++)
            createPoint(l);


        separator.setBackgroundColor(Color.rgb(0, 0, 0));
        parent.addView(separator,rowParam2);

    }

    //creates an orange point for one fault
    public void createPoint(LinearLayout l){
        float dp = l.getResources().getDisplayMetrics().density;

        TextView point=new TextView(Ctx);
        point.setWidth((int) (10 * dp));
        point.setHeight((int) (10 * dp));
        point.setBackgroundResource(R.drawable.round_corner_team_b_player);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER_VERTICAL;
        l.addView(point,params);



    }

    private void createButton(TableRow parent, int text, Integer id, Integer flags) {
        float dp = parent.getResources().getDisplayMetrics().density;
        if (flags == null) {
            flags = GRAY;
        }

		Button btn = new Button(Ctx);

        switch (flags) {
            case RED:

				btn.setBackgroundResource(R.drawable.button_red);
                break;
            case GREEN:

				btn.setBackgroundResource(R.drawable.button_green);
                break;
            default:

				btn.setBackgroundResource(R.drawable.button_grey);
                break;
        }
        btn.setText(text);
        btn.setTextColor(0xFF111111);
        if (id != null) {
            btn.setId(id);
        }

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) (5 * dp), (int) (5 * dp), (int) (5 * dp), (int) (5 * dp));


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callBackAction.action(v);
            }

        });




        parent.addView(btn, lp);

    }


    //fills the layout for a 2 clicks action
    public void onSelectedActions(String msg,String action){
        clearLayouts();
        if (teamOff) {
            onSelectOffenseLayout(table, listPlayers, msg,action);

        }

    }


    private void onSelectOffenseLayout(TableLayout tl, List<Player> listPlayers, String msg,String action){
        for (int j = 0; j < listPlayers.size(); j++) {
            Player tmp = listPlayers.get(j);
            createOnSelectedTableRow(tl, j, tmp.getNumber(),  tmp.getPseudo(), msg, action);
        }
    }





    private String truncate(String string, int n){
       if (string.length()<=n)
           return string;
       return string.substring(0,n);

   }




    public void clearLayouts(){
        table.removeAllViews();

    }

    public void setTeamOff(Boolean teamOff) {
        this.teamOff = teamOff;
    }

    public List<Player> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<Player> listPlayers) {
        this.listPlayers.addAll(listPlayers);
    }
}
