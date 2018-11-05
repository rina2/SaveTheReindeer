package com.caucse.rina.savethereindeer;

public class Tree extends Model {
    @Override
    void draw() {

    }

    Tree(Position p){
        position = p;
    }
    Tree(int x, int y){
        position = new Position(x, y);
    }
}
