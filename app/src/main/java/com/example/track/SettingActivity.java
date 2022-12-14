package com.example.track;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.track.adapter.MineRecycleViewAdapter;
import com.example.track.adapter.SettingAdapter;
import com.example.track.entity.Mine;
import com.example.track.entity.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private RecyclerView settingRecycleView;
    private ImageButton backButton;
    private Button exitButton;
    private List<Setting> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initItems();
        backButton = findViewById(R.id.setting_back);
        settingRecycleView = findViewById(R.id.setting_recycle);
        exitButton = findViewById(R.id.setting_exit);
        settingRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setItemPrefetchEnabled(false);
        settingRecycleView.setLayoutManager(linearLayoutManager);
        SettingAdapter adapter = new SettingAdapter(lists);
        settingRecycleView.setAdapter(adapter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                MainActivity.closeRun();
            }
        });
    }

    private void initItems(){
        Setting setting1 = new Setting("????????????",R.mipmap.enter_40);
        lists.add(setting1);
        Setting setting2 = new Setting("????????????",R.mipmap.enter_40);
        lists.add(setting2);
        Setting setting3 = new Setting("????????????",R.mipmap.enter_40);
        lists.add(setting3);



    }

}