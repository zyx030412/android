package com.example.track.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.MainActivity;
import com.example.track.PersonalInformationActivity;
import com.example.track.R;
import com.example.track.adapter.MineRecycleViewAdapter;
import com.example.track.entity.Mine;
import com.example.track.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//https://blog.csdn.net/qq_42257666/article/details/119799558
public class MineBodyFragment extends Fragment {
//    user数据的保存本地/数据库在退出APP时
    private TextView name,clock_day,integral;
    private LinearLayout qiandao;
    private CircleImageView head;
    private RecyclerView recyclerView;
    private static final String TAG="MineBodyFragment";
    private int count;//签到天数
    private List<Mine> list = new ArrayList<>();
    View sigup_view;  //签到弹窗视图
    AlertDialog write;//
    SharedPreferences sp;//
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//??
        View v = inflater.inflate(R.layout.fragment_body_mine,container,false);
        init(v);
        initItems();
        //设置累计签到的天数
        sp =getActivity().getSharedPreferences("tice", Context.MODE_PRIVATE);
        clock_day.setText(sp.getString("tice","0"));

//        ??调用adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        linearLayoutManager.setItemPrefetchEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MineRecycleViewAdapter adapter = new MineRecycleViewAdapter(list);
        recyclerView.setAdapter(adapter);
        return v;

    }
    private void init(View v){
        recyclerView = v.findViewById(R.id.mine_recycle_view);
        name = v.findViewById(R.id.fragment_mine_name);
        integral = v.findViewById(R.id.fragment_mine_integral);
        head = v.findViewById(R.id.fragment_mine_head);
        qiandao =v.findViewById(R.id.fragment_mine_qiandao);
        clock_day=v.findViewById(R.id.fragment_mine_clock_day);
//        head.set

        qiandao.setOnClickListener(this::onClick);
        name.setOnClickListener(this::onClick);
        head.setOnClickListener(this::onClick);

//      页面数据设置
        if (MainActivity.user.getHead().equals("") || MainActivity.user.getHead() == null ||MainActivity.user.getHead().equals("null") ) {
            if (MainActivity.user.getSex().equals("男"))
                head.setImageResource(R.mipmap.yonghu_male);
            else
                head.setImageResource(R.mipmap.yonghu_female);
        } else {
            Bitmap bitmap = BitmapUtils.base642Bitmap(MainActivity.user.getHead());
            head.setImageBitmap(bitmap);
        }
        name.setText(MainActivity.user.getUsername());
        integral.setText(MainActivity.user.getIntegral());
        clock_day.setText(MainActivity.user.getSign_in());
    }
    private void initItems(){
        Mine mine1 = new Mine(R.mipmap.tianjia,"车辆添加");
        list.add(mine1);
        Mine mine3 = new Mine(R.mipmap.shezhi2,"系统设置");
        list.add(mine3);
    }
    private void onClick(View view) {
        Intent intent_PersonalInformationActivity = new Intent(getContext(), PersonalInformationActivity.class);
        int id = view.getId();
        switch (id){
            case R.id.fragment_mine_qiandao:
                setSignup();
                break;
            case R.id.fragment_mine_head:
                startActivity(intent_PersonalInformationActivity);
                break;
            case R.id.fragment_mine_name:
                startActivity(intent_PersonalInformationActivity);
                break;
        }
    }

    //签到功能的实现
    public void setSignup(){
        //显示签到成功视图
        write = new AlertDialog.Builder(getActivity()).create();
        sigup_view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_up_signup,null);
        write.setView(sigup_view);
        write.show();

        count = Integer.parseInt(clock_day.getText().toString())+1;
        Log.d(TAG,"count:"+count);
        MainActivity.user.setSign_in(String.valueOf(count));
        count = Integer.parseInt(integral.getText().toString())+5;
        Log.d(TAG,"count:"+count);
        MainActivity.user.setIntegral(String.valueOf(count));
//        editor = sp.edit();
//        editor.putString("tice",Integer.toString(count));
//        editor.commit(); //写入
        onResume();//刷新
    }

    @Override
    public void onResume() {
//        读取数据
        super.onResume();
        clock_day.setText(MainActivity.user.getSign_in());
        integral.setText(MainActivity.user.getIntegral());
//        SharedPreferences sp =getActivity().getSharedPreferences("tice",Context.MODE_PRIVATE);
//        clock_day.setText(sp.getString("tice","0"));
    }
}
