package com.example.track.model;

import androidx.lifecycle.ViewModel;
import com.example.track.entity.Temperature;
import java.util.List;

public class TemperatureListViewModel extends ViewModel {
    //变量定义
    private List<Temperature> mSafetyList;
    private String curTemperature;
    private String search_time;
    //    提取数据所有温度数据
    public List<Temperature> getSafetyList(String time) {//给外部一个接口
        search_time=time;

        return mSafetyList;
    }




}