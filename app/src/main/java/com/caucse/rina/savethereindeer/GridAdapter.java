package com.caucse.rina.savethereindeer;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.ObbInfo;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private int[] map;
    ArrayList<Model> model;
    private Context context;
    protected static ItemListener listener;


    public GridAdapter(Context con, ArrayList<Model> m, ItemListener itemListener) {
        this.context = con;
        this.map = map;
        model = m;
        listener = itemListener;
    }


    public void update(int[] map) {
        this.map = map;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageButton imageButton;
        public FrameLayout Layout;
        protected ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.ivEffect);
            Layout = (FrameLayout) view.findViewById(R.id.holderFrame);
            imageButton = (ImageButton) view.findViewById(R.id.ibTile);
            //view.setOnClickListener(this);
            imageButton.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int x = getAdapterPosition();
            Log.d("CLICK_CHECK", x + "th clicked!");
            listener.onItemClick(view, getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tile, parent, false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = parent.getMeasuredWidth() / (int) Math.sqrt(model.size());
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, final int position) {
        holder.imageButton.setBackground(context.getResources().getDrawable(R.drawable.tile));
        holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.color.transparent));
        Model m = model.get(position);
        if(m instanceof Wolf){
            ((Wolf)m).draw(holder.imageButton, holder.imageView);
        }else if(m instanceof Reindeer){
            ((Reindeer)m).draw(holder.imageButton, holder.imageView);
        }else if(m instanceof  Tree){
            ((Tree)m).draw(holder.imageButton, holder.imageView);
        }else if(m instanceof Grass){
            ((Grass)m).draw(holder.imageButton, holder.imageView);
        }else if(m instanceof Santa){
            ((Santa)m).draw(holder.imageButton, holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public interface ItemListener {
        void onItemClick(View item, int position);
    }
    public void setItem(ArrayList<Model> model){
        this.model = model;
    }


}
