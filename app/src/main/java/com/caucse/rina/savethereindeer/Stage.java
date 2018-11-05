package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

public class Stage {
    private int numOfReindeer = 0;
    private int numOfWolf = 0;
    private int numOfSanta= 0;
    private int numOfTree= 0;
    private int sizeOfMap= 0;
    private int stageNumber= 0;
    private int speedOfWolf = 0;
    private ArrayList<Model> model;

    public Stage(ArrayList<Model> list, int numOfWolf ,int speedOfWolf ,int size, int stageNumber){
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

    //todo : make clone method
}
