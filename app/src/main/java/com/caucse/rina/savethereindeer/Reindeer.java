package com.caucse.rina.savethereindeer;

import android.util.Log;

public class Reindeer extends Model {

    private boolean isNearest;
    private boolean isScared;
    private boolean isDisguise;
    private int number;

    Reindeer(int posX, int posY, int DeerNumber){
        isNearest = false;
        isScared = false;
        isDisguise = false;
        position = new Position(posX, posY);
        this.number = DeerNumber;
    }

    //todo
    @Override
    void draw() {

        if(isNearest){
            Log.d("REINDEER_POSITION","("+position.getX()+", "+position.getY()+") : Nearest Deer");
            isNearest = false;
        }

        if(isScared){
            Log.d("REINDEER_POSITION","("+position.getX()+", "+position.getY()+") : Scared Deer");
        }

        if(isDisguise){
            Log.d("REINDEER_POSITION","("+position.getX()+", "+position.getY()+") : Disguise Deer");
        }
    }

    public void setDisguise(boolean disguise) {
        isDisguise = disguise;
    }
}
