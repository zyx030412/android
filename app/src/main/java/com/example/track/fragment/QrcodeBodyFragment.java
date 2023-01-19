package com.example.track.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.track.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class QrcodeBodyFragment extends Fragment {

    private TextView button1,button2,button3,button4;
    private TextView time;
    public static Timer time60s;
    private Handler handler1 = new Handler(Looper.myLooper()){
        //        int flag_1 = 0;
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                update();
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode,container,false);

        FragmentManager fragmentManager = getParentFragmentManager();
        Fragment fragment1 = new QrcodeBodyItem1Fragment();
        Fragment fragment2 = new QrcodeBodyItem2Fragment();
        Fragment fragment3 = new QrcodeBodyItem3Fragment();
        Fragment fragment4 = new QrcodeBodyItem4Fragment();
    //绑定实例
        button1 = v.findViewById(R.id.qrcode_item1);
        button2 = v.findViewById(R.id.qrcode_item2);
        button3 = v.findViewById(R.id.qrcode_item3);
        button4 = v.findViewById(R.id.qrcode_item4);
        time = v.findViewById(R.id.fragment_body_qrcode_time);

        time60s = new Timer();
        time60s.schedule(new TimerTask() {
            @Override
            public void run() {
                Message success = new Message();
                success.what = 0;
                handler1.sendMessage(success);
            }
        },0,60000);

        button1.setTextColor(Color.parseColor("#000000"));
        FragmentManager fm = getChildFragmentManager();


        fragmentManager.beginTransaction().add(R.id.fragment_body_qrcode_frame,fragment1).commit();
        fragmentManager.beginTransaction().hide(fragment1).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_body_qrcode_frame,fragment2).commit();
        fragmentManager.beginTransaction().hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_body_qrcode_frame,fragment3).commit();
        fragmentManager.beginTransaction().hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_body_qrcode_frame,fragment4).commit();
        fragmentManager.beginTransaction().hide(fragment4).commit();
        fragmentManager.beginTransaction().show(fragment1).commit();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#000000"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#737373"));
                fragmentManager.beginTransaction().hide(fragment2).commit();
                fragmentManager.beginTransaction().hide(fragment3).commit();
                fragmentManager.beginTransaction().hide(fragment4).commit();
                fragmentManager.beginTransaction().show(fragment1).commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#000000"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#737373"));
                fragmentManager.beginTransaction().hide(fragment1).commit();
                fragmentManager.beginTransaction().hide(fragment3).commit();
                fragmentManager.beginTransaction().hide(fragment4).commit();
                fragmentManager.beginTransaction().show(fragment2).commit();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#000000"));
                button4.setTextColor(Color.parseColor("#737373"));
                fragmentManager.beginTransaction().hide(fragment2).commit();
                fragmentManager.beginTransaction().hide(fragment1).commit();
                fragmentManager.beginTransaction().hide(fragment4).commit();
                fragmentManager.beginTransaction().show(fragment3).commit();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#000000"));
                fragmentManager.beginTransaction().hide(fragment2).commit();
                fragmentManager.beginTransaction().hide(fragment3).commit();
                fragmentManager.beginTransaction().hide(fragment1).commit();
                fragmentManager.beginTransaction().show(fragment4).commit();
            }
        });



        return v;
    }

    public void update() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        String update = sdf.format(date);
        if (time!=null) {
            time.setText(update);
        }
    }


}
