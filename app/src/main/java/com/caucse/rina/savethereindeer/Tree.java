package com.caucse.rina.savethereindeer;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Tree extends Model {
    @Override
    void draw(final ImageButton imageButton, final ImageView imageView) {
        Context context = imageButton.getContext();
        imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.tree));
    }

    Tree(Position p){
        position = p;
    }
    Tree(int x, int y){
        position = new Position(x, y);
    }
}
