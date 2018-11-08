package com.caucse.rina.savethereindeer;

import io.realm.RealmObject;

public class DBModel extends RealmObject {

    private int stageNum;
    private int posX;
    private int posY;
    private String kind;

    public int getStage() {
        return stageNum;
    }
    public String getKind(){
        return kind;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
