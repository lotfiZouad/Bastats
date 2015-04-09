package com.enseirb.pfa.bastats.match;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enseirb.pfa.bastats.R;


public class FragmentAction2Clicks extends Fragment {

    public interface Action2clicks {
        public void shoot3Click();
        public void shoot3FClick();
        public void freeThrowFClick();
        public void freeThrowClick();
        public void assistClick();



    }


    private Button shoot_3;
    private Button shoot_3f;
    private Button freeThrow;
    private Button freeThrowF;
    private Button assist;
    private Context Ctx;
    private Action2clicks clickCallback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_actions_2clicks,container,false);
        shoot_3 = (Button) v.findViewById(R.id.shoot_3);
        shoot_3f = (Button) v.findViewById(R.id.shoot_3f);
        freeThrow = (Button) v.findViewById(R.id.free_throw);
        freeThrowF = (Button) v.findViewById(R.id.free_throw_f);
        assist = (Button) v.findViewById(R.id.assist);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Ctx=getActivity();
        initButtonAction();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickCallback=(Action2clicks) activity;
    }

    public void initButtonAction(){
        //defines the listeners for the buttons

        shoot_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickCallback.shoot3Click();

            }
        });

        shoot_3f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickCallback.shoot3FClick();
            }
        });

        freeThrowF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickCallback.freeThrowFClick();
            }
        });

        freeThrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickCallback.freeThrowClick();
            }
        });

        assist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickCallback.assistClick();
            }

        });

    }



}


