package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Stage{

    private int stageNumber= 0;
    private int totalTurnNum = 0;
    private int numOfReindeer = 0;
    private int numOfWolf = 0;
    private int numOfSanta= 0;
    private int numOfTree= 0;
    private int sizeOfMap= 0;
    private int speedOfWolf = 0;

    private ArrayList<Model> model;


    public Stage(){};
    public Stage(ArrayList<Model> list, int numOfWolf ,int speedOfWolf ,int size, int stageNumber, int totalTurnNum){
        this.totalTurnNum = totalTurnNum;
        this.numOfWolf = numOfWolf;
        model = list;
        this.sizeOfMap = size;
        this.stageNumber = stageNumber;
        this.speedOfWolf = speedOfWolf;

        for(int i = 0; i<list.size(); i++){
            if(list.get(i) instanceof Santa) numOfSanta++;
            else if(list.get(i) instanceof Reindeer) numOfReindeer++;
            else if(list.get(i) instanceof Tree) numOfTree ++;
        }
    }

    public void setModel(ArrayList<Model> model){
        this.model = model;
    }
    public int getTotalTurnNum() {
        return totalTurnNum;
    }

    public ArrayList<Model> getModel() {
        return model;
    }

    public int getSizeOfMap() {
        return sizeOfMap;
    }

    public int getNumOfReindeer() {
        return numOfReindeer;
    }

    public int getNumOfSanta() {
        return numOfSanta;
    }

    public int getNumOfWolf() {
        return numOfWolf;
    }

    public void decreaseSpeedOfWolf(){
        this.speedOfWolf --;
    }

    public void increaseSpeedOfWolf(){
        this.speedOfWolf++;
    }

    public int getSpeedOfWolf() {
        return speedOfWolf;
    }

    public int getStageNumber() {
        return stageNumber;
    }
    //todo : make clone method
}
