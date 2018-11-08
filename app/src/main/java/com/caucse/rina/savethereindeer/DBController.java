package com.caucse.rina.savethereindeer;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DBController {
    private Realm realm;

    private final String WOLF = "WOLF";
    private final String REINDEER = "REINDEER";
    private final String TREE = "TREE";
    private final String SANTA = "SANTA";

    public void initDatabase(Context context) {
        realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();


        /****************Store User Information ******************/
        DBUser user = realm.createObject(DBUser.class);
        user.setInfo(0, 0, 0, 0, 500);

        /****************Store Stage Information******************/
        //todo

        //stage 1


        realm.commitTransaction();
    }


    //when open the app, get user information from database.
    public void getUserInformation() {
        realm.beginTransaction();
        DBUser DBUser = realm.where(DBUser.class).findFirst();
        User user = User.INSTANCE;
        user.initUserInfo(DBUser.getClearStage(), DBUser.getItemDisguise(), DBUser.getItemSearch(),
                DBUser.getItemSlow(), DBUser.getMoney());
        realm.commitTransaction();

    }

    //When Change User information, we have to update DBUser database.
    public void updateUserInformation() {
        User user = User.INSTANCE;
        realm.beginTransaction();
        DBUser vo = realm.where(DBUser.class).findFirst();
        vo.setInfo(user.getClearStage(), user.getItemDisguise(), user.getItemSearch(), user.getItemSlow(), user.getMoney());
        realm.commitTransaction();
    }

    public Stage getStageInformation(int stageNum) {

        realm.beginTransaction();
        ArrayList<Model> model;
        DBStage curStage = realm.where(DBStage.class).equalTo("stageNumber",stageNum).findFirst();
        int santaCapacity = (int) (curStage.getNumOfReindeer() / 2);
        int deerNum = 0;
        ArrayList<Model> modelList = new ArrayList<>();
        realm.beginTransaction();
        RealmResults<DBModel> dbModel = realm.where(DBModel.class).equalTo("stageNum", stageNum).findAll();
        for (int i = 0; i < dbModel.size(); i++) {
            DBModel curModel = dbModel.get(i);
            switch (curModel.getKind()) {
                case WOLF:
                    modelList.add(new Wolf(curModel.getPosX(), curModel.getPosY()));
                    break;
                case REINDEER:
                    deerNum++;
                    modelList.add(new Reindeer(curModel.getPosX(), curModel.getPosY(), deerNum));
                    break;
                case TREE:
                    modelList.add(new Tree(curModel.getPosX(), curModel.getPosY()));
                    break;
                case SANTA:
                    modelList.add(new Santa(curModel.getPosX(), curModel.getPosY(), santaCapacity));
                    break;
            }
        }

        realm.commitTransaction();
        return new Stage(modelList, curStage.getNumOfWolf(), curStage.getSpeedOfWolf(), curStage.getSizeOfMap(),
                curStage.getStageNumber(), curStage.getTotalTurnNum());
    }

}
