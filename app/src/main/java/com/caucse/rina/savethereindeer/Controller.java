package com.caucse.rina.savethereindeer;

import android.util.Log;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Random;

public class Controller {

    //when open the tile, represent the status of tile.
    final static int GAME_OVER = 100;
    final static int GAME_WIN = 101;
    final static int NONE = 0;
    final static int IS_CAPTURED = 1;
    final static int IS_TRACE = 2;


    private int remainTurn;
    private View view;
    private boolean isGameOver;
    private boolean isGameWin;
    private boolean isItemSearchUsed;
    private Stage stage;
    private User user = User.INSTANCE;


    Controller(Stage stage) {
        remainTurn = stage.getTotalTurnNum();
        isGameWin = false;
        isGameOver = false;
        isItemSearchUsed = false;
        view = new View(stage.getModel());
        this.stage = stage;
        initWolfPosition(); //set the position of wolf
        findNearestDeer();
        //view.showNearlistDeer(distance.getReindeer());
    }

    //todo
    private void findNearestDeer() {

        int[][] map = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];
        ArrayList<Reindeer> reindeer = new ArrayList<>();
        ArrayList<Wolf> wolf = new ArrayList<>();
        Distance distance = new Distance();

        //init map[][]
        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model model = stage.getModel().get(idx);
            if (model instanceof Tree || model instanceof Santa)
                map[model.getPosition().getX()][model.getPosition().getY()] = -1;
            else if (model instanceof Wolf) wolf.add((Wolf) model);
            else if (model instanceof Reindeer) reindeer.add((Reindeer) model);
        }

        int shortest = 100;
        for (int idx = 0; idx < wolf.size(); idx++) {
            Model curWolf = wolf.get(idx);

            int[][] distanceMap = setDistanceOnMap(stage.getSizeOfMap(), map, curWolf.getPosition().getX(), curWolf.getPosition().getY());

            for (int deerIdx = 0; deerIdx < reindeer.size(); deerIdx++) {
                Model deer = reindeer.get(deerIdx);
                int curDistance = distanceMap[deer.getPosition().getX()][deer.getPosition().getY()];
                if (curDistance < shortest) {
                    distance.setInfo((Wolf) curWolf, (Reindeer) deer, curDistance);
                } else if (curDistance == shortest) {
                    Random ran = new Random();
                    if (ran.nextBoolean()) {
                        distance.setInfo((Wolf) curWolf, (Reindeer) deer, curDistance);
                    }
                }
            }
        }

        view.showNearlistDeer(distance.getReindeer());
    }


    private int[][] setDistanceOnMap(int size, int[][] mymap, int posX, int posY) {
        ArrayList<Position> openList = new ArrayList<>();
        int[][] map = new int[size][size];
        boolean[][] closeList = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mymap[i][j] == -1) {
                    closeList[i][j] = true;
                    map[i][j] = -1;
                } else if (mymap[i][j] == 0) map[i][j] = 100;
                else map[i][j] = mymap[i][j];
            }
        }
        openList.add(new Position(posX, posY));
        map[posX][posY] = 0;

        while (!openList.isEmpty()) {
            Position p = openList.get(0);
            int x = p.getX();
            int y = p.getY();

            if (x > 0 && !closeList[x - 1][y] && map[x - 1][y] > map[x][y] + 1) {
                map[x - 1][y] = map[x][y] + 1;
                openList.add(new Position(x - 1, y));
            }
            if (x < size - 1 && !closeList[x + 1][y] && map[x + 1][y] > map[x][y] + 1) {
                map[x + 1][y] = map[x][y] + 1;
                openList.add(new Position(x + 1, y));
            }
            if (y > 0 && !closeList[x][y - 1] && map[x][y - 1] > map[x][y - 1]) {
                map[x][y - 1] = map[x][y] + 1;
                openList.add(new Position(x, y - 1));
            }
            if (y < size - 1 && !closeList[x][y + 1] && map[x][y + 1] > map[x][y] + 1) {
                map[x][y + 1] = map[x][y] + 1;
                openList.add(new Position(x, y + 1));
            }
            openList.remove(0);
            closeList[x][y] = true;

        }

        return map;
    }

    //todo
    ArrayList<Position> findShortestPath(Distance distance) {
        ArrayList<Position> position = new ArrayList<Position>();

        return position;

    }


    //if return value is false, user input wrong movement position.
    public boolean moveDeer(Position fromPos, Position toPos) {

        for (int i = 0; i < stage.getModel().size(); i++) {
            Model curModel = stage.getModel().get(i);

            if (!(curModel instanceof Wolf) && curModel.position.isSamePosition(toPos))
                return false;
            if (curModel.position.isSamePosition(fromPos) && curModel instanceof Reindeer) {
                curModel.move(toPos);
                view.updateMap();
                remainTurn--;
                return true;
            }
        }
        return false;
    }

    // save the status and show
    public boolean checkTile(Position position) {
        boolean is_trace = false;

        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model model = stage.getModel().get(idx);

            if (model instanceof Wolf) {
                if (position.isSamePosition(model.getPosition())) {
                    view.checkTile(position, IS_CAPTURED);
                    isGameWin = true;
                    return true;
                } else if (((Wolf) model).ismatchWithTrace(position)) {
                    is_trace = true;
                }
            } else if (model.getPosition().isSamePosition(position)) {
                return false;//cannot open this tile, because already be opened.
            }
        }
        if (is_trace) {
            view.checkTile(position, IS_TRACE);
        } else {
            view.checkTile(position, NONE);
        }

        if (!isItemSearchUsed) {
            movewolf();
            remainTurn--;
        } else {
            isItemSearchUsed = false;
        }
        return true;
    }

    //change status of reindeer and update view
    public void useItemDisguise(Position position) {
        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model model = stage.getModel().get(idx);

            if (model instanceof Reindeer && model.getPosition().isSamePosition(position)) {
                ((Reindeer) model).setDisguise(true);
                user.decreaseItemDisguise();
                view.updateMap();
            }
        }
    }

    //todo : move wolf, check status of reindeer
    private void movewolf() {

    }

    //todo
    //check if Game is over/win.
    public int stateUpdate() {
        int numOfDeer = 0;

        if (isGameOver || remainTurn == 0) {
            return GAME_OVER;
        } else if (isGameWin) {
            return GAME_WIN;
        }

        //check if sheep meet wolf of santa.

        for (int deer = 0; deer < stage.getModel().size(); deer++) {

            Model model = stage.getModel().get(deer);

            if (model instanceof Reindeer) {
                numOfDeer++;

                for (int idx = 0; idx < stage.getModel().size(); idx++) {

                    Model second = stage.getModel().get(idx);
                    if (model == second) continue;
                    else if (second instanceof Wolf && model.getPosition().isSamePosition(second.getPosition()))
                        return GAME_OVER; //Captured! deer dead..
                    else if (second instanceof Santa && model.getPosition().isSamePosition(second.getPosition())) {
                        //if met santa, remove reindeer and reduce santa's capacity
                        ((Santa) second).decreaseCapacity();
                        stage.getModel().remove(deer);

                        if (((Santa) second).getCapacity() == 0) {
                            stage.getModel().remove(second);
                        }
                    }
                }
            }
        }
        if (numOfDeer == 0) return GAME_WIN;


        return 0;
    }

    //todo
    public void useItemSlow() {
        user.decreaseItemSlow();
        stage.decreaseSpeedOfWolf();
    }

    //todo
    public void useItemSearch(Position p) {
        user.decreaseItemSearch();
        checkTile(p);
    }

    //todo
    private void initWolfPosition() {

        int[][] map = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];

        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model m = stage.getModel().get(idx);
            int x = m.getPosition().getX();
            int y = m.getPosition().getY();
            map[x][y] = -1;

            if (m instanceof Reindeer) {  //ban around 8 tile
                if (x > 0) map[x - 1][y] = -1;
                if (x < stage.getSizeOfMap() - 1) map[x + 1][y] = -1;
                if (y > 0) map[x][y - 1] = -1;
                if (y < stage.getSizeOfMap() - 1) map[x][y + 1] = -1;
            }
        }
        //for every wolf, make new Wolf instance and set position
        for (int wolf = 0; wolf < stage.getNumOfWolf(); wolf++) {
            while (true) {
                Random random = new Random();
                int ranX = random.nextInt(stage.getSizeOfMap());
                int ranY = random.nextInt(stage.getSizeOfMap());

                if (map[ranX][ranY] != -1) {
                    stage.getModel().add(new Wolf(ranX, ranY));
                    map[ranX][ranY] = -1;
                    break;
                }
                continue;
            }
        }
        Log.d("WOLF_SETTING", "POSITION COMPLETE");
        //todo
    }

    class Distance {
        private Wolf wolf;
        private Reindeer reindeer;
        int distance;

        public void setInfo(Wolf wolf, Reindeer deer, int distance) {
            this.distance = distance;
            this.wolf = wolf;
            reindeer = deer;
        }

        public Wolf getWolf() {
            return wolf;
        }

        public Reindeer getReindeer() {
            return reindeer;
        }

    }

}
