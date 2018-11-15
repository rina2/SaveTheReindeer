package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity implements GridAdapter.ItemListener{

    private GridLayout gridLayout;
    private DBController dbController;
    private View view;
    private Controller controller;
    private boolean ISGAMEFINISH = false;


    private int actionMode ;
    private int selectedPosition;

    private final int NONE= 2221;
    private final int SELECTION = 2222;
    private final int MOVEDEER = Stage.MOVEDEER;
    private final int REINDEER = Stage.REINDEER;
    private final int SANTA = Stage.SANTA;
    private final int TREE = Stage.TREE;
    private final int WOLF = Stage.WOLF;
    private final int GRASS = Stage.GRASS;

    LinearLayout linearLayout;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        /********************* Get Stage Information *********************/

        dbController = new DBController(this);
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
        controller.initMap(this);
        actionMode = SELECTION;



    }

    @Override
    public void onItemClick(ImageButton itemImageButton,int position) {
        Log.d("PLAY ACTIVITY CLICK : ", position +"th");

        if(actionMode == SELECTION){
            if(controller.getMap()[position] == REINDEER){
                controller.setDeerMovementTile(position,MOVEDEER);
                actionMode = MOVEDEER;
                selectedPosition = position;
                Log.d("MOVEDEER CLICKED!",  position+"th deer are clicked");
            }

            if(controller.getMap()[position] == GRASS){
                Log.d("GRASS CLICKED!",position+"th grass are clicked");
                controller.checkTile(position);
                controller.stateUpdate();
                actionMode = NONE;
            }
        }else if(actionMode == MOVEDEER){
            if(controller.getMap()[position] ==MOVEDEER) {
                controller.moveDeer(selectedPosition, position);
                controller.stateUpdate();
                actionMode = SELECTION;
                selectedPosition = -1;
                Log.d("MOVEDEER CLICKED",position+"로 이동.");
            }
        }

    }
}
