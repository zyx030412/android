package com.example.track;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetTrackInfoActivity extends AppCompatActivity{
    private RelativeLayout carInfoSetArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_car_info);
        carInfoSetArea = findViewById(R.id.car_info_select_area_change);
        carInfoSetArea.setVisibility(View.GONE);

    }
}
