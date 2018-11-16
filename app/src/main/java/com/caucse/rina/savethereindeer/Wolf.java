package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class Wolf extends Model {
    final int NUM_TRACE = 3;
    private int status;
    private boolean isSlow;
    private ArrayList<Position> trace;

    Wolf(int x, int y){
        isSlow = false;
        position = new Position(x, y);
        status = NONE;
        trace = new ArrayList<Position>(NUM_TRACE);
    }

    public void setStatus(int status){
        this.status = status;
    }

    @Override
    void draw(final ImageButton imageButton, final ImageView imageView) {
        Context context = imageButton.getContext();
        if(status == ISCAPTRUED){
            imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.wolf));
            imageButton.setPadding(3, 3, 3, 3);
        }else if(status == SELECTED){
            imageButton.setBackground(context.getResources().getDrawable(R.color.brown));
        }
    }

    @Override
    void move(Position p) {
        addTrace(this.position);
        super.move(p);
        Log.d("WOLF : MOVED", "("+position.getX()+","+position.getY()+")");
    }

    public boolean isMatchWithTrace(Position p){
        for(int idx =0; idx<trace.size(); idx++){
            if(p.isSamePosition(trace.get(idx))) return true;
        }
        return false;
    }
    //position 0 is the oldest one
    private void addTrace(Position p){
        if(trace.size() >=NUM_TRACE){
            trace.remove(0);
            trace.add(p);
        }else{
            trace.add(p);
        }

    }
}
