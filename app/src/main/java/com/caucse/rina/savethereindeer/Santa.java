package com.caucse.rina.savethereindeer;

public class Santa extends Model {

    private int remainDeer=0;
    @Override
    void draw() {

    }

    Santa(Position p, int num){
        position  = p;
        remainDeer = num;
    }
    Santa(int posx, int posy, int num){
        position = new Position(posx, posy);
        remainDeer = num;
    }
}
