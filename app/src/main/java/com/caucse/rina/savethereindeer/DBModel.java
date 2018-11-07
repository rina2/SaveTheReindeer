package com.caucse.rina.savethereindeer;

import io.realm.RealmObject;

public class DBModel extends RealmObject {

    private DBStage stage;
    private int posX;
    private int posY;
    private String kind;
}
