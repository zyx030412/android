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
import com.example.track.adapter.MineRecycleViewAdapter;
import com.example.track.entity.Mine;

import java.util.ArrayList;
import java.util.List;

public class MineBodyFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Mine> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_body_mine,container,false);
        recyclerView = v.findViewById(R.id.mine_recycle_view);
        initItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        linearLayoutManager.setItemPrefetchEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MineRecycleViewAdapter adapter = new MineRecycleViewAdapter(list);
        recyclerView.setAdapter(adapter);


        return v;

    }

    private void initItems(){
        Mine mine1 = new Mine(R.mipmap.change,"车辆添加");
        list.add(mine1);
        Mine mine3 = new Mine(R.mipmap.set,"系统设置");
        list.add(mine3);

    }

}
