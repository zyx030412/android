package com.example.track;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.track.fragment.HomepageBodyFragment;
import com.example.track.fragment.MineBodyFragment;
import com.example.track.fragment.QrcodeBodyFragment;


public class MainActivity extends AppCompatActivity {
    private ImageButton mine,navigation,qrcode;
    private TextView mineText,homepageText,qrcodeText;

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
        Fragment homepageFragment = new HomepageBodyFragment();
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


    }



}