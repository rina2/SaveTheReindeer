package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

public class Controller {
    private Stage stage;

    Controller(Stage stage){

        this.stage = stage;
        //todo : copy the stage -> setting in the the stage variable

    }

    private void initWolfPosition(){
        //todo
    }

    public Distance findNearlistDeer() {
        Distance distance = new Distance();
        //todo
        return distance;
    }

    ArrayList<Position> findShortestPath(Distance distance){
        ArrayList<Position> position = new ArrayList<Position>();

        return position;

    }

    public void moveDeer(Reindeer reindeer, Position position){
        reindeer.move(position);
    }

    public void checkTile(Position position){}

    public void disguiseReindeer(Reindeer reindeer, boolean flag){
        reindeer.setDisguise(flag);
    }

    public void movewolf(Wolf wolf, ArrayList<Position> trace){
        for(int i = 0; i<stage.getSpeedOfWolf(); i++){
            wolf.move(trace.get(i));
        }
    }

    public void stateUpdate(){}

    public void deSpeedOfWolf(){
        stage.decreaseSpeedOfWolf();
    }



    class Distance {
        Position wolfpos;
        Position reindeerpos;
        int distance;
    }

}
