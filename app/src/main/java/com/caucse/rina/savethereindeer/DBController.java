package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static io.realm.Realm.init;

public class DBController {
    private Realm realm;
    private final String WOLF = "WOLF";
    private final String REINDEER = "REINDEER";
    private final String TREE = "TREE";
    private final String SANTA = "SANTA";
    private Context context;

    DBController(Context con){
        context = con;
        realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void initDatabase() throws JSONException {

        realm.beginTransaction();


        /****************Store User Information ******************/
        DBUser user = realm.createObject(DBUser.class);
        user.setInfo(0, 0, 0, 0, 500);

        /****************Store Stage Information******************/
        saveStageInfoToDB(getStringFromJSON());



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
        DBStage curStage = realm.where(DBStage.class).equalTo("stageNumber", stageNum).findFirst();
        int santaCapacity = (int) (curStage.getNumOfReindeer() / 2);
        int deerNum = 0;
        ArrayList<Model> modelList = new ArrayList<>();
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


    private String getStringFromJSON(){
        AssetManager assetManager = context.getResources().getAssets();

        try{
            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream)assetManager.open("test.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(ais));
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024*1024;

            char readBuf[] = new char[bufferSize];
            int resultSize =0;

            while((resultSize = br.read(readBuf)) != -1){
                if(resultSize == bufferSize){
                    sb.append(readBuf);
                }else{
                    for(int i =0; i<resultSize; i++){
                        sb.append(readBuf[i]);
                    }
                }
            }
            String jString = sb.toString();
            Log.d("JSON_CONTENT",jString);
            return jString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveStageInfoToDB(String str) throws JSONException {

    try {
        JSONObject jsonObject = new JSONObject(str);
        JSONArray jsonStage = jsonObject.getJSONArray("DBStage");
        JSONArray jsonModel = jsonObject.getJSONArray("DBModel");

        for (int i = 0; i < jsonStage.length(); i++) {
            JSONObject curStage = jsonStage.getJSONObject(i);

            DBStage dbStage = realm.createObject(DBStage.class, curStage.getInt("stageNumber"));
            dbStage.setDBStage(curStage.getInt("totalTurnNum"),
                    curStage.getInt("numOfReindeer"), curStage.getInt("numOfWolf"), curStage.getInt("numOfSanta"), curStage.getInt("numOfTree"),
                    curStage.getInt("sizeOfMap"), curStage.getInt("speedOfWolf"));
        }

        for (int i = 0; i < jsonModel.length(); i++) {
            JSONObject curModel = jsonModel.getJSONObject(i);

            DBModel dbModel = realm.createObject(DBModel.class);
            dbModel.setDBModel(curModel.getInt("stageNum"), curModel.getInt("posX"),
                    curModel.getInt("posY"), curModel.getString("kind"));
        }
    }catch (Exception e){
        return;
    }
    }


}
