package com.example.track;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapsInitializer;
import com.example.track.entity.Temperature;
import com.example.track.entity.Trip;
import com.example.track.entity.User;
import com.example.track.fragment.MineBodyFragment;
import com.example.track.fragment.NavigationFragment;
import com.example.track.fragment.QrcodeBodyFragment;
import com.example.track.service.MainActivityUpdateService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private ImageButton mine,navigation,qrcode;
    private TextView mineText,homepageText,qrcodeText;
    private static User user = new User("18345264895","123321");

    private ActivityResultLauncher mLocationPermissionResultLauncher;//定位权限处理，同时负责调用下面两个处理
    private ActivityResultLauncher mCallPermissionResultLauncher;//拨打电话处理
    private ActivityResultLauncher mSendPermissionResultLauncher;//发送短信处理


    public static Temperature currentTemperature;
    public static Trip currentTrip;

    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener mMapLocationListener;
    private String mEmergencyText = "";

    private TextView mOffTextView;
    private Dialog mDialog;
    private Timer mOffTime;
    public static boolean isNeed;//是否需要救援


    private Handler handler1 = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(MainActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Temperature temperature = (Temperature) msg.obj;
                System.out.println(temperature.toString());
                currentTemperature = temperature;
                int ifOverHeated = temperature.getWarning_flag();
                if (ifOverHeated == 0) {
                    Toast.makeText(MainActivity.this,"刹车片过热",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private Handler getPositionHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 9){
                if (isNeed) {
                    initDialog();
                    Toast.makeText(MainActivity.this, "emergency", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private Handler mOffHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what > 0) {
                mOffTextView = new TextView(getApplicationContext());
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //绑定实例
        mine = findViewById(R.id.mine);
        navigation = findViewById(R.id.navigation);
        qrcode = findViewById(R.id.qrcode);
        mineText = findViewById(R.id.mine_text);
        homepageText = findViewById(R.id.navigation_text);
        qrcodeText = findViewById(R.id.qrcode_text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.index_body_fragment_container);

        isNeed = false;
        mLocationPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result){
                //获取权限
                mCallPermissionResultLauncher.launch(Manifest.permission.CALL_PHONE);
                mSendPermissionResultLauncher.launch(Manifest.permission.SEND_SMS);
            }else {
                Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
            }
        });

        AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);

        //Fragment页面的添加
        Fragment homepageFragment = new NavigationFragment();
        Fragment qrcodeFragment = new QrcodeBodyFragment();
        Fragment mineFragment = new MineBodyFragment();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,mineFragment).commit();
        fragmentManager.beginTransaction().hide(mineFragment).commit();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,homepageFragment).commit();
        fragmentManager.beginTransaction().hide(homepageFragment).commit();
        fragmentManager.beginTransaction().add(R.id.index_body_fragment_container,qrcodeFragment).commit();
        fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
        fragmentManager.beginTransaction().show(homepageFragment).commit();

        homepageText.setTextColor(Color.parseColor("#0582E8"));
        navigation.setImageResource(R.mipmap.navigation_blue);

        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //css以及点击事件

                navigation.setImageResource(R.mipmap.navigation_black);
                mine.setImageResource(R.mipmap.mine_blue);
                qrcode.setImageResource(R.mipmap.qrcode_black);
                fragmentManager.beginTransaction().hide(homepageFragment).commit();
                fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
                fragmentManager.beginTransaction().show(mineFragment).commit();
                mineText.setTextColor(Color.parseColor("#0582E8"));
                qrcodeText.setTextColor(Color.parseColor("#808080"));
                homepageText.setTextColor(Color.parseColor("#808080"));
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //css以及点击事件
                navigation.setImageResource(R.mipmap.navigation_blue);
                mine.setImageResource(R.mipmap.mine_black);
                qrcode.setImageResource(R.mipmap.qrcode_black);
                fragmentManager.beginTransaction().hide(qrcodeFragment).commit();
                fragmentManager.beginTransaction().hide(mineFragment).commit();
                fragmentManager.beginTransaction().show(homepageFragment).commit();
                mineText.setTextColor(Color.parseColor("#808080"));
                qrcodeText.setTextColor(Color.parseColor("#808080"));
                homepageText.setTextColor(Color.parseColor("#0582E8"));

            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //css以及点击事件
                navigation.setImageResource(R.mipmap.navigation_black);
                mine.setImageResource(R.mipmap.mine_black);
                qrcode.setImageResource(R.mipmap.qrcode_blue);
                fragmentManager.beginTransaction().hide(homepageFragment).commit();
                fragmentManager.beginTransaction().hide(mineFragment).commit();
                fragmentManager.beginTransaction().show(qrcodeFragment).commit();
                mineText.setTextColor(Color.parseColor("#808080"));
                qrcodeText.setTextColor(Color.parseColor("#0582E8"));
                homepageText.setTextColor(Color.parseColor("#808080"));

            }
        });




        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,9,1,
                TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(128));

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Runnable temperatureRunnable = new MainActivityUpdateService().getCurrentTemperature(handler1);
                pool.execute(temperatureRunnable);
            }
        },0,2000);

/**
 * 以下为报警组件
 */
        initWarning();
        isNeed = true;
        if (isNeed) {
            getLocationText();
            warning();
        }


    }

    public void getLocationText(){
        mMapLocationListener = aMapLocation -> {
            if(aMapLocation!=null)
            {
                if(aMapLocation.getErrorCode()==0)
                {
                    String str = "";
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
                    str="我在以下位置遭遇严重车祸急需救援"+text+"\n救救我";
                    //这里 try catch fragment还是放到onViewCreated比较合适，减少崩溃几率

                        aMapLocationClient.stopLocation();
                        Message msg = new Message();
                        msg.what = 9;
                        msg.obj = str;
                        getPositionHandler.sendMessage(msg);
                }
                else
                {
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());

                }
            }
        };
    }
    public void initWarning(){


        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        try {
            aMapLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void warning(){
        mCallPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
            if (result){
                Uri uri=Uri.parse("tel:17816140412");//求救号码
                startActivity(new Intent(Intent.ACTION_CALL,uri));
            }else {
                Toast.makeText(this, "fail to call", Toast.LENGTH_SHORT).show();

                //mLocationPermissionResultLauncher.launch(Manifest.permission.CALL_PHONE);
            }
        });
        mSendPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
            if (result){
                SmsManager smsManager = SmsManager.getDefault();
                //短信号码
                smsManager.sendTextMessage("10086", null, "求救信息", null, null);
                Toast.makeText(this, "求救短信已经发出", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "fail to send text", Toast.LENGTH_SHORT).show();
                //mLocationPermissionResultLauncher.launch(Manifest.permission.SEND_SMS);
            }
        });

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
    };



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




    public static String getUser(){
        return user.getUsername();
    }

    public static Temperature getCurrentTemperature(){
        return currentTemperature;
    }



}