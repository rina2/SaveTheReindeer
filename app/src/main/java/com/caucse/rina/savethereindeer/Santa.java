package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Santa extends Model {

    private int capacity;
    @Override
    void draw(final ImageButton imageButton,final ImageView imageView) {
        Context context = imageButton.getContext();
        if(status == SELECTED){
            imageButton.setBackground(context.getResources().getDrawable(R.color.brown));
        }
        imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.santa));

    }

    Santa(Position p, int num){
        position  = p;
        capacity = num;
    }
    Santa(int posx, int posy, int num){
        position = new Position(posx, posy);
        capacity = num;
    }

    public void decreaseCapacity(){
        capacity--;
    }

    public int getCapacity() {
        return capacity;
    }
}
