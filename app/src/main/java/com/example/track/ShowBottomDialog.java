package com.example.track;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class ShowBottomDialog {
    private View view;
    private TextView takePhoto,takePic,takeCancel;
    private Dialog dialog;
    public void BottomDialog(Context context) {
         dialog = new Dialog(context, R.style.DialogTheme);
        //1、使用Dialog、设置style底部弹窗实现样式
        //2、设置布局
        view = View.inflate(context, R.layout.dialog_picture, null);
        init();
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
//三个点击事件
//        takePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("55","ShowBottomDialog——打开相机");
//            }
//        });
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("55","ShowBottomDialog——打开相机");
//            }
//        });
//        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("55","ShowBottomDialog——打开相机");
//            }
//        });
//
//        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("55","ShowBottomDialog——打开相机");
////                dialog.dismiss();
//            }
//        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void init(){
        takePhoto = view.findViewById(R.id.tv_take_photo);
        takePic = view.findViewById(R.id.tv_take_pic);
        takeCancel = view.findViewById(R.id.tv_cancel);
//        takeCancel.setOnClickListener(this::onClick);
//        takeCancel.setOnClickListener(this::onClick);
//        takeCancel.setOnClickListener(this::onClick);
    }
    private void onClick(View view) {
        int id = view.getId();
        System.out.println("view.getId():"+id);
        switch (id){
//            ？？？？？
            case R.id.tv_take_photo:
                break;
            case R.id.tv_take_pic:
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
        }
    }
}
