package com.example.track.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track.R;

public class Item4Adapter extends RecyclerView.Adapter{

    static class ViewHolder extends RecyclerView.ViewHolder{
        private Button call,sos,fix,brake;
        private ImageButton call_img,sos_img,fix_img,brake_img;

        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
        call = itemView.findViewById(R.id.body_qrcode_item1_button_call);
        sos = itemView.findViewById(R.id.body_qrcode_item1_button_sos);
        fix = itemView.findViewById(R.id.body_qrcode_item1_button_fix);

        call_img = itemView.findViewById(R.id.body_qrcode_item1_image_call);
        sos_img = itemView.findViewById(R.id.body_qrcode_item1_image_sos);
        fix_img = itemView.findViewById(R.id.body_qrcode_item1_image_fix);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int familynumber = 11111;                                    //个人信息界面界面中输入

                    AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                            .setTitle("联系紧急联系人")
                            .setMessage("是否要联系紧急联系人")
                            .setIcon(R.mipmap.brake)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
                                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + familynumber));
                                    context.startActivity(dialIntent);
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

                    AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                            .setTitle("联系紧急联系人")
                            .setMessage("是否要联系紧急联系人")
                            .setIcon(R.mipmap.brake)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Uri uri=Uri.parse("tel:10086");//求救号码
                                    context.startActivity(new Intent(Intent.ACTION_CALL,uri));


                                    SmsManager smsManager=SmsManager.getDefault();
                                    //短信号码
                                    smsManager.sendTextMessage("10086", null, "MainActivity.getPosition()", null, null);
                                    Toast.makeText(context, "求救短信已经发出", Toast.LENGTH_SHORT).show();


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

                    AlertDialog alertDialog3 = new AlertDialog.Builder(context)
                            .setTitle("报警")
                            .setMessage("是否要报警")
                            .setIcon(R.mipmap.brake)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Uri uri=Uri.parse("tel:10086");//求救号码
                                    context.startActivity(new Intent(Intent.ACTION_CALL,uri));


                                    SmsManager smsManager=SmsManager.getDefault();
                                    //短信号码
                                    smsManager.sendTextMessage("10086", null, "MainActivity.getPosition()", null, null);
                                    Toast.makeText(context, "求救短信已经发出", Toast.LENGTH_SHORT).show();
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

                    AlertDialog alertDialog3 = new AlertDialog.Builder(context)
                            .setTitle("报警")
                            .setMessage("是否要报警")
                            .setIcon(R.mipmap.brake)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
                                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sos));
                                    context.startActivity(dialIntent);
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
                    context.startActivity(dialIntent);
                }
            });
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_body_qrcode_item1,parent,false);
        ViewHolder holder = new ViewHolder(view, view.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}


