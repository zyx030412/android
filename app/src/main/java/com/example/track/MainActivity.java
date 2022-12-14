package com.example.track;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapsInitializer;
import com.example.track.entity.Temperature;
import com.example.track.entity.Trip;
import com.example.track.entity.User;
import com.example.track.fragment.MineBodyFragment;
import com.example.track.fragment.NavigationFragment;
import com.example.track.fragment.QrcodeBodyFragment;
import com.example.track.model.View.CirStatisticGraph;
import com.example.track.service.MainActivityUpdateService;
import com.example.track.service.StoreService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {
    private ImageButton mine,navigation,qrcode;
    private TextView mineText,homepageText,qrcodeText;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener mMapLocationListener;
//    private String mEmergencyText = "";    //??????AMapLocationClient?????????
    public AMapLocationClient mLocationClient = null;
    //???????????????????????????
    public AMapLocationListener mLocationListener = null;
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;
    private TextView mOffTextView;
    private Dialog mDialog;
    private Timer mOffTime;
    public static boolean isNeed;//??????????????????
    public static int isRescue = 0;
    private static User user = new User("18345264895","123321");
    public static Temperature currentTemperature;
    public static Trip currentTrip;
    public static Activity instance;
    private static ThreadPoolExecutor pool;
    private static Timer time;


    private Handler handler1 = new Handler(Looper.myLooper()){
//        int flag_1 = 0;
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
//                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
//                Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {

                Temperature temperature = (Temperature) msg.obj;
                System.out.println(temperature.toString());
                currentTemperature = temperature;
                int ifOverHeated = temperature.getWarning_flag();
                if (ifOverHeated == 0) {
                    Toast.makeText(MainActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                }
                if (Float.parseFloat(temperature.getTemperature()) > 100){
                    initDialog();
                    isRescue = 1;
                }
            }
        }
    };
    private Handler mOffHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        instance = this;
        //????????????
        mine = findViewById(R.id.mine);
        navigation = findViewById(R.id.navigation);
        qrcode = findViewById(R.id.qrcode);
        mineText = findViewById(R.id.mine_text);
        homepageText = findViewById(R.id.navigation_text);
        qrcodeText = findViewById(R.id.qrcode_text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.index_body_fragment_container);

        isNeed = false;



        AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);

        //Fragment???????????????
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

                //css??????????????????

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
                //css??????????????????
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
                //css??????????????????
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

        pool = new ThreadPoolExecutor(3,9,1, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(128));

        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Runnable temperatureRunnable = new MainActivityUpdateService().getCurrentTemperature(handler1);
                pool.execute(temperatureRunnable);
            }
        },0,2000);



/**
 * ?????????????????????
 */


    }

    @SuppressLint("ResourceAsColor")
    void initDialog(){
        if (isRescue == 0){
            LayoutInflater mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.warning_dialog, null);

            LinearLayout linearLayout;
            linearLayout = view.findViewById(R.id.warning_dialog);
            TextView textView;
            textView = view.findViewById(R.id.warning_text);

            mDialog = new AlertDialog.Builder(this,R.style.warningDialog)
                    .setTitle("??????")
                    .setCancelable(false)
                    .setView(linearLayout)
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            mOffTime.cancel();
                            isNeed=false;
                        }
                    }).create();
            mDialog.show();
            WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
            params.width = 1000;
            params.height = 1000;

            mDialog.getWindow().setAttributes(params);
            mDialog.setCanceledOnTouchOutside(false);

            mOffHandler = new Handler(Looper.myLooper()) {
                public void handleMessage(Message msg) {

                    if (msg.what > 0) {

                        //?????????????????????
                        textView.setText("???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????    ??????????????????????????????"+msg.what);

                    } else {
                        //???????????????????????????f

                        if(mDialog!=null){
                            mDialog.dismiss();
                        }
                        //off();??????????????????
                        mOffTime.cancel();
                        rescue();
                    }
                    super.handleMessage(msg);
                }

            };

            //?????????

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

    }


    public void rescue(){
        Uri uri=Uri.parse("tel:120");//????????????
        startActivity(new Intent(Intent.ACTION_CALL,uri));

        SmsManager smsManager=SmsManager.getDefault();
        //????????????
//        String text = getPosition();
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //??????????????????amapLocation?????????????????????
                        String la = String.valueOf(amapLocation.getLatitude());
                        String lo = String.valueOf(amapLocation.getLongitude());
                        String address = amapLocation.getAddress();
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = simpleDateFormat.format(date);
                        String warn = "???????????????????????????????????????????????????????????????\n"+"??????:"+la+"\n??????:"+lo+"\n????????????:"+address+"\n";
                        System.out.println(warn);
                        smsManager.sendTextMessage("10001", null, warn, null, null);
                        Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
                    }else {
                        //???????????????????????????ErrCode????????????????????????????????????????????????errInfo???????????????????????????????????????
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.startLocation();


    }


    public static String getUser(){
        return user.getUsername();
    }

    public static Temperature getCurrentTemperature(){
        return currentTemperature;
    }

    public static void closeRun(){
        pool.shutdown();
        time.cancel();
        instance.finish();
    }

}