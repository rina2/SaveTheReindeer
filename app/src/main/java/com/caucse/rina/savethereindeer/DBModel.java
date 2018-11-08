package com.caucse.rina.savethereindeer;

import io.realm.RealmObject;

public class DBModel extends RealmObject {

    private int stageNum;
    private int posX;
    private int posY;
    private String kind;

    public DBModel(){}
    public DBModel(int s, int px, int py, String kind){
        stageNum = s;
        posX = px;
        posY = py;
        this.kind = kind;
    }
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
