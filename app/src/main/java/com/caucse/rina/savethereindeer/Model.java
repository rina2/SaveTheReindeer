package com.caucse.rina.savethereindeer;

import io.realm.RealmObject;

abstract class Model {
    protected Position position;


    public Model(){}
    public void setPosition(Position p){
        position = p;
    }


    public Position getPosition(){
        return position;
    }

    abstract void draw();
    void move(Position p){
        this.position = p;
    }
    public int getGridPosition(int sizeOfMap){
        return sizeOfMap*position.getX() + position.getY();
    }
}
