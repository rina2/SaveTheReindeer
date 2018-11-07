package com.caucse.rina.savethereindeer;

import android.content.Context;

import io.realm.Realm;

public class DBController {
    private Realm realm;



    public void initDatabase(Context context){
        realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();


        /****************Store User Information ******************/
        DBUser user = realm.createObject(DBUser.class);
        user.setInfo(0,0,0,0,500);

        /****************Store Stage Information******************/
        //todo



        realm.commitTransaction();
    }


    //when open the app, get user information from database.
    public void getUserInformation(){
        realm.beginTransaction();
        DBUser DBUser = realm.where(DBUser.class).findFirst();
        User user = User.INSTANCE;
        user.initUserInfo(DBUser.getClearStage(), DBUser.getItemDisguise(), DBUser.getItemSearch(),
                DBUser.getItemSlow(), DBUser.getMoney());
        realm.commitTransaction();

    }

    //When Change User information, we have to update DBUser database.
    public void updateUserInformation(){
        User user = User.INSTANCE;
        realm.beginTransaction();
        DBUser vo = realm.where(DBUser.class).findFirst();
        vo.setInfo(user.getClearStage(), user.getItemDisguise(), user.getItemSearch(), user.getItemSlow(), user.getMoney());
        realm.commitTransaction();
    }


}
