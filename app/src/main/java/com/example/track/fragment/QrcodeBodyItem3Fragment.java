package com.example.track.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.adapter.Item3RecycleViewAdapter;
import com.example.track.entity.Item3;

import java.util.ArrayList;
import java.util.List;

public class QrcodeBodyItem3Fragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Item3> item1List = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode_item3, container, false);
        recyclerView = v.findViewById(R.id.qrcode_item1_recycleview);
        if (item1List.size()==0) {
            initItems();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Item3RecycleViewAdapter adapter = new Item3RecycleViewAdapter(item1List);
        recyclerView.setAdapter(adapter);

//        recyclerView.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event){
//                return true;
//            }
//        });
        return v;
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
