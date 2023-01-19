package com.example.track;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.Timer;
import java.util.TimerTask;

public class RescueActivity extends AppCompatActivity {

    private ActivityResultLauncher mLocationPermissionResultLauncher;//定位权限处理，同时负责调用下面两个处理
    private ActivityResultLauncher mCallPermissionResultLauncher;//拨打电话处理
    private ActivityResultLauncher mSendPermissionResultLauncher;//发送短信处理

    MapView mMapView;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    AMapLocationClient aMapLocationClient;
    AMapLocationListener mMapLocationListener;
    String mEmergencyText;
    private TextView mOffTextView;
    private Handler mOffHandler;
    private Timer mOffTime;
    private Dialog mDialog;
    boolean isNeed;//是否需要救援


    //创建对话框
    //创建时间来取消救援的对话框
    void initDialog(){

        mOffTextView = new TextView(this);
        mOffTextView.setTextSize(26);

        mDialog = new AlertDialog.Builder(this)
                .setTitle("执行警告")
                .setCancelable(false)
                .setView(mOffTextView)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mOffTime.cancel();
                        isNeed=false;
                    }
                })
                .create();
        mDialog.show();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = 1000;
        params.height = 1000;
        mDialog.getWindow().setAttributes(params);
        mDialog.setCanceledOnTouchOutside(false);

        mOffHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what > 0) {

                    //动态显示倒计时
                    mOffTextView.setText("检测到您受到剧烈撞击，即将自动发送求救短信与拨打求救电话，如需取消，请在倒计时前结束前取消    即将执行，剩余时间："+msg.what);

                } else {
                    //倒计时结束自动关闭f

                    if(mDialog!=null){
                        mDialog.dismiss();
                    }
                    //off();关闭后的操作
                    mOffTime.cancel();
                    mLocationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                super.handleMessage(msg);
            }

        };

        //倒计时

        mOffTime = new Timer(true);
        TimerTask tt = new TimerTask() {
            int countTime = 10;
            public void run() {
                if (countTime > 0) {
                    countTime--;
                }
                Message msg = new Message();
                msg.what = countTime;
                mOffHandler.sendMessage(msg);
            }
        };
        mOffTime.schedule(tt, 1000, 1000);
    }

    //救援函数
    public void Emergency(boolean isNeed) throws InterruptedException {
        if (isNeed) {
            initDialog();
            Toast.makeText(this, "emergency", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        isNeed = true;
        mLocationPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result){
                mCallPermissionResultLauncher.launch(Manifest.permission.CALL_PHONE);
                mSendPermissionResultLauncher.launch(Manifest.permission.SEND_SMS);
            }else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            MapsInitializer.updatePrivacyShow(getApplicationContext(),true,true);
            MapsInitializer.updatePrivacyAgree(getApplicationContext(),true);

            AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
            AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);
        }catch (Exception e){

        }

        //获取地图控件引用
        mMapLocationListener = aMapLocation -> {
            if(aMapLocation!=null)
            {
                if(aMapLocation.getErrorCode()==0)
                {
                    double latitude=aMapLocation.getLatitude();
                    double Longitude=aMapLocation.getLongitude();
                    String province=aMapLocation.getProvince();
                    String city=aMapLocation.getCity();
                    String district=aMapLocation.getDistrict();
                    String streetNumber=aMapLocation.getStreetNum();
                    String text="经度: "+Longitude+"\n"
                            +"纬度: "+latitude+"\n"
                            +"详细位置: "+province+city+district+streetNumber;
                    //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    mEmergencyText="我在以下位置遭遇严重车祸急需救援" + text + "\n救救我";
                    //这里 try catch fragment还是放到onViewCreated比较合适，减少崩溃几率
                    try {
                        Emergency(isNeed);
                        aMapLocationClient.stopLocation();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());

                }
            }
        };

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        if (aMap == null) {
            aMap = mMapView.getMap();

        }
        mMapView.onCreate(savedInstanceState);
        try {
            aMapLocationClient=new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCallPermissionResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
            if (result){
                Uri uri=Uri.parse("tel:17816140412");//求救号码
                startActivity(new Intent(Intent.ACTION_CALL,uri));
            }else {
                Toast.makeText(this, "fail to call", Toast.LENGTH_SHORT).show();

                //mLocationPermissionResultLauncher.launch(Manifest.permission.CALL_PHONE);
            }
        });
        mSendPermissionResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
            if (result){
                SmsManager smsManager=SmsManager.getDefault();
                //短信号码
                smsManager.sendTextMessage("10086", null, mEmergencyText, null, null);
                Toast.makeText(this, "求救短信已经发出", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "fail to send text", Toast.LENGTH_SHORT).show();
                //mLocationPermissionResultLauncher.launch(Manifest.permission.SEND_SMS);
            }
        });



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        aMapLocationClient.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}