package com.caucse.rina.savethereindeer;

import java.util.ArrayList;

public class Controller {
    private Stage stage;

    Controller(){

        //todo : copy the stage -> setting in the the stage variable

    }

    private void initPosition(){
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

    public void moveDeer(Position position){}

    public void checkTile(Position position){}

    public void disguiseReindeer(){}

    public void movewolf(ArrayList<Position> trace){}

    public void stateUpdate(){}

    public void deSpeedOfWolf(){}



    class Distance {
        Position wolfpos;
        Position reindeerpos;
        int distance;

    }

}
