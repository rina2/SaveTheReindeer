package com.caucse.rina.savethereindeer;

public class Reindeer extends Model {

    private boolean isScared;
    private boolean isDisguise;
    private int number;

    Reindeer(int posX, int posY, int DeerNumber){
        isScared = false;
        isDisguise = false;
        position = new Position(posX, posY);
        this.number = DeerNumber;
    }

    @Override
    void draw() {

    }

    public void setDisguise(boolean disguise) {
        isDisguise = disguise;
    }
}
