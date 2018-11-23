package com.caucse.rina.savethereindeer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

public class View  {

    final int IS_CAPTURE = Controller.IS_CAPTURED;
    final int IS_TRACE = Controller.IS_TRACE;
    final int NONE = Controller.NONE;

    private RecyclerView.LayoutManager layoutManager;
    public static RecyclerView recyclerView;
    private GridAdapter adapter;
    private int[] map;
    private ArrayList<Model> grid;
    private Context context;
    private Stage stage;

    View(Stage stage,Context context,ArrayList<Model> grid){
        recyclerView = ((Activity)context).getWindow().getDecorView().findViewById(R.id.recyclerView);
        this.stage = stage;
        this.context = context;
        map = stage.getIntegerArray();
        this.grid = grid;

    } //constructor


    public void setOnMap(GridAdapter.ItemListener itemListener) {
        layoutManager = new GridLayoutManager(context, stage.getSizeOfMap());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GridAdapter(context,grid,itemListener);
        recyclerView.setAdapter(adapter);
    }

    public void showNearestDeer(Reindeer deer){
        if(deer == null){
            Toast.makeText(context.getApplicationContext(), "error !!! ", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(context.getApplicationContext(),"Game Start! nearest deer : ("+deer.getPosition().getX()+","+deer.getPosition().getY()+")",Toast.LENGTH_SHORT).show();
        int position = deer.getPosition().getX()*stage.getSizeOfMap()+deer.getPosition().getY();
        deer.setStatus(Model.WARNING);
        updateTile(position);
        //todo : notify who is the nearest deer

    }

    public void updateMap(){
        adapter.setItem(grid);
        adapter.notifyDataSetChanged();
    }

    public void updateTile(int pos){
        recyclerView.getAdapter().notifyItemChanged(pos);
    }

    public void checkTile(Position position, int status){
        switch(status){
            case IS_CAPTURE:{
                Log.d("TILE CHECK","IS_CAPTURE");
                break;
            }
            case IS_TRACE:{
                int pos = position.getX()*stage.getSizeOfMap() + position.getY();
                map[pos] = IS_TRACE;
                adapter.notifyItemChanged(pos);

                break;
            }
            case NONE:{
                Log.d("TILE CHECK","IS_TRACE");
                break;
            }
        }
    }


}
