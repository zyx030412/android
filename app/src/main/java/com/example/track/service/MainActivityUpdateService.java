package com.example.track.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.example.track.MainActivity;
import com.example.track.entity.Temperature;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivityUpdateService {
    private Handler mHandler;

    public MainActivityUpdateService(){

    }

    /**
     * 此方法用于获得实时温度数据
     * 0:网络请求失败
     * 2:请求失败
     * 1:请求成功，并返回数据到message中
     * @param handler
     */
    public Runnable getCurrentTemperature(Handler handler){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                //创建http请求

                Request request = new Request.Builder().url("http://120.25.145.148:6000/temperature/temperature_by_user?phone="+ MainActivity.getUser())
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
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        if (body == null || body.equals("")) {
                            //请求失败
                        } else {
                            Temperature temperature = JSON.parseObject(body, Temperature.class);
                            Message success = new Message();
                            success.what = 1;
                            success.obj = temperature;
                            handler.sendMessage(success);
                        }
                    }
                });
            }
        };
        return runnable;
    }

    public void getCurrent (){

    }
}
