package com.caucse.rina.savethereindeer;

public class Santa extends Model {

    private int capacity=0;
    @Override
    void draw() {

    }

    Santa(Position p, int num){
        position  = p;
        capacity = num;
    }
    Santa(int posx, int posy, int num){
        position = new Position(posx, posy);
        capacity = num;
    }

    public void decreaseCapacity(){
        capacity--;
    }

    public int getCapacity() {
        return capacity;
    }
}
