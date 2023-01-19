package com.example.track.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.track.MainActivity;
import com.example.track.entity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService {
    public final OkHttpClient client = new OkHttpClient();

    /**
     *1. 登录时
     * LoginActivity,
     * @param handler
     * @param username
     * @param password
     */
    public void login(Handler handler,String username, String password){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody= new FormBody.Builder();
                User user = new User();
                user.setPhone(username);
                user.setPassword(password);
                System.out.println("username:"+username+"----"+"password:"+password);
                //要发送的json数据
                String json = user.toString();
                //创建http客户端

                //创建http请求
                MediaType jsonType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(jsonType,json);

                //post请求?
                Request request = new Request.Builder().url("http://120.25.145.148:6000/user/check_login?username="+user.getPhone()+"&password="+user.getPassword())
                        .get().build();
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
                        if (body.equals("")){
                            Message fail = new Message();
                            fail.what = 2;
                            handler.sendMessage(fail);
                        }else{
                            User user = JSON.parseObject(body,User.class);
                            Log.d("JSON--user",user.toString());
                            Message success = new Message();
                            success.what = 1;
                            success.obj = user;
//                            请求成功时
                            handler.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }

/**
 * 2. 判断这个手机号是否已经存在--有问题
 * LoginActivity,Forget
 */
    public void userJudge(Handler handler2,String phone){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody= new FormBody.Builder();
                Request request;
                formBody.add("phone",phone);
                //创建http请求
                //post请求?
                request = new Request.Builder().url("http://120.25.145.148:6000/user/check_user_phone").post(formBody.build()).build();
                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
//                        返回信息
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        handler2.sendMessage(onFailure);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
//                        body:{"timestamp":"2023-01-02T08:45:53.037+00:00","status":404,"error":"Not Found","path":"/check_user"}
                        System.out.println("body:"+body);
                        if (body.equals("")){
                            Message fail = new Message();
                            fail.what = 2;
                            handler2.sendMessage(fail);
                            System.out.println("当前手机号没有注册过");
                        }else{
                            Message success = new Message();
                            User user = JSON.parseObject(body,User.class);
                            Log.d("JSON--user",user.toString());
                            success.obj = user;
                            success.what = 1;
                            System.out.println("当前手机号已经注册过");
                            handler2.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 3.注册用户
     * RegisterActivity
     * @param
     * @param phone
     */
    public void register(Handler handler_register,String phone,String password){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("register(Handler handler,String phone,String password)");
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody= new FormBody.Builder();
                Request request;
                formBody.add("phone",phone);
                formBody.add("password",password);
                //创建http请求
                //post请求?
                request = new Request.Builder().url("http://120.25.145.148:6000/user/user_add").post(formBody.build()).build();;
                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
//                        返回信息
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        handler_register.sendMessage(onFailure);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        System.out.println("body:"+body);
                        if (body.equals("")){
                            Message fail = new Message();
                            fail.what = 2;
                            handler_register.sendMessage(fail);
                            System.out.println("register注册失败");
                        }else{
                            Message success = new Message();
                            User user = JSON.parseObject(body,User.class);
                            Log.d("JSON--user",user.toString());
                            success.obj = user;
                            success.what = 1;
                            System.out.println("register注册成功");
                            handler_register.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }
/**
 * 4.修改密码
 *ForgetActivity
 */
    public void modify(Handler handler_modify,String phone,String password){
//        Handler handler有什么用
    //启用子线程
    new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("modify(Handler handler_modify,String phone,String password)");
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody= new FormBody.Builder();
            Request request;
            formBody.add("phone",phone);
            formBody.add("password",password);
            //创建http请求
            //post请求?
            request = new Request.Builder().url("http://120.25.145.148:6000/user/user_modify").post(formBody.build()).build();;
            Call call = client.newCall(request);
            //异步访问
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("login","onFailure");
//                        返回信息
                    Message onFailure = new Message();
                    onFailure.what = 0;
                    handler_modify.sendMessage(onFailure);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    String body = response.body().string();
                    System.out.println("body:"+body);
                    if ( body == "1"||body.equals("1")){
                        User user = new User();
                        user.setUsername(phone);
                        user.setPassword(password);
                        Message success = new Message();
                        success.what = 2;
                        success.obj = user;
                        System.out.println("密码修改成功");
                        handler_modify.sendMessage(success);
                    }else {
                        System.out.println("密码修改失败");
                        Message success = new Message();
                        success.what = 1;
                        handler_modify.sendMessage(success);
                    }
                }
            });
        }
    }).start();
}
/**
 * 用户头像修改
 */
    public void headModify(Handler head_modify,String imageString){
//        Handler handler有什么用
    //启用子线程
    new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("headModify(Handler handler_modify,String phone,String password)");
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody= new FormBody.Builder();
            Request request;
            Log.d("headModify-imageString" , imageString);
            String img = imageString;
            formBody.add("imageString",img);
            formBody.add("phone", MainActivity.user.getPhone());
            //创建http请求
            //post请求?
            request = new Request.Builder().url("http://120.25.145.148:6000/user/head_modify").post(formBody.build()).build();
            Call call = client.newCall(request);
            //异步访问
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("login","onFailure");
//                        返回信息
                    Message onFailure = new Message();
                    onFailure.what = 0;
                    head_modify.sendMessage(onFailure);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    String body = response.body().string();
                    System.out.println("body:"+body);
                    if ( body == "1"||body.equals("1")){
//                        User user = new User();
//                        user.setUsername(phone);
//                        user.setPassword(password);
                        Message success = new Message();
                        success.what = 2;
//                        success.obj = user;
                        System.out.println("密码修改成功");
                        head_modify.sendMessage(success);
                    }else {
                        System.out.println("密码修改失败");
                        Message success = new Message();
                        success.what = 1;
                        head_modify.sendMessage(success);
                    }
                }
            });
        }
    }).start();
}

    /**
     *用户信息修改
     * @param user_info_modify
     */
    public void userInfoModify(Handler user_info_modify){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("headModify(Handler user_info_modify");
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody= new FormBody.Builder();
                Request request;

                formBody.add("username",MainActivity.user.getUsername());
                formBody.add("birthday",MainActivity.user.getBirthday());
                formBody.add("sex",MainActivity.user.getSex());
                formBody.add("phone",MainActivity.user.getPhone());
                //创建http请求
                //post请求?
                request = new Request.Builder().url("http://120.25.145.148:6000/user/user_info_modify").post(formBody.build()).build();
                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
//                        返回信息
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        user_info_modify.sendMessage(onFailure);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        System.out.println("body:"+body);
                        if ( body == "1"||body.equals("1")){
                            Message success = new Message();
                            success.what = 2;
//                        success.obj = user;
                            System.out.println("成功");
                            user_info_modify.sendMessage(success);
                        }else {
                            System.out.println("修改失败");
                            Message success = new Message();
                            success.what = 1;
                            user_info_modify.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }
    /**
     *手机号码修改
     */
    public void phoneModify(Handler phone_modify,String phoneNew){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("headModify(Handler phone_modify");
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody= new FormBody.Builder();
                Request request;
                formBody.add("phoneOld","18345264895");
                formBody.add("phoneNew",phoneNew);
                //创建http请求
                //post请求?
                request = new Request.Builder().url("http://120.25.145.148:6000/user/phone_modify").post(formBody.build()).build();
                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
//                        返回信息
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        phone_modify.sendMessage(onFailure);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        System.out.println("body:"+body);
                        if ( body == "1"||body.equals("1")){
                            Message success = new Message();
                            success.what = 2;
//                        success.obj = user;
                            MainActivity.user.setPhone(phoneNew);
                            System.out.println("成功");
                            phone_modify.sendMessage(success);
                        }else {
                            System.out.println("修改失败");
                            Message success = new Message();
                            success.what = 1;
                            phone_modify.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }
    /**
     *手机号码修改
     */
    public void userAllInfoModify(Handler user_all_info_modify){
//        Handler handler有什么用
        //启用子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("headModify(Handler phone_modify");
                OkHttpClient client = new OkHttpClient();
                // 将对象转为json字符串方法
                String userStr = JSONObject.toJSONString(MainActivity.user);
                FormBody.Builder formBody= new FormBody.Builder();
                Request request;
                formBody.add("user",userStr);

                //创建http请求
                //post请求?
                request = new Request.Builder().url("http://120.25.145.148:6000/user/user_all_info_modify").post(formBody.build()).build();
                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login","onFailure");
//                        返回信息
                        Message onFailure = new Message();
                        onFailure.what = 0;
                        user_all_info_modify.sendMessage(onFailure);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String body = response.body().string();
                        System.out.println("body:"+body);
                        if ( body == "1"||body.equals("1")){
                            Message success = new Message();
                            success.what = 2;
////                        success.obj = user;
//                            MainActivity.user.setPhone(phoneNew);
                            System.out.println("成功");
                            user_all_info_modify.sendMessage(success);
                        }else {
                            System.out.println("修改失败");
                            Message success = new Message();
                            success.what = 1;
                            user_all_info_modify.sendMessage(success);
                        }
                    }
                });
            }
        }).start();
    }
}

