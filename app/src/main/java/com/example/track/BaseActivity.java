package com.example.track;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

//Android--关闭某个指定activity
//发布于2019-04-17 17:22:01阅读 3.4K0
//最近项目中有这样的需要,在关闭当前Activity同时关闭前面两个Activity，不涉及到应用的退出。自己想了一些方案，也查了一些资料，做个笔记吧。
//
//方案一
//广播的方式
//这个是最容易想到的，同时也是网上提供最多的。 由于多个Activity要使用，关闭页面的广播最好写在基类BaseActivity中，也可以在各个子页面单独写，但是代码量就增加了。
public class BaseActivity extends Activity {
    private MyBaseActiviy_Broad oBaseActiviy_Broad;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //动态注册广播
        oBaseActiviy_Broad = new MyBaseActiviy_Broad();
        IntentFilter intentFilter = new IntentFilter("drc.xxx.yyy.baseActivity");
        registerReceiver(oBaseActiviy_Broad, intentFilter);
    }
    //在销毁的方法里面注销广播
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(oBaseActiviy_Broad);//注销广播
    }
    //定义一个广播
    public class MyBaseActiviy_Broad extends BroadcastReceiver {

        public void onReceive(Context arg0, Intent intent) {
//接收发送过来的广播内容
            int closeAll = intent.getIntExtra("closeAll", 0);
            if (closeAll == 1) {
                finish();//销毁BaseActivity
            }
        }

    }
    /**
     * 显示Toast信息
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
