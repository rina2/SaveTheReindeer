package com.caucse.rina.savethereindeer;

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

    private RealmList<DBModel> model;
}

