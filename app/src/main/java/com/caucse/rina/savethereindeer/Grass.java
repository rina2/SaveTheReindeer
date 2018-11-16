package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.logging.Handler;

public class Grass extends Model {

    Grass() {
        status = NONE;
    }
    Grass(Position p){
        position = p;
        status = NONE;
    }

    public Grass(int pos, int sizeMap) {
        super(pos, sizeMap);
    }

    @Override
    void draw(final ImageButton imageButton, final ImageView imageView) {
        final Context context = imageButton.getContext();
        if (status == NONE) {
            imageButton.setBackground(context.getResources().getDrawable(R.drawable.tile));
        }else if(status == SELECTED){
            imageButton.setBackground(context.getResources().getDrawable(R.color.brown));
            return;
        }

        switch (status) {
            case ISCAPTRUED:
                imageButton.setBackground(context.getResources().getDrawable(R.color.opentile));
                imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.wolf));
                break;
            case ISTRACE:
                imageButton.setBackground(context.getResources().getDrawable(R.color.opentile));
                imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.footprint));
                break;
            case Nothing:
                imageButton.setBackground(context.getResources().getDrawable(R.color.opentile));
                break;
        }
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                status = NONE;
                imageButton.setBackground(context.getResources().getDrawable(R.drawable.tile));
                imageButton.setImageDrawable(context.getResources().getDrawable(R.color.transparent));
            }
        }, 1500);

    }
    public void setStatus(int status) {
        this.status = status;
    }
}
