package com.caucse.rina.savethereindeer;

public class User {
    public final User INSTANCE = new User();

    private int clearStage;
    private int itemDisguise;
    private int itemSearch;
    private int itemSlow;
    private int money;

    private User(){
        clearStage = 0;
        itemDisguise = 0;
        itemSearch  = 0;
        itemSlow = 0;
        money = 0;
    }

    public void increaseStageClear(){
        this.clearStage++;
    }

    public void increaseItemDisguise(){
        this.itemDisguise++;
    }

    public void decreaseItemDisguise(){
        this.itemDisguise--;
    }
    public void increaseItemSlow(){
        itemSlow++;
    }
    public void decreaseItemSlow(){
        itemSlow--;
    }
    public void increaseItemSearch(){
        itemSearch ++;
    }
    public void decreaseItemSearch(){
        itemSearch--;
    }
    public void earnMoney(int plus){
        this.money += plus;
    }
    public void spendMoney(int minus){
        this.money -= minus;
    }
}
