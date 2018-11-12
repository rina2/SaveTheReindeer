package com.caucse.rina.savethereindeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private DBController dbController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        gridLayout.removeAllViews();
        dbController = new DBController(this);

        Intent intent = getIntent();
        int stage_num = intent.getIntExtra("stageNumber",-1);

        if(stage_num == -1){
            Toast.makeText(this, "Error",Toast.LENGTH_SHORT).show();
            setResult(-1);
        }
        Stage curStage = dbController.getStageInformation(stage_num);
        Toast.makeText(this,"curStage : "+curStage.getSizeOfMap(),Toast.LENGTH_SHORT).show();
    }
}
