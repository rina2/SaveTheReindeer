package com.caucse.rina.savethereindeer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    ArrayList<Model> model;
    Stage stage;
    Controller controller;
    public SharedPreferences prefs;
    DBController dbController;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        dbController = new DBController();

        try {
            dbController.initDatabase(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //checkFirstRun();
        //dbController.getUserInformation();


        /*initUserInfo();
        Log.d("DATABASE_CHECK",User.INSTANCE.getMoney()+"");
        Toast.makeText(this, User.INSTANCE.getMoney()+"",Toast.LENGTH_SHORT).show();
        realm.beginTransaction();
        userVO.setMoney(userVO.getMoney()+1500);
        realm.commitTransaction();*/


        /*
        init();
        controller = new Controller(stage);
        controller.initMap();
        Log.d("FUNCTION CHECK","Success!");

        controller.moveDeer(new Position(3,0),new Position(4,0));
        controller.checkTile(new Position(5,1));
*/
    }


    private void init(){

        model = new ArrayList<>();
        model.add(new Reindeer(0,4,0));
        model.add(new Reindeer(1,3,1));
        model.add(new Reindeer(3,2,2));
        model.add(new Reindeer(3,0,3));
        model.add(new Santa(1,1,2));
        model.add(new Santa(1,5,2));
        model.add(new Tree(1,4));
        model.add(new Tree(2,1));
        model.add(new Tree(2,3));
        model.add(new Tree(3,1));
        stage = new Stage(model, 2,1,6,1,20);

    }

}
