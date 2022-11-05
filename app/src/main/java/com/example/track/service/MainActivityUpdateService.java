package com.example.track.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.track.entity.Safety;
import com.example.track.entity.User;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivityUpdateService {

    public MainActivityUpdateService(Handler handler){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                //创建http请求

                Request request = new Request.Builder().url("http://120.25.145.148:8078/track_safety_system_current")
                        .get().build();

                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        handler.sendMessage(onFailure);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        if (body == null || body.equals("")){
                            Message fail = new Message();
                            fail.what = 2;
                            handler.sendMessage(fail);
                        }else{
                            Safety safety = JSON.parseObject(body,Safety.class);
                            Message success = new Message();
                            success.what = 1;
                            success.obj = safety;
                            handler.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }
}
