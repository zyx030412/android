package com.example.track.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.track.R;
import com.example.track.model.View.CirStatisticGraph;
import com.example.track.model.View.WaveView;

public class QrcodeBodyItem4Fragment extends Fragment {
    private CirStatisticGraph cirStatisticGraph;
    private View mCurrentTemperature;
    private WaveView waveview;
    private float waterPrecent;//范围0.00--1.00

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode_item4, container, false);

        init(v);
        //设置水位百分比
        waveview.setPrecentChangeListener(new WaveView.PrecentChangeListener() {
            @Override
            public void precentChange(double precent) {
//                tvPrecent.setText("当前进度：" + precent + "");
            }
        });

        return v;
    }

    public void init(View v){

        mCurrentTemperature = v.findViewById(R.id.view_221);
        LinearLayout layout_view = v.findViewById(R.id.activity_currentTemp_view);


        waveview = v.findViewById(R.id.waveview);

        setWaterPrecent();
        waveview.reset();
        waveview.start();

    }

    public void setWaterPrecent(){
        /**
         *当前是静态数据
         */
        waterPrecent=0.3f;
        if (waterPrecent<=0.4f){
            waveview.setTextColor(Color.RED);
        }
        waveview.setPrecent(waterPrecent);
    }

}
