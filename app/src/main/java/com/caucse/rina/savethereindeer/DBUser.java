package com.caucse.rina.savethereindeer;

import io.realm.RealmObject;

public class DBUser extends RealmObject{

    private int clearStage;
    private int itemDisguise;
    private int itemSearch;
    private int itemSlow;
    private int money;

    public DBUser(){}

    public void setInfo( int clearStage, int itemDisguise, int itemSearch, int itemSlow, int money){

        this. clearStage = clearStage;
        this.itemDisguise = itemDisguise;
        this.itemSearch = itemSearch;
        this.itemSlow = itemSlow;
        this.money = money;
    }



    /********************** Getter ******************/
    public int getClearStage() {
        return clearStage;
    }

    public int getItemDisguise() {
        return itemDisguise;
    }

    public int getItemSearch() {
        return itemSearch;
    }

    public int getItemSlow() {
        return itemSlow;
    }

    public int getMoney() {
        return money;
    }

}
