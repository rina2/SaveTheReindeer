package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class View {

    final int IS_CAPTURE = Controller.IS_CAPTURED;
    final int IS_TRACE = Controller.IS_TRACE;
    final int NONE = Controller.NONE;

    private Context context;
    private ArrayList<Model> model;
    private ArrayList<Model> copymodel;
    private Stage stage;


    View(Stage stage,Context context){
        this.stage = stage;
        this.context = context;
        this.model = stage.getModel();
        copymodel = new ArrayList<Model>();
    } //constructor



    public void setOnMap(GridLayout gridLayout) {
        gridLayout.setColumnCount(stage.getSizeOfMap());
        gridLayout.setOrientation(GridLayout.HORIZONTAL);


        for (int i = 0; i < stage.getSizeOfMap() * stage.getSizeOfMap(); i++) {
            ImageButton btn = new ImageButton(context);
            gridLayout.addView(btn);
            int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
            btn.setScaleType(ImageView.ScaleType.FIT_XY);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) btn.getLayoutParams();
            params.width = ((displayWidth - params.getMarginEnd() - params.getMarginStart() - (stage.getSizeOfMap()) * 20) / stage.getSizeOfMap());
            params.height = params.width;
            params.setMargins(5, 5, 5, 5);
            btn.setBackground(context.getResources().getDrawable(R.drawable.tile));
            btn.setLayoutParams(params);
        }

        for(int i = 0; i<model.size(); i++){
            Model curModel = model.get(i);
            if(curModel instanceof Tree) {
                ImageButton curButton = (ImageButton)gridLayout.getChildAt(curModel.getGridPosition(stage.getSizeOfMap()));
                curButton.setImageDrawable(context.getResources().getDrawable(R.drawable.tree));
            }else if(curModel instanceof Santa){
                ImageButton curButton = (ImageButton)gridLayout.getChildAt(curModel.getGridPosition(stage.getSizeOfMap()));
                curButton.setImageDrawable(context.getResources().getDrawable(R.drawable.santa));
            }else if(curModel instanceof  Reindeer){
                ImageButton curButton = (ImageButton)gridLayout.getChildAt(curModel.getGridPosition(stage.getSizeOfMap()));
                curButton.setImageDrawable(context.getResources().getDrawable(R.drawable.reindeer));
            }
        }

        copymodel.addAll(model);
    }


    public void showNearlistDeer(Reindeer deer){
        deer.draw();
        Log.d("CHECK_POSITION_DEER","START!. NEAREST DEER : ("+deer.getPosition().getX()+", "+deer.getPosition().getY()+")");
        //todo : animation that show nearest deer. shinny tile!

    }


    public void updateMap(){


    }


    public void checkTile(Position pos, int status){
        switch(status){
            case IS_CAPTURE:{
                Log.d("CHEKC_TILE","IS_CAPTURE");
                break;
            }
            case IS_TRACE:{
                Log.d("CHEKC_TILE","IS_TRACE");
                break;
            }
            case NONE:{
                Log.d("CHEKC_TILE","IS_TRACE");
                break;
            }
        }
    }


}
