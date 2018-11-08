package com.caucse.rina.savethereindeer;

public class User {
    public static final User INSTANCE = new User();

    private int clearStage;
    private int itemDisguise;
    private int itemSearch;
    private int itemSlow;
    private int money;

    private User() {
    }

    public void initUserInfo(int clearStage, int itemDisguise, int itemSearch, int itemSlow, int money) {
        this.clearStage = clearStage;
        this.itemDisguise = itemDisguise;
        this.itemSearch = itemSearch;
        this.itemSlow = itemSlow;
        this.money = money;
    }


    /************************** Getter ***********************/
    public int getItemSlow() {
        return itemSlow;
    }

    public int getItemSearch() {
        return itemSearch;
    }

    public int getItemDisguise() {
        return itemDisguise;
    }

    public int getClearStage() {
        return clearStage;
    }

    public int getMoney() {
        return money;
    }


    /************************** Change User Information **************/

    public void increaseStageClear() {
        this.clearStage++;
    }

    public void increaseItemDisguise() {
        this.itemDisguise++;

    }


    public void decreaseItemDisguise() {
        this.itemDisguise--;
    }

    public void increaseItemSlow() {
        itemSlow++;
    }

    public void decreaseItemSlow() {
        itemSlow--;
    }

    public void increaseItemSearch() {
        itemSearch++;
    }

    public void decreaseItemSearch() {
        itemSearch--;
    }

    public void earnMoney(int plus) {
        this.money += plus;
    }

    public void spendMoney(int minus) {
        this.money -= minus;
    }

}
