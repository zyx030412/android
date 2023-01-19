package com.example.track.service;

import com.example.track.entity.CarInfo;
import com.example.track.entity.Trip;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoreService {

    public final OkHttpClient client = new OkHttpClient();

    public void storeTrip(Trip trip){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                //创建http请求
                String url = "http://120.25.145.148:6000/store/trip?mileage="+trip.getMileage()+"&time="+trip.getTime()+"&overSpeedTime="+ trip.getOverSpeedTime()+"&startTime="+ trip.getStartTime()+"&endTime="+ trip.getEndTime()+"&startAddress="+ trip.getStartAddress()+"&endAddress="+ trip.getEndAddress()+"&startLo="+ trip.getStartLo()+"&startLa="+ trip.getStartLa()+"&endLo="+ trip.getEndLo()+"&endLa="+ trip.getEndLa()+"&username="+ trip.getUsername();

                System.out.println(url);
                Request request = new Request.Builder().url(url)
                        .get().build();

                Call call = client.newCall(request);
                //异步访问
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        System.out.println("请求成功");
                    }
                });


            }


        }).start();
    }

    public void storeCarInfo(CarInfo carInfo) {

    }
}
