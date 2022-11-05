package com.example.track.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.track.entity.User;
import com.example.track.interfac.VolleyCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService {
    public final OkHttpClient client = new OkHttpClient();

    public void login(Handler handler,String username, String password){
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                //要发送的json数据
                String json = user.toString();
                //创建http客户端

                //创建http请求
                MediaType jsonType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(jsonType,json);

                //post请求?
                Request request = new Request.Builder().url("http://120.25.145.148:8078/check_login?username="+user.getUsername()+"&password="+user.getPassword())
                        .post(body).build();

                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        handler.sendMessage(onFailure);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        if (body.equals("") && body != null){
                            Message fail = new Message();
                            fail.what = 2;
                            handler.sendMessage(fail);
                        }else{
                            User user = JSON.parseObject(body,User.class);
                            Message success = new Message();
                            success.what = 1;
                            success.obj = user;
                            handler.sendMessage(success);
                        }
                    }
                });


            }


        }).start();
    }
}
