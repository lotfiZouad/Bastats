package com.enseirb.pfa.bastats.match;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.enseirb.pfa.bastats.R;


public class FragmentMenuMatch extends Fragment {

    //setters for the teams name

    public void setTeamA(String team) {
        teamA.setText(team);
          }
    public void setTeamB(String teamB) {
       this.teamB.setText(teamB);
    }


   //setters for the ball image next to the offensive team
    public void setBallA(){
        ballA.setVisibility(View.VISIBLE);
        ballB.setVisibility(View.INVISIBLE);

    }

    public void setBallB(){
        ballB.setVisibility(View.VISIBLE);
        ballA.setVisibility(View.INVISIBLE);

    }

    //setter for the teams color
    public void setColorA(int color) {
        this.colorA = color;
    }
    public void setColorB(int color) {
        this.colorB = color;
    }


    public interface menuMatch{
        public void switchPossession();
    }


    private menuMatch switchCallback;
    private Context Ctx;

    private ImageView ballA;
    private ImageView ballB;
    private TextView teamA;
    private TextView teamB;
    private Button pause;
    private int colorA;
    private int colorB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_menu_match,container,false);
        teamA =(TextView) v.findViewById(R.id.team_1);
        teamB =(TextView) v.findViewById(R.id.team_2);
        pause= (Button) v.findViewById(R.id.pause);
        ballA =(ImageView) v.findViewById(R.id.ballA);
        ballB =(ImageView) v.findViewById(R.id.ballB);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Ctx=getActivity();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCallback.switchPossession();
            }
        });
        teamA.setTextColor(colorA);
        teamB.setTextColor(colorB);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        switchCallback=(menuMatch) activity;
    }
}
