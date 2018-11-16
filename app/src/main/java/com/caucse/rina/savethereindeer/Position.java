package com.caucse.rina.savethereindeer;

public class Position {
    private int pointX;
    private int pointY;

    Position(int x, int y){
        pointX = x;
        pointY = y;
    }
    public static Position getPositionFromGrid(int num,int size){
        return new Position(num/size, num%size);
    }

    public static boolean isValidPosition(int posX, int posY, int sizeOfMap){
        if(posX >= 0 && posX < sizeOfMap && posY >= 0 && posY <sizeOfMap) return true;
        return false;
    }
    public int getX(){ return pointX;}
    public int getY(){ return pointY;}


    public boolean isSamePosition(Position p){
        if(p.getX() == this.getX() && p.getY() == this.getY()) return true;
        return false;
    }

    public void setPosition(Position p){
        this.pointY = p.pointX;
        this.pointX = p.pointY;
    }
}
