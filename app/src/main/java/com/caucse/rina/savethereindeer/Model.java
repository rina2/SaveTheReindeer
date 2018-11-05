package com.caucse.rina.savethereindeer;

abstract class Model {
    protected Position position;

    public void setPosition(Position p){
        position = p;
    }

    public boolean isSamePosition(Model model){
        if(position.pointX == model.getPosition().pointX && position.pointY == model.getPosition().pointY)
            return true;
        return false;
    }

    public Position getPosition(){
        return position;
    }


    abstract void draw();
    void move(Position p){
        this.position = p;
    }
}
