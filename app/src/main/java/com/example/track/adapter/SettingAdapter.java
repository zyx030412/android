package com.example.track.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.entity.Setting;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private List<Setting> list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemText;
        ImageButton enter;
        View view;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.recycle_setting_text);
            enter = itemView.findViewById(R.id.recycle_setting_enter);
            view = itemView.findViewById(R.id.updownline);
            linearLayout = itemView.findViewById(R.id.setting_layout);
        }
    }

    public SettingAdapter(List<Setting> list){
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_setting,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Setting setting = list.get(position);
        holder.itemText.setText(setting.getText());
        holder.enter.setImageResource(setting.getImgId());
        if (position == list.size()-1)
            holder.view.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
//                        Intent intent = new Intent(holder.itemView.getContext(), ChangeTrackInfoActivity.class);
//                        holder.itemView.getContext().startActivity(intent);
                        System.out.println("1 click");
                        break;
                    case 1:
//                        Intent intent2 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent2);
                        System.out.println("2 click");
                        break;
                    case 2:
//                        Intent intent3 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent3);
                        break;
                }
            }
        });
        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
//                        Intent intent = new Intent(holder.itemView.getContext(), ChangeTrackInfoActivity.class);
//                        holder.itemView.getContext().startActivity(intent);
                        System.out.println("1 click");
                        break;
                    case 1:
//                        Intent intent2 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent2);
                        System.out.println("2 click");
                        break;
                    case 2:
//                        Intent intent3 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent3);
                        break;
                }
            }
        });
        holder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
//                        Intent intent = new Intent(holder.itemView.getContext(), ChangeTrackInfoActivity.class);
//                        holder.itemView.getContext().startActivity(intent);
                        System.out.println("1 click");
                        break;
                    case 1:
//                        Intent intent2 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent2);
                        System.out.println("2 click");
                        break;
                    case 2:
//                        Intent intent3 = new Intent(holder.itemView.getContext(), SettingActivity.class);
//                        holder.itemView.getContext().startActivity(intent3);
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
