package com.caucse.rina.savethereindeer;

import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class View {

    final int IS_CAPTURE = Controller.IS_CAPTURED;
    final int IS_TRACE = Controller.IS_TRACE;
    final int NONE = Controller.NONE;

    private ArrayList<Model> model;
    private ArrayList<Model> copymodel;

    GridView grid;
    GridAdapter gridAdapter;

    View(ArrayList<Model> m){
        this.model = m;
        copymodel = new ArrayList<Model>();
    } //constructor


    public void setOnMap(GridView grid){}


    public void showNearlistDeer(Reindeer deer){
        deer.draw();
        Log.d("CHECK_POSITION_DEER","START!. NEAREST DEER : ("+deer.getPosition().getX()+", "+deer.getPosition().getY()+")");
    }


    public void updateMap(){


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
