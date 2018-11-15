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

    public static final int MOVEDEER = 2223;
    public static final int REINDEER = 100;
    public static final int TREE = 101;
    public static  final int SANTA = 102;
    public static  final int WOLF = 103;
    public static final int GRASS  = 104;

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

    public int getTotalTurnNum() {
        return totalTurnNum;
    }

    public ArrayList<Model> getModel() {
        return model;
    }

    public int getSizeOfMap() {
        return sizeOfMap;
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

    public int[] getIntegerArray(){
        int[] array = new int[sizeOfMap*sizeOfMap];

        for(int i = 0; i<model.size(); i++){
            Model curModel = model.get(i);
            if(curModel instanceof Reindeer){
                array[curModel.getGridPosition(sizeOfMap)] = REINDEER;
            }else if( curModel instanceof  Tree){
                array[curModel.getGridPosition(sizeOfMap)] = TREE;
            }else if( curModel instanceof Santa){
                array[curModel.getGridPosition(sizeOfMap)] = SANTA;
            }else if( curModel instanceof  Wolf){
                array[curModel.getGridPosition(sizeOfMap)] = WOLF;
            }else{
                array[curModel.getGridPosition(sizeOfMap)] = GRASS;
            }
        }

        return array;
    }
}
