package com.example.track.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.entity.Item3;

import java.util.ArrayList;
import java.util.List;

public class Item3Adapter extends RecyclerView.Adapter{

    static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private List<Item3> item1List = new ArrayList<>();

        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.qrcode_item1_recycleview);
            if (item1List.size()==0) {
                initItems();
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            Item3RecycleViewAdapter adapter = new Item3RecycleViewAdapter(item1List);
            recyclerView.setAdapter(adapter);

            recyclerView.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    return true;
                }
            });
        }

        private void initItems(){
            Item3 item1 = new Item3(R.mipmap.brake,"刹车系统");
            item1List.add(item1);
            Item3 item2 = new Item3(R.mipmap.water,"水位系统");
            item1List.add(item2);
            Item3 item3 = new Item3(R.mipmap.speed,"速度系统");
            item1List.add(item3);
            Item3 item4 = new Item3(R.mipmap.camera,"检测系统");
            item1List.add(item4);
        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_body_qrcode_item3,parent,false);
        ViewHolder holder = new ViewHolder(view, view.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }



}


