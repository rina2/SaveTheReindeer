package com.caucse.rina.savethereindeer;

import android.app.Activity;
import android.content.Context;
import android.net.sip.SipSession;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class View  {

    final int IS_CAPTURE = Controller.IS_CAPTURED;
    final int IS_TRACE = Controller.IS_TRACE;
    final int NONE = Controller.NONE;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private int[] map;
    private Context context;
    private Stage stage;

    View(Stage stage,Context context){
        recyclerView = ((Activity)context).getWindow().getDecorView().findViewById(R.id.recyclerView);
        this.stage = stage;
        this.context = context;
        map = stage.getIntegerArray();

    } //constructor


    public void setOnMap(GridAdapter.ItemListener itemListener) {

        layoutManager = new GridLayoutManager(context, stage.getSizeOfMap());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GridAdapter(context,map,itemListener);
        recyclerView.setAdapter(adapter);


    }

    public void showNearestDeer(Reindeer deer){
        deer.draw();
        Log.d("CHECK_POSITION_DEER","START!. NEAREST DEER : ("+deer.getPosition().getX()+", "+deer.getPosition().getY()+")");
        //todo : animation that show nearest deer. shinny tile!


    }

    public void updateMap(int[] map){
        this.map = map;
        adapter.update(this.map);

    }


    public void checkTile(Position pos, int status){
        switch(status){
            case IS_CAPTURE:{
                Log.d("CHEKC_TILE","IS_CAPTURE");
                break;
            }
            case IS_TRACE:{
                Log.d("CHEKC_TILE","IS_TRACE");
                break;
            }
            case NONE:{
                Log.d("CHEKC_TILE","IS_TRACE");
                break;
            }
        }
    }


}
