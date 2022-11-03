package com.example.track.service;

import android.util.Log;

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

public class RegisterService {
    public final OkHttpClient client = new OkHttpClient();

    public void register(final VolleyCallback callback, String phone, String code){
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                User user = new User();
                user.setUsername(phone);
                user.setPassword(code);
                //要发送的json数据
                String json = user.toString();
                //创建http客户端

                //创建http请求
                MediaType JSON = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json);

                //post请求?
                Request request = new Request.Builder().url("http://120.25.145.148:8078/check_login?username="+user.getUsername()+"&password="+user.getPassword())
                        .post(body).build();

                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        Log.d("register","onResponse");
                        callback.onSuccess(response.body().string());
                    }
                });


            }


        }).start();
    }
}
