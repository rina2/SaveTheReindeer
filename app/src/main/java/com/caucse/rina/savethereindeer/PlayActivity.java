package com.caucse.rina.savethereindeer;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private DBController dbController;
    private View view;
    private Controller controller;
    private boolean ISGAMEFINISH = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        gridLayout.removeAllViews();
        dbController = new DBController(this);


        /********************* Get Stage Information *********************/
        Intent intent = getIntent();
        int stage_num = intent.getIntExtra("stageNumber",-1);

        if(stage_num == -1){
            Toast.makeText(this, "Error",Toast.LENGTH_SHORT).show();
            setResult(-1);
        }
        Stage curStage = dbController.getStageInformation(stage_num);
        Log.d("CHECK_STAGE_INFORMATION",curStage.getSizeOfMap()+"");

        /**********************Set Map on the Activity **********************/

        controller = new Controller(curStage,this);
        controller.initMap(gridLayout);

        /************************ Play ***********************************/
        while(!ISGAMEFINISH){

            //todo : play the game. start turn!


        }

    }
}
