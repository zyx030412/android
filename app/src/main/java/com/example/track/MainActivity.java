package com.example.track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.track.entity.Temperature;
import com.example.track.entity.Trip;
import com.example.track.entity.User;
import com.example.track.fragment.MineBodyFragment;
import com.example.track.fragment.NavigationFragment;
import com.example.track.fragment.QrcodeBodyFragment;
import com.example.track.service.MainActivityUpdateService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private ImageButton mine,navigation,qrcode;
    private TextView mineText,homepageText,qrcodeText;
    private static User user = new User("18345264895","123321");

    public static Temperature currentTemperature;
    public static Trip currentTrip;


    private Handler handler1 = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(MainActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Temperature temperature = (Temperature) msg.obj;
                System.out.println(temperature.toString());
                currentTemperature = temperature;
                int ifOverHeated = temperature.getWarning_flag();
                if (ifOverHeated == 0) {
                    Toast.makeText(MainActivity.this,"刹车片过热",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //绑定实例
        mine = findViewById(R.id.mine);
        navigation = findViewById(R.id.navigation);
        qrcode = findViewById(R.id.qrcode);
        mineText = findViewById(R.id.mine_text);
        homepageText = findViewById(R.id.navigation_text);
        qrcodeText = findViewById(R.id.qrcode_text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.index_body_fragment_container);

        //Fragment页面的添加
        Fragment homepageFragment = new NavigationFragment();
        Fragment qrcodeFragment = new QrcodeBodyFragment();
        Fragment mineFragment = new MineBodyFragment();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,mineFragment).commit();
        fragmentManager.beginTransaction().hide(mineFragment).commit();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,homepageFragment).commit();
        fragmentManager.beginTransaction().hide(homepageFragment).commit();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,qrcodeFragment).commit();
        fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
        fragmentManager.beginTransaction().show(homepageFragment).commit();

        homepageText.setTextColor(Color.parseColor("#0582E8"));
        navigation.setImageResource(R.mipmap.navigation_blue);

        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //css以及点击事件

                navigation.setImageResource(R.mipmap.navigation_black);
                mine.setImageResource(R.mipmap.mine_blue);
                qrcode.setImageResource(R.mipmap.qrcode_black);
                fragmentManager.beginTransaction().hide(homepageFragment).commit();
                fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
                fragmentManager.beginTransaction().show(mineFragment).commit();
                mineText.setTextColor(Color.parseColor("#0582E8"));
                qrcodeText.setTextColor(Color.parseColor("#808080"));
                homepageText.setTextColor(Color.parseColor("#808080"));
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //css以及点击事件
                navigation.setImageResource(R.mipmap.navigation_blue);
                mine.setImageResource(R.mipmap.mine_black);
                qrcode.setImageResource(R.mipmap.qrcode_black);
                fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
                fragmentManager.beginTransaction().hide(mineFragment).commit();
                fragmentManager.beginTransaction().show(homepageFragment).commit();
                mineText.setTextColor(Color.parseColor("#808080"));
                qrcodeText.setTextColor(Color.parseColor("#808080"));
                homepageText.setTextColor(Color.parseColor("#0582E8"));

            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //css以及点击事件
                navigation.setImageResource(R.mipmap.navigation_black);
                mine.setImageResource(R.mipmap.mine_black);
                qrcode.setImageResource(R.mipmap.qrcode_blue);
                fragmentManager.beginTransaction().hide(homepageFragment).commit();
                fragmentManager.beginTransaction().hide(mineFragment).commit();
                fragmentManager.beginTransaction().show(qrcodeFragment).commit();
                mineText.setTextColor(Color.parseColor("#808080"));
                qrcodeText.setTextColor(Color.parseColor("#0582E8"));
                homepageText.setTextColor(Color.parseColor("#808080"));

            }
        });




        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,9,1,
                TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(128));

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Runnable temperatureRunnable = new MainActivityUpdateService().getCurrentTemperature(handler1);
                pool.execute(temperatureRunnable);
            }
        },1000,1000);

    }
    public static String getUser(){
        return user.getUsername();
    }

    public static Temperature getCurrentTemperature(){
        return currentTemperature;
    }



}