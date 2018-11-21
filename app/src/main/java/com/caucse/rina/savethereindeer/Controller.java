package com.caucse.rina.savethereindeer;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class Controller {

    //when open the custom_grid_tile, represent the status of custom_grid_tile.
    private final static int GAME_OVER = 12;
    private final static int GAME_WIN = 14;
    public final static int NONE = 0;
    public final static int IS_CAPTURED = 1;
    public final static int IS_TRACE = 2;

    private int remainTurn;
    private View view;
    private boolean isGameOver;
    private boolean isGameWin;
    private boolean isItemSearchUsed;
    private Stage stage;
    private Context context;
    private User user = User.INSTANCE;
    private ArrayList<Model> grid;


    Controller(Stage stage, Context context) {
        remainTurn = stage.getTotalTurnNum();
        this.context = context;
        isGameWin = false;
        isGameOver = false;
        isItemSearchUsed = false;
        this.stage = stage;
        grid = new ArrayList<>();
    }

    public ArrayList<Model> getGrid() {

        return this.grid;

    }

    /********************Start Game *****************************/

    public void initMap(GridAdapter.ItemListener itemListener) {
        initWolfPosition(); //set the position of wolf
        Distance distance = findNearestDeer();

        for (int i = 0; i < stage.getSizeOfMap() * stage.getSizeOfMap(); i++) {
            grid.add(new Grass(Position.getPositionFromGrid(i, stage.getSizeOfMap())));
        }
        for (int i = 0; i < stage.getModel().size(); i++) {
            int pos = stage.getModel().get(i).getPosition().getX() * stage.getSizeOfMap() + stage.getModel().get(i).getPosition().getY();
            grid.remove(pos);
            grid.add(pos, stage.getModel().get(i));
        }
        view = new View(stage, context, grid);
        view.setOnMap(itemListener);
        view.showNearestDeer(distance.getReindeer());
        PlayActivity.isSettingFinished = true;
    }

    private void initWolfPosition() {

        int[][] map = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];

        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model m = stage.getModel().get(idx);
            int x = m.getPosition().getX();
            int y = m.getPosition().getY();
            map[x][y] = -1;

            if (m instanceof Reindeer) {  //ban around 8 custom_grid_tile
                if (x > 0) map[x - 1][y] = -1;
                if (x < stage.getSizeOfMap() - 1) map[x + 1][y] = -1;
                if (y > 0) map[x][y - 1] = -1;
                if (y < stage.getSizeOfMap() - 1) map[x][y + 1] = -1;
                if (x > 0 && y > 0) map[x - 1][y - 1] = -1;
                if (x < stage.getSizeOfMap() - 1 && y > 0) map[x + 1][y - 1] = -1;
                if (x > 0 && y < stage.getSizeOfMap() - 1) map[x - 1][y + 1] = -1;
                if (x < stage.getSizeOfMap() - 1 && y < stage.getSizeOfMap() - 1)
                    map[x + 1][y + 1] = -1;
            }
        }
        //for every wolf, make new Wolf instance and set position
        for (int wolf = 0; wolf < stage.getNumOfWolf(); wolf++) {
            while (true) {
                Random random = new Random();
                int ranX = random.nextInt(stage.getSizeOfMap());
                int ranY = random.nextInt(stage.getSizeOfMap());

                if (map[ranX][ranY] != -1) {
                    Wolf curWolf = new Wolf(ranX, ranY);
                    stage.getModel().add(curWolf);
                    map[ranX][ranY] = -1;
                    Toast.makeText(context.getApplicationContext(), "Wolf setting complete. (" + curWolf.getPosition().getX() + "," + curWolf.getPosition().getY() + ")", Toast.LENGTH_SHORT).show();
                    Log.d("CHECK_POSITION_WOLF", "POSITION COMPLETE : (" + curWolf.getPosition().getX() + "," + curWolf.getPosition().getY() + ")");
                    break;
                }
                continue;
            }
        }
    }


    /**********************************Find Wolf Path **********************************/

    //return chainPath, which store the parent position of each position.
    private ArrayList<ChainPath> setDistanceOnMap(int size, int[][] map, int posX, int posY) {
        ArrayList<Position> openList = new ArrayList<>();
        ArrayList<ChainPath> chainPaths = new ArrayList<>();
        boolean[][] closeList = new boolean[size][size];

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
        chainPaths.add(new ChainPath(new Position(posX, posY), null));
        openList.add(new Position(posX, posY));
        map[posX][posY] = 0;

        while (!openList.isEmpty()) {
            Position parent = openList.get(0);
            int x = parent.getX();
            int y = parent.getY();

            if (x > 0 && !closeList[x - 1][y] && map[x - 1][y] > map[x][y] + 1) {
                map[x - 1][y] = map[x][y] + 1;
                Position pos = new Position(x - 1, y);
                openList.add(pos);
                chainPaths.add(new ChainPath(pos, parent));
            }
            if (x < size - 1 && !closeList[x + 1][y] && map[x + 1][y] > map[x][y] + 1) {
                map[x + 1][y] = map[x][y] + 1;
                Position pos = new Position(x + 1, y);
                openList.add(pos);
                chainPaths.add(new ChainPath(pos, parent));
            }
            if (y > 0 && !closeList[x][y - 1] && map[x][y - 1] > map[x][y] + 1) {
                map[x][y - 1] = map[x][y] + 1;
                Position pos = new Position(x, y - 1);
                openList.add(pos);
                chainPaths.add(new ChainPath(pos, parent));
            }
            if (y < size - 1 && !closeList[x][y + 1] && map[x][y + 1] > map[x][y] + 1) {
                map[x][y + 1] = map[x][y] + 1;
                Position pos = new Position(x, y + 1);
                openList.add(pos);
                chainPaths.add(new ChainPath(pos, parent));
            }
            openList.remove(0);
            closeList[x][y] = true;

        }

        return chainPaths;
    }

    private Position findParentPosition(ArrayList<ChainPath> chainPaths, Position p) {
        for (int i = 0; i < chainPaths.size(); i++) {
            if (chainPaths.get(i).isMatchWithPosition(p)) return chainPaths.get(i).getParent();
        }
        return null;
    }

    private Distance findNearestDeer() {

        int[][] map = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];
        ArrayList<Reindeer> reindeer = new ArrayList<>();
        ArrayList<Wolf> wolf = new ArrayList<>();
        Distance distance = new Distance();

        for (int idx = 0; idx < stage.getModel().size(); idx++) {
            Model model = stage.getModel().get(idx);
            if (model instanceof Tree || model instanceof Santa) {
                map[model.getPosition().getX()][model.getPosition().getY()] = -1;
            } else if (model instanceof Wolf) wolf.add((Wolf) model);
            else if (model instanceof Reindeer) reindeer.add((Reindeer) model);
        }

        if (reindeer.isEmpty()) return null;

        int shortest = 100;
        for (int idx = 0; idx < wolf.size(); idx++) {
            Model curWolf = wolf.get(idx);
            setDistanceOnMap(stage.getSizeOfMap(), map, curWolf.getPosition().getX(), curWolf.getPosition().getY());

            for (int deerIdx = 0; deerIdx < reindeer.size(); deerIdx++) {
                Model deer = reindeer.get(deerIdx);
                int curDistance = map[deer.getPosition().getX()][deer.getPosition().getY()];
                if (curDistance < shortest) {
                    shortest = curDistance;
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

    //from 0
    ArrayList<Position> findShortestPath(Distance distance) {
        if (distance == null) return null;
        ArrayList<Position> shortestPath = new ArrayList<Position>();

        int map[][] = new int[stage.getSizeOfMap()][stage.getSizeOfMap()];
        ArrayList<ChainPath> chainPaths = setDistanceOnMap(stage.getSizeOfMap(), map, distance.getWolf().getPosition().getX(), distance.getWolf().getPosition().getY());
        Position pos = distance.getReindeer().getPosition();
        for (int idx = 0; idx < chainPaths.size(); idx++) {
            if (chainPaths.get(idx).isMatchWithPosition(pos)) {
                shortestPath.add(0, pos);// add deer(last)
                break;
            }
        }
        while (true) {
            pos = findParentPosition(chainPaths, pos);
            if (pos == null) break;
            else shortestPath.add(0, pos);
        }
        return shortestPath;

    }


    /**********************************User Action ************************************/

    // save the status and show
    public boolean checkTile(int pos) {
        Position position = Position.getPositionFromGrid(pos, stage.getSizeOfMap());
        if (grid.get(pos) instanceof Wolf) {
            //Toast.makeText(context, "There is a wolf", Toast.LENGTH_SHORT).show();
            ((Wolf) grid.get(pos)).setStatus(Model.ISCAPTRUED);
            view.updateTile(pos);
            isGameWin = true;
            stateUpdate();
            return false;
        } else {
            for (int idx = 0; idx < stage.getModel().size(); idx++) {
                Model model = stage.getModel().get(idx);
                if (model instanceof Wolf) {
                    if (((Wolf) model).isMatchWithTrace(position)) {
                        // Toast.makeText(context, "There is a trace", Toast.LENGTH_SHORT).show();
                        ((Grass) grid.get(pos)).setStatus(Model.ISTRACE);
                        view.updateTile(pos);
                        moveWolf();
                        return true;
                    }
                }
            }

        }
        ((Grass) grid.get(pos)).setStatus(Model.Nothing);
        view.updateTile(pos);
        moveWolf();
        return true;
    }

    public void setStatusOfAroundTile(int pos, int status) {
        int temp;
        if (Position.isValidPosition((int) (pos / stage.getSizeOfMap()), pos % stage.getSizeOfMap() + 1, stage.getSizeOfMap())) {
            temp = pos + 1;
            Model curModel = grid.get(temp);
            if (!(curModel instanceof Tree) && !(curModel instanceof Reindeer)) {
                curModel.setStatus(status);
            }
            view.updateTile(temp);
        }

        if (Position.isValidPosition((int) (pos / stage.getSizeOfMap()), pos % stage.getSizeOfMap() - 1, stage.getSizeOfMap())) {
            temp = pos - 1;
            Model curModel = grid.get(temp);
            if (!(curModel instanceof Tree) && !(curModel instanceof Reindeer)) {
                curModel.setStatus(status);
            }
            view.updateTile(temp);
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() + 1, (int) (pos % stage.getSizeOfMap()), stage.getSizeOfMap())) {
            temp = pos + stage.getSizeOfMap();
            Model curModel = grid.get(temp);
            if (!(curModel instanceof Tree) && !(curModel instanceof Reindeer)) {
                curModel.setStatus(status);
            }
            view.updateTile(temp);
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() - 1, (int) (pos % stage.getSizeOfMap()), stage.getSizeOfMap())) {
            temp = pos - stage.getSizeOfMap();
            Model curModel = grid.get(temp);
            if (!(curModel instanceof Tree) && !(curModel instanceof Reindeer)) {
                curModel.setStatus(status);
            }
            view.updateTile(temp);
        }
    }

    //if return value is false, user input wrong movement position.
    public boolean moveDeer(int fPos, int tPos) {

        Model deer = grid.get(fPos);
        deer.move(Position.getPositionFromGrid(tPos, stage.getSizeOfMap()));
        setStatusOfAroundTile(fPos, Model.NONE);

        if (grid.get(tPos) instanceof Wolf) {
            stage.getModel().remove(deer);
            grid.remove(fPos);
            grid.add(fPos, new Grass(fPos, stage.getSizeOfMap()));
            grid.get(tPos).setStatus(Model.ISCAPTRUED);
            isGameOver = true;
        } else if (grid.get(tPos) instanceof Santa) {
            stage.getModel().remove(deer);
            grid.remove(fPos);
            grid.add(fPos, new Grass(fPos, stage.getSizeOfMap()));
            Santa santa = ((Santa) grid.get(tPos));
            santa.decreaseCapacity();

            if (santa.getCapacity() == 0) {
                stage.getModel().remove(santa);
                grid.remove(tPos);
                grid.add(tPos, new Grass(tPos, stage.getSizeOfMap()));
            }
        } else {
            grid.remove(fPos);
            grid.add(fPos, new Grass(fPos, stage.getSizeOfMap()));
            grid.remove(tPos);
            grid.add(tPos, deer);
        }
        view.updateTile(tPos);
        view.updateTile(fPos);
        moveWolf();
        return true;
    }


    public void moveWolf() {
        Distance distance = findNearestDeer();
        if (distance == null) {
            stateUpdate();
            return;
        }
        Reindeer deer = distance.getReindeer();
        Wolf wolf = distance.getWolf();

        ArrayList<Position> shortestPath = findShortestPath(distance);

        for (int i = 1; i < stage.getSpeedOfWolf() + 1; i++) {

            int pos = wolf.getPosition().getX() * stage.getSizeOfMap() + wolf.getPosition().getY();
            int toPos = shortestPath.get(i).getX() * stage.getSizeOfMap() + shortestPath.get(i).getY();
            distance.getWolf().move(shortestPath.get(i));

            grid.remove(pos);
            grid.add(pos, new Grass(Position.getPositionFromGrid(pos, stage.getSizeOfMap())));
            view.updateTile(pos);


            if (grid.get(toPos) instanceof Reindeer) {
                isGameOver = true;
                stage.getModel().remove(deer);
                distance.getWolf().setStatus(Model.ISCAPTRUED);
                grid.remove(toPos);
                grid.add(toPos, wolf);
                view.updateTile(toPos);
                break;
            }
            grid.remove(toPos);
            grid.add(toPos, wolf);
            view.updateTile(toPos);

        }
        Toast.makeText(context.getApplicationContext(), "Wolf moved : (" + distance.getWolf().getPosition().getX() + "," + distance.getWolf().getPosition().getY() + ")", Toast.LENGTH_SHORT).show();
        stateUpdate();
    }

    /***********************************After action : State Update *******************/

    //todo
    private int stateUpdate() {
        remainTurn--;
        if (isGameOver || remainTurn <= 0) {
            CustomDialog dialog = new CustomDialog(context, stage);
            dialog.CallFunction(CustomDialog.DIALOG_GAME_LOSE);
            return GAME_OVER;
        }
        if (isGameWin) {
            if (stage.getStageNumber() == User.INSTANCE.getClearStage() + 1) {

                User.INSTANCE.increaseStageClear();
                PlayActivity.dbController.updateUserInformation();
            }
            CustomDialog dialog = new CustomDialog(context, stage);
            dialog.CallFunction(CustomDialog.DIALOG_GAME_WIN); //todo
            return GAME_WIN;
        }

        int remainDeer = 0;
        for (int i = 0; i < stage.getModel().size(); i++) {
            Model model = stage.getModel().get(i);
            if (model instanceof Reindeer) {
                remainDeer++;
                checkDeerStatus((Reindeer) model); //todo
            }
        }
        if (remainDeer == 0) {
            CustomDialog dialog = new CustomDialog(context, stage);
            dialog.CallFunction(CustomDialog.DIALOG_GAME_WIN);
            return GAME_WIN;
        }
        /*for(int i = 0; i< stage.getNumOfReindeer() -remainDeer;i++){
            stage.increaseSpeedOfWolf();
        }*/
        View.recyclerView.setClickable(true);
        View.recyclerView.setEnabled(true);
        return 0;
    }


    private void checkDeerStatus(final Reindeer deer) {
        //todo : check around 8 tile & change status & update view
        int pos = deer.getPosition().getX() * stage.getSizeOfMap() + deer.getPosition().getY();
        int temp;
        if (Position.isValidPosition((int) (pos / stage.getSizeOfMap()), pos % stage.getSizeOfMap() + 1, stage.getSizeOfMap())) {
            temp = pos + 1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition((int) (pos / stage.getSizeOfMap()), pos % stage.getSizeOfMap() - 1, stage.getSizeOfMap())) {
            temp = pos - 1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() + 1, (int) (pos % stage.getSizeOfMap()), stage.getSizeOfMap())) {
            temp = pos + stage.getSizeOfMap();
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() - 1, (int) (pos % stage.getSizeOfMap()), stage.getSizeOfMap())) {
            temp = pos - stage.getSizeOfMap();
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() - 1, (int) (pos % stage.getSizeOfMap())-1, stage.getSizeOfMap())) {
            temp = pos - stage.getSizeOfMap()-1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() - 1, (int) (pos % stage.getSizeOfMap())+1, stage.getSizeOfMap())) {
            temp = pos - stage.getSizeOfMap()+1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() +1 , (int) (pos % stage.getSizeOfMap())-1, stage.getSizeOfMap())) {
            temp = pos + stage.getSizeOfMap()-1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
        if (Position.isValidPosition(pos / stage.getSizeOfMap() + 1, (int) (pos % stage.getSizeOfMap())+1, stage.getSizeOfMap())) {
            temp = pos + stage.getSizeOfMap() +1;
            Model curModel = grid.get(temp);
            if (curModel instanceof Wolf) {
                deer.setIsScrared(true);
                view.updateTile(pos);
                return;
            }
        }
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
        if (stage.getSpeedOfWolf() > 1) return false;

        user.decreaseItemSlow();
        stage.decreaseSpeedOfWolf();
        return true;
    }


    public void useItemSearch(Position p) {
        user.decreaseItemSearch();
        isItemSearchUsed = true;
        //checkTile(p);
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

        ChainPath(Position position, Position parent) {
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
