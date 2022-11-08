package com.example.track.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.track.R;
import com.example.track.entity.Temperature;

public class TemperatureFragment extends Fragment {
    private Temperature mSafety;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSafety = new Temperature();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_temperature,container,false);
//        初始化
                return v;
    }

    @Override
    public void onStart() {//??
        super.onStart();
    }
}
