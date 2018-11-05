package com.caucse.rina.savethereindeer;

abstract class Model {
    protected Position position;

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
}
