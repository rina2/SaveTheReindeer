package com.caucse.rina.savethereindeer;

import android.util.Log;

import java.util.ArrayList;

import java.util.Random;

public class Controller {

    //when open the tile, represent the status of tile.
    private final static int GAME_OVER = 100;
    private final static int GAME_WIN = 101;
    private final static int NONE = 0;
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
    }

    /********************Start Game *****************************/

    public void initMap() {
        initWolfPosition(); //set the position of wolf
        Distance distance = findNearestDeer();
        view.showNearlistDeer(distance.getReindeer());
    }


    private Distance findNearestDeer() {

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

            setDistanceOnMap(stage.getSizeOfMap(), map, curWolf.getPosition().getX(), curWolf.getPosition().getY());

            for (int deerIdx = 0; deerIdx < reindeer.size(); deerIdx++) {
                Model deer = reindeer.get(deerIdx);
                int curDistance = map[deer.getPosition().getX()][deer.getPosition().getY()];
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
        return distance;
    }

    //return chainPath, which store the parent position of each position.
    private ArrayList<ChainPath> setDistanceOnMap(int size, int[][] map, int posX, int posY) {
        ArrayList<Position> openList = new ArrayList<>();
        ArrayList<ChainPath> chainPaths = new ArrayList<>();
        boolean[][] closeList = new boolean[size][size];
        chainPaths.add(new ChainPath(new Position(posX, posY), null));

        map = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = 100;
            }
        }
        for (int i = 0; i < stage.getModel().size(); i++) {
            Model model = stage.getModel().get(i);
            if (model instanceof Tree || model instanceof Santa)
                map[model.getPosition().getX()][model.getPosition().getY()] = -1;
        }
        openList.add(new Position(posX, posY));
        map[posX][posY] = 0;

        while (!openList.isEmpty()) {
            Position p = openList.get(0);
            int x = p.getX();
            int y = p.getY();

            if (x > 0 && !closeList[x - 1][y] && map[x - 1][y] > map[x][y] + 1) {
                map[x - 1][y] = map[x][y] + 1;
                Position pos = new Position(x - 1, y);
                openList.add(pos);
                chainPaths.add(new ChainPath(new Position(x, y), pos));
            }
            if (x < size - 1 && !closeList[x + 1][y] && map[x + 1][y] > map[x][y] + 1) {
                map[x + 1][y] = map[x][y] + 1;
                Position pos = new Position(x + 1, y);
                openList.add(pos);
                chainPaths.add(new ChainPath(new Position(x, y), pos));
            }
            if (y > 0 && !closeList[x][y - 1] && map[x][y - 1] > map[x][y - 1]) {
                map[x][y - 1] = map[x][y] + 1;
                Position pos = new Position(x, y - 1);
                openList.add(pos);
                chainPaths.add(new ChainPath(new Position(x, y), pos));
            }
            if (y < size - 1 && !closeList[x][y + 1] && map[x][y + 1] > map[x][y] + 1) {
                map[x][y + 1] = map[x][y] + 1;
                Position pos = new Position(x, y + 1);
                openList.add(pos);
                chainPaths.add(new ChainPath(new Position(x, y), pos));
            }
            openList.remove(0);
            closeList[x][y] = true;

        }

        return chainPaths;
    }


    //path(0) is deer, path(last) is wolf
    ArrayList<Position> findShortestPath(ArrayList<ChainPath> chainPaths, Distance distance) {
        ArrayList<Position> shortestPath = new ArrayList<Position>();

        Position pos = distance.getReindeer().getPosition();
        for (int idx = 0; idx < chainPaths.size(); idx++) {
            if (chainPaths.get(idx).isMatchWithPosition(pos)) {
                shortestPath.add(pos);
                pos = chainPaths.get(idx).getParent();
                break;
            }
        }
        while (true) {
            pos = findParentPosition(chainPaths, pos);
            if (pos == null) break;
            shortestPath.add(pos);
        }
        return shortestPath;

    }

    private Position findParentPosition(ArrayList<ChainPath> chainPaths, Position p) {
        for (int i = 0; i < chainPaths.size(); i++) {
            if (chainPaths.get(i).isMatchWithPosition(p)) return chainPaths.get(i).getParent();
        }
        return null;
    }


    /**********************************User Action ************************************/

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
                } else if (((Wolf) model).isMatchWithTrace(position)) {
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
            moveWolf();
            remainTurn--;
        } else {
            isItemSearchUsed = false;
        }
        return true;
    }


    //if return value is false, user input wrong movement position.
    public boolean moveDeer(Position fromPos, Position toPos) {

        for (int i = 0; i < stage.getModel().size(); i++) {
            Model curModel = stage.getModel().get(i);

            if (!(curModel instanceof Wolf) && !(curModel instanceof Santa) && curModel.position.isSamePosition(toPos))
                return false;
            if (curModel.position.isSamePosition(fromPos) && curModel instanceof Reindeer) {
                curModel.move(toPos);
                remainTurn--;
                moveWolf();
                return true;
            }
        }
        return false;
    }

    /***********************************Item use **************************************/

    //change status of reindeer and update view
    public void useItemDisguise(Position position) {
        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model model = stage.getModel().get(idx);

            if (model instanceof Reindeer && model.getPosition().isSamePosition(position)) {
                ((Reindeer) model).setDisguise(true);
                user.decreaseItemDisguise();
            }
        }
    }

    //if success, return true else return false
    public boolean useItemSlow() {
        if(stage.getSpeedOfWolf() > 1) return false;

        user.decreaseItemSlow();
        stage.decreaseSpeedOfWolf();
        return true;
    }


    public void useItemSearch(Position p) {
        user.decreaseItemSearch();
        isItemSearchUsed = true;
        checkTile(p);
    }



    //todo
    //check if Game is over/win, and draw in the grid
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
    }

    private void moveWolf() {
        Distance distance = findNearestDeer();
        Wolf wolf = distance.getWolf();

        int[][] map = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];
        ArrayList<ChainPath> chainPaths = setDistanceOnMap(stage.getSizeOfMap(), map,
                distance.getWolf().getPosition().getX(), distance.getWolf().getPosition().getY());
        ArrayList<Position> shortestPath = findShortestPath(chainPaths, distance);

        for(int i = 0;i<stage.getSpeedOfWolf(); i++){
            distance.getWolf().move(shortestPath.get(shortestPath.size()-2-i));
        }
    }


    
    /******************************Inner Class *****************************/

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

    class ChainPath {
        Position parent;
        Position position;

        ChainPath(Position parent, Position position) {
            this.parent = parent;
            this.position = position;
        }

        public boolean isMatchWithPosition(Position p) {
            return (this.position.getX() == p.getX() && this.position.getY() == p.getY());
        }

        public Position getParent() {
            return parent;
        }

    }

}
