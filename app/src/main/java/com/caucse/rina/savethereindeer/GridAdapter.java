package com.caucse.rina.savethereindeer;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{

    private int[] map;
    private Context context;
    protected static ItemListener listener;


    public GridAdapter(Context con, int[] map,ItemListener itemListener){
        this.context = con;
        this.map = map;
        listener = itemListener;
    }


    public void update(int[] map){
        this.map  = map;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageButton imageButton;
        public LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout)view.findViewById(R.id.holderLinear);
            imageButton = (ImageButton)view.findViewById(R.id.ibTile);
            //view.setOnClickListener(this);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int x = getAdapterPosition();
            Log.d("CLICK_CHECK",x+"th clicked!");
            listener.onItemClick((ImageButton)view, getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tile,parent,false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams)view.getLayoutParams();
        params.height = parent.getMeasuredWidth()/(int) Math.sqrt(map.length);
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {

        switch(map[position]){
            case Stage.REINDEER : holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.reindeer));
            holder.imageButton.setPadding(3,3,3,3);
            break;
            case Stage.SANTA : holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.santa));
            break;
            case Stage.TREE : holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.tree));
            break;
            case Stage.MOVEDEER : holder.imageButton.setBackground(context.getResources().getDrawable(R.color.brown));
        }

    }

    @Override
    public int getItemCount() {
        return map.length;
    }

    public interface ItemListener{
        void onItemClick(ImageButton item,int position);
    }


}
