package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DBStage extends RealmObject {

    @PrimaryKey
    private int stageNumber;
    private int totalTurnNum;
    private int numOfReindeer;
    private int numOfWolf;
    private int numOfSanta;
    private int numOfTree;
    private int sizeOfMap;
    private int speedOfWolf;

    public DBStage(){}

    public void setDBStage(int t, int nr, int nw, int ns, int nt, int size, int sw){
        totalTurnNum = t;
        numOfReindeer = nr;
        numOfWolf = nw;
        numOfSanta = ns;
        numOfTree = nt;
        sizeOfMap = size;
        speedOfWolf = sw;
    }
    public int getStageNumber() {
        return stageNumber;
    }

    public int getNumOfReindeer() {
        return numOfReindeer;
    }

    public int getTotalTurnNum() {
        return totalTurnNum;
    }

    public int getSizeOfMap() {
        return sizeOfMap;
    }

    public int getSpeedOfWolf() {
        return speedOfWolf;
    }

    public int getNumOfWolf() {
        return numOfWolf;
    }

    public int getNumOfSanta() {
        return numOfSanta;
    }

    public int getNumOfTree() {
        return numOfTree;
    }

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }
}

