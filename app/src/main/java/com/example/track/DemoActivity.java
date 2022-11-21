package com.example.track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.track.entity.Temperature;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DemoActivity extends AppCompatActivity {
    //用一个数组来listView的每一项数据
    private String[] s = {"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii", "ii"};
    private ListView listView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);



//        //通过id找到listView对象
//        listView = findViewById(R.id.listView);
//        //给listView设置ArrayAdapter，绑定数据
//        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s));
//        listView.setCacheColorHint(Color.parseColor("#000000"));

        mRecyclerView = findViewById(R.id.listView);




    }

}