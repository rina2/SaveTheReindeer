package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.logging.Handler;

public class Reindeer extends Model {

    private boolean isNearest;
    private boolean isWarning;
    private boolean isDisguise;
    private boolean isScared;
    private int number;

    Reindeer(int posX, int posY, int DeerNumber) {
        isNearest = false;
        isScared = false;
        isDisguise = false;
        status = NONE;
        position = new Position(posX, posY);
        this.number = DeerNumber;
    }

    //todo
    @Override
    void draw(final ImageButton imageButton, final ImageView imageView) {
        final Context context = imageButton.getContext();
        android.os.Handler handler = new android.os.Handler();
        imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.reindeer));
        imageButton.setPadding(3, 3, 3, 3);
        if (status == WARNING) {
            imageButton.setBackground(context.getResources().getDrawable(R.color.warning));
        }
        imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.reindeer));
    }


    @Override
    void move(Position p) {
        super.move(p);
        Log.d("REINDEER : MOVED", "(" + position.getX() + "," + position.getY() + ")");
    }

    public void setDisguise(boolean disguise) {
        isDisguise = disguise;
    }
}
