package com.example.track.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.MainActivity;
import com.example.track.R;
import com.example.track.entity.Item3;

import java.util.ArrayList;
import java.util.List;

public class QrcodeBodyItem1Fragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Item3> item1List = new ArrayList<>();
    private Button call,sos,fix,brake;
    private ImageButton call_img,sos_img,fix_img,brake_img;
    private ActivityResultLauncher mCallPermissionResultLauncher;//拨打电话处理
    private ActivityResultLauncher mSendPermissionResultLauncher;//发送短信处理


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body_qrcode_item1, container, false);

        call = v.findViewById(R.id.body_qrcode_item1_button_call);
        sos = v.findViewById(R.id.body_qrcode_item1_button_sos);
        fix = v.findViewById(R.id.body_qrcode_item1_button_fix);

        call_img = v.findViewById(R.id.body_qrcode_item1_image_call);
        sos_img = v.findViewById(R.id.body_qrcode_item1_image_sos);
        fix_img = v.findViewById(R.id.body_qrcode_item1_image_fix);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int familynumber = 11111;                                    //个人信息界面界面中输入

                AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                        .setTitle("联系紧急联系人")
                        .setMessage("是否要联系紧急联系人")
                        .setIcon(R.mipmap.brake)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "确定", Toast.LENGTH_SHORT).show();
                                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + familynumber));
                                startActivity(dialIntent);
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //取消
                            }
                        })
                        .create();
                alertDialog2.show();


            }
        });

        call_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int familynumber = 11111;                                    //个人信息界面界面中输入

                AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                        .setTitle("联系紧急联系人")
                        .setMessage("是否要联系紧急联系人")
                        .setIcon(R.mipmap.brake)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Uri uri=Uri.parse("tel:10086");//求救号码
                                startActivity(new Intent(Intent.ACTION_CALL,uri));


                                SmsManager smsManager=SmsManager.getDefault();
                                //短信号码
                                smsManager.sendTextMessage("10086", null, "MainActivity.getPosition()", null, null);
                                Toast.makeText(getContext(), "求救短信已经发出", Toast.LENGTH_SHORT).show();


                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //取消
                            }
                        })
                        .create();
                alertDialog2.show();


            }
        });



        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sos = 110;

                AlertDialog alertDialog3 = new AlertDialog.Builder(getContext())
                        .setTitle("报警")
                        .setMessage("是否要报警")
                        .setIcon(R.mipmap.brake)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri=Uri.parse("tel:10086");//求救号码
                                startActivity(new Intent(Intent.ACTION_CALL,uri));


                                SmsManager smsManager=SmsManager.getDefault();
                                //短信号码
                                smsManager.sendTextMessage("10086", null, "MainActivity.getPosition()", null, null);
                                Toast.makeText(getContext(), "求救短信已经发出", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog3.show();

            }
        });

        sos_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sos = 110;

                AlertDialog alertDialog3 = new AlertDialog.Builder(getContext())
                        .setTitle("报警")
                        .setMessage("是否要报警")
                        .setIcon(R.mipmap.brake)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "确定", Toast.LENGTH_SHORT).show();
                                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sos));
                                startActivity(dialIntent);
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog3.show();

            }
        });



        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 132456));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });

//        brake=v.findViewById(R.id.brake);
//        brake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                brake.setTextColor(Color.parseColor("#66ccff") );
//            }
//        });




        return v;
    }
}
