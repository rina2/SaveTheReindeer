package com.caucse.rina.savethereindeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

public class StageListActivity extends AppCompatActivity {


    private User user = User.INSTANCE;
    private GridLayout grid;
    private final int START_GAME_REQUEST = 55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_list);


        grid = (GridLayout)findViewById(R.id.gridLayout);
        DBController con = new DBController(this);


        /************** Setting color on the list ***************/
        int clearStage = user.getClearStage();
        for(int i = 0; i<clearStage+1; i++){
            Button btn = (Button)grid.getChildAt(i);
            btn.setBackground(this.getResources().getDrawable(R.drawable.clearsquare));
        }


        /*************** onClickListener ****************/

        for(int i = 0; i<grid.getChildCount();i++){
            Button btn  = (Button)grid.getChildAt(i);
            final int stagenum = i+1;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(stagenum <= user.getClearStage()+1){
                        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                        intent.putExtra("stageNumber",stagenum);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }
}
