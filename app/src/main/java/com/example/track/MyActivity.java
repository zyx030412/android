package com.example.track;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.track.service.ActivityCollector;
import com.example.track.service.LoginService;

public class MyActivity extends AppCompatActivity {
    //    线程问题
    private final Handler handler2 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(MyActivity.this, "用户信息保存失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {//当前用户存在时
                    finish();
                }else {//2
                    Toast.makeText(MyActivity.this,"用户信息保存成功",Toast.LENGTH_SHORT).show();
                }
            }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在活动管理器添加当前Activity
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        当用户要退出app时统一将信息保存到本地/数据库中
        if (ActivityCollector.activities.size()==1){
            System.out.println("当用户要退出app时统一将信息保存到本地/数据库中");
            UtilsFile.saveOpenFile(MainActivity.user.toString(),"user.csv",0,getApplicationContext());
            LoginService loginService = new LoginService();
            loginService.userAllInfoModify(handler2);
        }
        //从活动管理器删除当前Activity
        ActivityCollector.removeActivity(this);
    }
}
