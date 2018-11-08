package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DBStage extends RealmObject {

    @PrimaryKey
    private int stageNumber= 0;
    private int totalTurnNum = 0;
    private int numOfReindeer = 0;
    private int numOfWolf = 0;
    private int numOfSanta= 0;
    private int numOfTree= 0;
    private int sizeOfMap= 0;
    private int speedOfWolf = 0;

    public DBStage(){}

    DBStage(int s, int t, int nr, int nw, int ns, int nt, int size, int sw){
        stageNumber = s;
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

