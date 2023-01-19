package com.example.track;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.track.entity.User;
import com.example.track.service.ActivityCollector;
import com.example.track.service.LoginService;
import com.mob.MobSDK;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends MyActivity {
    //    public  static  LoginActivity loginActivity;
    private ImageButton help, qqLogin, wechatLogin, alipayLogin, back;
    private ImageView mimaStatus;
    private TextView loginButton,forget,radio, getCode,user;
    private EditText username, password, phone, code;
    private Button  codeLogin, pwdLogin;
    private LinearLayout mLinearLayoutCodeLogin, mLinearLayoutPwdLogin;
    Timer timer;
    int count = 60;
    EventHandler eventHandler;
    private String phone2 = "";
    private int loginType = 0;//0代表密码登录；1代表验证码
    //    0代表不可以点击，1可以
    private int flagAffirm = 0;
    private static int isSelected = 0;
    //    判断记录验证是否正确0代表正确
    private int codeFlag = -1;
    private static final String TAG = "LoginActivity";
    //所需申请的权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
    };
    /**
     * 点击登录时
     */
    @SuppressLint("HandlerLeak")
    private final Handler handlerLogin = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            int tag = msg.what;
            if (ActivityCollector.getFirstActivity().equals("com.example.track.LoginActivity")) {
                switch (tag) {
                    case 1:
                        int arg = msg.arg1;
                        if (arg == 1) {
                            getCode.setText("重新获取");//计时结束停止计时把值恢复
                            count = 60;
                            timer.cancel();
                            getCode.setEnabled(true);
                            getCode.setBackgroundResource(R.drawable.retangle2);
                        } else {
                            getCode.setText(count + "");
                        }
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this, "获取短信验证码成功", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Log.i("Codr", "获取短信验证码失败");
                        Toast.makeText(LoginActivity.this, msg.getData().getString("code"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(LoginActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                        break;
                    case 4:
//                    当成功时=4
//                    这里可以试试看
//                    Toast.makeText(LoginActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
                        codeFlag = 0;
                        System.out.println("验证码正确Login！！！！！");
                        LoginService loginService = new LoginService();
                        loginService.userJudge(handler2, phone2);
                        break;
                    case 5:
                        Log.i("Codr", "输入的验证码不正确！");
                        Toast.makeText(LoginActivity.this, msg.getData().getString("code"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(LoginActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        }

        ;
    };
    //    线程问题
    private final Handler handler1 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(LoginActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(LoginActivity.this, "用户名或密码有误", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                User user = (User) msg.obj;
                if (UtilsFile.saveOpenFile(user.toString(),"user.csv",0,getApplicationContext())){
                    Log.d(TAG, "页面跳转到MainActivity");
//                intent.putExtra("user", (Serializable) user);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"用户信息保存失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    //    线程问题
    private final Handler handler2 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(LoginActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1 && codeFlag == 0) {//当前用户存在时
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                User user = (User) msg.obj;
                Log.d(TAG, "user.toString():" + user.toString());
                if (UtilsFile.saveOpenFile(user.toString(),"user.csv",0,getApplicationContext())){
                    Log.d(TAG, "页面跳转到MainActivity");
//                intent.putExtra("user", (Serializable) user);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"用户信息保存失败",Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 2 && codeFlag == 0) {//没有当前用户时
//                跳转到注册页面将手机号传递过去
                Log.d(TAG, "当前手机号没有注册--LoginActivity");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("phone", phone2);
//                没有结束当前页面
                startActivity(intent);
//                Toast.makeText(LoginActivity.this,"用户名或密码有误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        用于关闭当前页面
//        loginActivity=this;
        applypermission();
        MobSDK.submitPolicyGrantResult(true, null);
        init();
//        3、自定义接口
        forget.setOnClickListener(this::onClick);
        codeLogin.setOnClickListener(this::onClick);
        pwdLogin.setOnClickListener(this::onClick);
        getCode.setOnClickListener(this::onClick);
        loginButton.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);
        help.setOnClickListener(this::onClick);
        radio.setOnClickListener(this::onClick);
        user.setOnClickListener(this::onClick);
        mimaStatus.setOnClickListener(this::onClick);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = username.getText().toString();
                String editable2 = password.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if ((editable.length() < 1 || editable2.length() < 8) && loginType == 0) {
                    loginButton.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
                    loginButton.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = username.getText().toString();
                String editable2 = password.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if ((editable.length() < 1 || editable2.length() < 8) && loginType == 0) {
                    loginButton.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
                    loginButton.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if ((editablePhone.length() < 11 || editableCode.length() < 6) && loginType == 1) {
                    loginButton.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
                    loginButton.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if ((editablePhone.length() < 11 || editableCode.length() < 6) && loginType == 1) {
                    loginButton.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
                    loginButton.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
    }

    //定义判断权限申请的函数，在onCreat中调用就行
    public void applypermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("Login:", "开始添加权限");
            boolean needapply = false;
            for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
                int chechpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        PERMISSIONS_STORAGE[i]);
                if (chechpermission != PackageManager.PERMISSION_GRANTED) {
                    needapply = true;
                }
            }
            if (needapply) {
                ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_STORAGE, 1);
            }
        }
    }

    //重载用户是否同意权限的回调函数，进行相应处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {      //检查权限是否获取
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                //同意后的操作
                //Toast.makeText(MainActivity.this, permissions[i]+"已授权",Toast.LENGTH_SHORT).show();//提示
            } else {
                //不同意后的操作
                //Toast.makeText(MainActivity.this,permissions[i]+"拒绝授权",Toast.LENGTH_SHORT).show();//提示

            }
        }
    }

    public void init() {
        //绑定实例
        forget = findViewById(R.id.login_forget);
        mimaStatus = findViewById(R.id.iv_mima);
        help = findViewById(R.id.login_help);
//       qqLogin = findViewById(R.id.login_qq_login);
//       wechatLogin = findViewById(R.id.login_wechat_login);
//       alipayLogin = findViewById(R.id.login_alipay_login);
        back = findViewById(R.id.login_back);
        code = findViewById(R.id.Login_code);
        phone = findViewById(R.id.login_phone);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        getCode = findViewById(R.id.login_getCode);
        codeLogin = findViewById(R.id.login_codeLogin);
        pwdLogin = findViewById(R.id.login_pwdLogin);
        user = findViewById(R.id.Login_user);
        radio = findViewById(R.id.Login_radio);
        loginButton = findViewById(R.id.login_login);
//        forgetLogin = findViewById(R.id.login_forget);
        mLinearLayoutCodeLogin = findViewById(R.id.login_codeLogin_linearLayout);
        mLinearLayoutPwdLogin = findViewById(R.id.login_pwdLogin_linearLayout);
//????????
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {// TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
                Log.i("返回:", event + " | " + result + " | " + data.toString());
                if (result == SMSSDK.RESULT_COMPLETE) {//成功回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交短信、语音验证码成功
//                       Activity之间或者线程间传递数据
                        Bundle bundle = new Bundle();
//                      在验证码正确的前提下做判断，做出判断是注册还是登录
                        bundle.putString("code", "登录成功");
                        System.out.println("code：登录成功");
                        Message message = new Message();
                        message.what = 4;
                        message.setData(bundle);
                        handlerLogin.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//不同验证类型
                        Message message = new Message();
                        message.what = 2;
                        handlerLogin.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {//获取语音验证码成功
                        Message message = new Message();
                        message.what = 2;
                        handlerLogin.sendMessage(message);
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {//失败回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交返回
                        Bundle bundle = new Bundle();
                        bundle.putString("code", "输入的验证码不正确！");
                        Message message = new Message();
                        message.what = 5;
                        message.setData(bundle);
                        handlerLogin.sendMessage(message);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("code", data.toString());
                        Message message = new Message();
                        message.what = 3;
                        message.setData(bundle);
                        handlerLogin.sendMessage(message);
                    }

                } else {//其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    private void onClick(View view) {
        phone2 = "";
        String code2 = "";
        String usn = "";
        String pwd = "";
        int id = view.getId();
        switch (id) {
            case R.id.login_forget:
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
//                没有结束当前页面
                startActivity(intent);
                break;
            case R.id.login_getCode:
                phone2 = phone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone2)) {//倒计时
                    CountdownStart();
                    getCode.setBackgroundResource(R.drawable.retangle3);
                    getVerificationCode("86", phone2);
                } else {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.login_login:
//                ???
                phone2 = phone.getText().toString().trim();
                code2 = code.getText().toString().trim();
                usn = username.getText().toString().trim();
                pwd = password.getText().toString().trim();
                LoginService loginService = new LoginService();
                if (flagAffirm == 0) {
                    break;
                } else if (loginType == 0) {
                    if (isSelected == 0) {
                        Toast.makeText(LoginActivity.this, "请先同意用户条款", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(usn)) {
                        Toast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(pwd)) {
                        Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                    } else {
                        loginService.login(handler1, usn, pwd);
//                        loginVerify(usn,pwd);
                    }
                } else {
                    if (isSelected == 0) {
                        Toast.makeText(LoginActivity.this, "请先同意用户条款", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(phone2)) {
                        Toast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(code2)) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
                    } else {//登录逻辑
                        /**
                         * cn.smssdk.SMSSDK.class
                         * 提交验证码
                         * @param country   国家区号
                         * @param phone     手机号
                         * @param code      验证码
                         */
                        SMSSDK.submitVerificationCode("86", phone2, code2);//提交验证码
                    }
                }
                break;
            case R.id.login_codeLogin:
                mLinearLayoutCodeLogin.setVisibility(View.VISIBLE);
                mLinearLayoutPwdLogin.setVisibility(View.GONE);
                codeLogin.setTextColor(0xFF0990FF);
                pwdLogin.setTextColor(Color.BLACK);
                loginButton.setText("登录/注册");
                loginType = 1;
                break;
            case R.id.login_pwdLogin:
                mLinearLayoutCodeLogin.setVisibility(View.GONE);
                mLinearLayoutPwdLogin.setVisibility(View.VISIBLE);
                codeLogin.setTextColor(Color.BLACK);
                pwdLogin.setTextColor(0XFF0990FF);
                loginButton.setText("登录");
                loginType = 0;
                break;
            case R.id.login_back:
//                ??????
//                Intent Main_intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(Main_intent);
                finish();
                break;
            case R.id.Login_radio:
                if (isSelected == 0) {
                    radio.setBackground(getResources().getDrawable(R.drawable.radio_register_selected));
                    isSelected = 1;
                } else {
                    radio.setBackground(getResources().getDrawable(R.drawable.radio_register));
                    isSelected = 0;
                }
                break;
            case R.id.iv_mima:
//                点击查看密码隐藏密码
                TransformationMethod method = password.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mimaStatus.setBackgroundResource(R.mipmap.mimayincang);
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mimaStatus.setBackgroundResource(R.mipmap.chakanmima);
                }
                break;
        }
    }

    /**
     * cn.smssdk.SMSSDK.class
     * 请求文本验证码
     *
     * @param country 国家区号
     * @param phone   手机号
     */
    public static void getVerificationCode(String country, String phone) {
        //获取短信验证码
        SMSSDK.getVerificationCode(country, phone);
    }

    /**
     * cn.smssdk.SMSSDK.class
     * 请求文本验证码(带模板编号)
     *
     * @param tempCode 模板编号
     * @param country  国家区号
     * @param phone    手机号
     */
    public static void getVerificationCode(String tempCode, String country, String phone) {
        //获取短信验证码
        SMSSDK.getVerificationCode(tempCode, country, phone);
    }

    //倒计时函数
    private void CountdownStart() {
        getCode.setEnabled(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                if (count != 0) {
                    count--;
                } else {
                    return;
                }
                handlerLogin.sendMessage(message);
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }

//    public void save(String user) {
//            String fileName = "user.csv";
//            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
//            File directory = contextWrapper.getDir(getFilesDir().getName(), ContextWrapper.MODE_PRIVATE);
//            File file = new File(directory, fileName);
////        String data = "FirstName,LastName,PhoneNumber";
//            FileOutputStream outputStream;
//            try {
//                Log.d(TAG, "用户信息保存到user.csv中");
//                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//                outputStream.write(user.getBytes());
//                outputStream.close();
//            } catch (IOException e) {
//                Log.d(TAG, "用户信息保存到user.csv中--没有成功");
//                e.printStackTrace();
//            }
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        finish();
//        startActivity(intent);
//    }
}