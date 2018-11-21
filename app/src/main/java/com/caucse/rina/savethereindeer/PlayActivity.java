package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity implements GridAdapter.ItemListener {

    private GridLayout gridLayout;
    public static DBController dbController;
    private View view;
    public static boolean isSettingFinished = false;
    private Controller controller;
    private boolean ISGAMEFINISH = false;
    private long mLastClickTime = 0;

    private int actionMode;
    private int selectedPosition;


    private Stage curStage;
    private final static int GAME_OVER = 12;
    private final static int GAME_WIN = 14;
    private final int NONE = 2221;
    private final int SELECTION = 2222;
    private final int MOVEDEERTOSANTA = Stage.MOVEDEERTOSANTA;
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
        int stage_num = intent.getIntExtra("stageNumber", -1);

        if (stage_num == -1) {
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            setResult(-1);
        }
        curStage = dbController.getStageInformation(stage_num);
        Log.d("CHECK_STAGE_INFORMATION", curStage.getSizeOfMap() + "");

        /**********************Set Map on the Activity **********************/

        controller = new Controller(curStage, this);
        controller.initMap(this);
        actionMode = SELECTION;


        CustomDialog dialog = new CustomDialog(this,curStage);
        dialog.CallFunction(CustomDialog.DIALOG_GAME_START);

    }

    @Override
    public void onItemClick(android.view.View item, int position) {
        View.recyclerView.setEnabled(false);
        View.recyclerView.setClickable(false);

        if (actionMode == SELECTION) {
            if (controller.getGrid().get(position) instanceof Reindeer) {
                controller.setStatusOfAroundTile(position,Model.SELECTED);
                actionMode = MOVEDEER;
                selectedPosition = position;
            } else if (controller.getGrid().get(position) instanceof Grass || controller.getGrid().get(position) instanceof Wolf) {
                controller.checkTile(position);
            }
        } else if (actionMode == MOVEDEER) {
            if (position == selectedPosition) {
                controller.setStatusOfAroundTile(position,Model.NONE);
                actionMode = SELECTION;
                selectedPosition = -1;
            } else if (controller.getGrid().get(position).getStatus() == Model.SELECTED) {
                controller.moveDeer(selectedPosition, position);
                actionMode = SELECTION;
                selectedPosition = -1;
                Log.d("MOVEDEER CLICKED", position + "로 이동.");
            }
        }
    }


    @Override
    public void onBackPressed() {
        CustomDialog dialog = new CustomDialog(this,curStage);
        dialog.CallFunction(CustomDialog.DIALOG_GAME_EXIT);
    }
}
