package com.example.track.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.Temperature1Activity;
import com.example.track.entity.Item3;

import java.util.List;

public class Item3RecycleViewAdapter extends RecyclerView.Adapter<Item3RecycleViewAdapter.ViewHolder> {

    private List<Item3> list;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemText;
        TextView watch;
        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item1_recycle_image);
            itemText = itemView.findViewById(R.id.item1_recycle_text);
            watch = itemView.findViewById(R.id.watch);
            line = itemView.findViewById(R.id.updownline);
        }
    }

    public Item3RecycleViewAdapter(List<Item3> list){
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item1,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Item3 item1 = list.get(position);
        holder.itemImage.setImageResource(item1.getId());
        holder.itemText.setText(item1.getStr());
        holder.watch.setText(item1.getWatch());
        if (position == list.size()-1)
            holder.line.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(holder.itemView.getContext(), Temperature1Activity.class);
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case 1:

                }
            }
        });
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(holder.itemView.getContext(), Temperature1Activity.class);
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case 1:

                }
            }
        });
        holder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(holder.itemView.getContext(), Temperature1Activity.class);
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case 1:

                }
            }
        });
        holder.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(holder.itemView.getContext(), Temperature1Activity.class);
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case 1:

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
