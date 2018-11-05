package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

public class Wolf extends Model {
    final int NUM_TRACE = 3;
    private boolean isCaptured;
    private boolean isSlow;
    private ArrayList<Position> trace;

    Wolf(int x, int y){
        isSlow = false;
        position = new Position(x, y);
        isCaptured = false;
        trace = new ArrayList<Position>(NUM_TRACE);
    }
    @Override
    void draw() {

    }

    @Override
    void move(Position p) {
        addTrace(this.position);
        super.move(p);
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
