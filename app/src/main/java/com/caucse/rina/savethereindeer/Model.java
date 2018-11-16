package com.caucse.rina.savethereindeer;

import android.widget.ImageButton;
import android.widget.ImageView;

import io.realm.RealmObject;

abstract class Model {
    protected Position position;
    public static final int NONE = 1111;
    public static final int ISCAPTRUED = 1112;
    public static final int ISTRACE = 1113;
    public static final int Nothing = 1114;
    public static final int WARNING = 1115;
    public static final int SCARED = 1115;
    public static final int SELECTED = 1116;
    protected int status;

    public Model(){}
    public Model(int pos, int sizeMap){
        this.position = new Position(pos/sizeMap, pos%sizeMap);
    }
    public void setPosition(Position p){
        position = p;
    }

    public int getStatus(){return status;}

    public Position getPosition(){
        return position;
    }

    public void setStatus(int status){
        this.status = status;
    }
    abstract void draw(final ImageButton imageButton,final ImageView imageView);
    void move(Position p){
        this.position = p;
    }
    public int getGridPosition(int sizeOfMap){
        return sizeOfMap*position.getX() + position.getY();
    }
}
