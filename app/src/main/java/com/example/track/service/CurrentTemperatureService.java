package com.example.track.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.example.track.entity.Temperature;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentTemperatureService {

    public final OkHttpClient client = new OkHttpClient();

    public void getCurrentTemperature(Handler handler, String phone) {
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();


                //post请求?
                Request request = new Request.Builder().url("http://120.25.145.148:6000/temperature/temperature_by_user?phone=" + phone).get().build();

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
                        if (body.equals("") || body == null) {
                            Message fail = new Message();
                            fail.what = 2;
                            handler.sendMessage(fail);
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


        }).start();
    }
}
