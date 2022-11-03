package com.example.track.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.entity.Mine;

import java.util.List;

public class MineRecycleViewAdapter extends RecyclerView.Adapter<MineRecycleViewAdapter.ViewHolder> {

    private List<Mine> list;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton itemImage;
        Button itemText;
        ImageButton enter;
//        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.recycle_mine_image);
            itemText = itemView.findViewById(R.id.recycle_mine_text);
            enter = itemView.findViewById(R.id.recycle_mine_enter);
//            line = itemView.findViewById(R.id.bottom_line);
        }
    }

    public MineRecycleViewAdapter(List<Mine> list){
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_mine,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mine mine = list.get(position);
        holder.itemImage.setImageResource(mine.getImageId());
        holder.itemText.setText(mine.getTitle());
//        if (position == list.size()-1)
//            holder.line.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
