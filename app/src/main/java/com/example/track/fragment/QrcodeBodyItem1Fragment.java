package com.example.track.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;
import com.example.track.entity.Item3;

import java.util.ArrayList;
import java.util.List;

public class QrcodeBodyItem1Fragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Item3> item1List = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode_item1, container, false);
        return v;
    }
}
