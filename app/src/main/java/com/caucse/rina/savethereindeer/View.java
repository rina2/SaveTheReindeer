package com.caucse.rina.savethereindeer;

import android.widget.GridView;

import java.util.ArrayList;

public class View {
    private ArrayList<Model> model;
    private ArrayList<Model> copymodel;

    GridView grid;
    GridAdapter gridAdapter;

    View(){} //constructor
    public void setOnMap(GridView grid){}
    public void showNearlistDeer(Position pos){}
    public void updateMap(){}
    public void openTile(Position pos, boolean isWolf, boolean istrace){}



}
