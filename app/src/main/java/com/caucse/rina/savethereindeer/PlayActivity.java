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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity implements GridAdapter.ItemListener , android.view.View.OnClickListener {

    private Controller controller;
    private static TextView tvRemainTurn;
    private int actionMode;
    private TextView tvItemSlow, tvItemDisguise, tvItemSearch;
    private ImageView ivItemSlow, ivItemDisguise, ivItemSearch, ivSpeedOne, ivSpeedTwo, ivSpeedThree;
    private int selectedPosition;
    private Stage curStage;
    private final int SELECTION = 2222;
    private final int MOVEDEER = 2223;
    private final int ITEMSEARCH = 2224;
    private final int ITEMDISGUISE = 2225;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvRemainTurn = findViewById(R.id.tvRemainTurn);
        tvItemDisguise = findViewById(R.id.tvItemDisguise);
        tvItemSearch = findViewById(R.id.tvItemSearch);
        tvItemSlow = findViewById(R.id.tvItemSlow);
        ivItemDisguise = findViewById(R.id.ivItemDisguise);
        ivItemSearch = findViewById(R.id.ivItemSearch);
        ivItemSlow = findViewById(R.id.ivItemSlow);
        ivSpeedOne = findViewById(R.id.ivSpeedOne);
        ivSpeedTwo = findViewById(R.id.ivSpeedTwo);
        ivSpeedThree = findViewById(R.id.ivSpeedThree);

        tvItemDisguise.setOnClickListener(this);
        tvItemSearch.setOnClickListener(this);
        tvItemSlow.setOnClickListener(this);
        ivItemSearch.setOnClickListener(this);
        ivItemDisguise.setOnClickListener(this);
        ivItemSlow.setOnClickListener(this);



        /********************* Get Stage Information *********************/
        Intent intent = getIntent();
        int stage_num = intent.getIntExtra("stageNumber", -1);
        if (stage_num == -1) {
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            setResult(-1);
        }
        controller = new Controller(this,stage_num);
        curStage = controller.getStage();


        Log.d("CHECK_STAGE_INFORMATION", curStage.getSizeOfMap() + "");

        tvRemainTurn.setText(""+ curStage.getTotalTurnNum());
        tvItemSlow.setText(""+User.INSTANCE.getItemSlow());
        tvItemSearch.setText(""+User.INSTANCE.getItemSearch());
        tvItemDisguise.setText(""+User.INSTANCE.getItemDisguise());

        /**********************Set Map on the Activity **********************/


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
                controller.checkTile(position,false);
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
        }else if(actionMode == ITEMSEARCH){
            if(controller.getGrid().get(position) instanceof Grass || controller.getGrid().get(position) instanceof Wolf){
                controller.useItemSearch(position);
                tvItemSearch.setText(""+User.INSTANCE.getItemSearch());
                actionMode=SELECTION;
            }
        }else if(actionMode == ITEMDISGUISE){
            if(controller.getGrid().get(position) instanceof Reindeer){
                controller.useItemDisguise(position);
                tvItemDisguise.setText(""+User.INSTANCE.getItemDisguise());
                actionMode = SELECTION;
            }
        }
    }

    @Override
    public void onBackPressed() {
        CustomDialog dialog = new CustomDialog(this,curStage);
        dialog.CallFunction(CustomDialog.DIALOG_GAME_EXIT);
    }

    public static void setTurnOnTextView(int total){
        tvRemainTurn.setText(""+total);
    }


    /*************************USING ITEM **********************************/
    @Override
    public void onClick(android.view.View v) {
        if(actionMode == MOVEDEER){
            Toast.makeText(getApplicationContext(), "루돌프 이동중에는 아이템을 사용하실 수 없습니다",Toast.LENGTH_SHORT).show();
            return;
        }
        switch(v.getId()){
            case R.id.ivItemSlow :
            case R.id.tvItemSlow:
                if(User.INSTANCE.getItemSlow() == 0){
                    Toast.makeText(getApplicationContext(),"아이템이없음",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(controller.useItemSlow()){
                    setViewOfWolfSpeed();
                    tvItemSlow.setText(""+User.INSTANCE.getItemSlow());
                }
                break;

            case R.id.ivItemSearch:
            case R.id.tvItemSearch:
                if(User.INSTANCE.getItemSearch() == 0){
                    Toast.makeText(getApplicationContext(),"아이템이없음",Toast.LENGTH_SHORT).show();
                }
                if(actionMode == ITEMSEARCH){
                    actionMode = SELECTION;
                    Toast.makeText(getApplicationContext(),"아이템 사용취소",Toast.LENGTH_SHORT).show();
                    return;
                }
                actionMode = ITEMSEARCH;
                break;
            case R.id.tvItemDisguise:
            case R.id.ivItemDisguise:
                if(User.INSTANCE.getItemDisguise() == 0){
                    Toast.makeText(getApplicationContext(),"아이템이없음",Toast.LENGTH_SHORT).show();
                }
                if(actionMode == ITEMDISGUISE){
                    actionMode = SELECTION;
                    Toast.makeText(getApplicationContext(),"아이템 사용취소",Toast.LENGTH_SHORT).show();
                }else{
                    actionMode = ITEMDISGUISE;
                }



        }
    }

    private void setViewOfWolfSpeed( ){
        int i =curStage.getSpeedOfWolf();
        if(i==1){
            ivSpeedOne.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
            ivSpeedTwo.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf_empty));
            ivSpeedThree.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf_empty));
        }else if(i == 2){
            ivSpeedOne.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
            ivSpeedTwo.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
            ivSpeedThree.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf_empty));
        }else if(i == 3){
            ivSpeedOne.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
            ivSpeedTwo.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
            ivSpeedThree.setImageDrawable(getResources().getDrawable(R.drawable.speed_wolf));
        }
    }

}
