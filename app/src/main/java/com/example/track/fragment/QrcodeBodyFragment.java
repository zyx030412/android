package com.example.track.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.track.R;

public class QrcodeBodyFragment extends Fragment {

    private Button button1,button2,button3,button4;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode,container,false);

    //绑定实例
        button1 = v.findViewById(R.id.qrcode_item1);
        button2 = v.findViewById(R.id.qrcode_item2);
        button3 = v.findViewById(R.id.qrcode_item3);
        button4 = v.findViewById(R.id.qrcode_item4);

        button1.setTextColor(Color.parseColor("#000000"));
        viewPager = v.findViewById(R.id.fragment_body_qrcode_viewpager);
        FragmentManager fm = getChildFragmentManager();

        //pager分页设置
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            private Fragment[] fragments = new Fragment[]{
                    new QrcodeBodyItem1Fragment(),
                    new QrcodeBodyItem2Fragment(),
                    new QrcodeBodyItem3Fragment(),
                    new QrcodeBodyItem4Fragment(),
            };

            @NonNull
            @Override
            public Fragment getItem(int position) {

                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        //设置当前页
        viewPager.setCurrentItem(0);
        //取消点击/滑动事件
        viewPager.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#000000"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#737373"));
                viewPager.setCurrentItem(0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#000000"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#737373"));
                viewPager.setCurrentItem(1);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#000000"));
                button4.setTextColor(Color.parseColor("#737373"));
                viewPager.setCurrentItem(2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#737373"));
                button2.setTextColor(Color.parseColor("#737373"));
                button3.setTextColor(Color.parseColor("#737373"));
                button4.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(3);
            }
        });



        return v;
    }
}
