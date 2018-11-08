package com.caucse.rina.savethereindeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;

public class IntroActivity extends AppCompatActivity {

    private ImageView logo;
    private Button btnStart;
    public SharedPreferences prefs;
    private DBController dbController;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        prefs = getSharedPreferences("Pref",MODE_PRIVATE);
        logo = (ImageView)findViewById(R.id.splash);
        btnStart = (Button)findViewById(R.id.btnStart);
        dbController = new DBController(this);
        handler = new Handler();

        /***************** splash animation and show game start button ******************/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnStart.setVisibility(View.VISIBLE);
            }
        },2000);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash);
        logo.startAnimation(splashAnimation);


        checkFirstRun();    //save information to DB

        dbController.getUserInformation();
        Log.d("DATABASE_CHECK","getting user information : complete");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StageListActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    private void checkFirstRun() {
        Log.d("CHECK_FIRST_RUN","FIRST OPENED");
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun){
            try {
                dbController.initDatabase();
                prefs.edit().putBoolean("isFirstRun",false).apply();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("DBCONTROLLER_ERROR","fail to save the data to database");
            }
        }
    }
}
