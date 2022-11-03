package com.example.track;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.track.fragment.TemperatureListFragment;

public class TemperatureDataActivity extends AppCompatActivity {
//    //用来存储年月日
//    int year,month,day;
//    //存储页面上的日期选择器
//    DatePicker datePicker;
//    Button datapickerVISIBLEButton;
private String currentMon, currentDay, currentHour, currentMin; //当前选中的月、日、时、分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_data);

        Fragment fragment =  getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new TemperatureListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)//fragment_container_view_list
                    .commit();
        }

    }
}