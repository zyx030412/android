package com.example.track;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.track.entity.User;
import com.example.track.service.ActivityCollector;
import com.example.track.service.LoginService;
import com.mob.MobSDK;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

//思路点击获取验证码时先检查有没有这个用户2.有时才可以获取验证码3.验证码正确时登录
public class ForgetActivity extends MyActivity {
    private static final String TAG="ForgetActivity";
    private TextView affirm,getCode;
    private ImageView mimaStatus1,mimaStatus2,back;
    private EditText password1,password2,phone,code;
    private String  password1Str,password2Str,phoneStr,codeStr;
    private User user;
    Timer timer;
    int count = 60;
    EventHandler eventHandler;
    //    判断记录验证是否正确0代表正确
    private int codeFlag =-1;
    //    0代表不可以点击，1可以
    private int flagAffirm=0;
    @SuppressLint("HandlerLeak")
    private final Handler handlerForget = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg) {
//            this.toString():Handler (com.example.track.ForgetActivity$1) {2cea1d9}
//            ActivityCollector.getFirstActivity():com.example.track.ForgetActivity@f8cb6f6
            Log.d(TAG,"ActivityCollector.getFirstActivity():"+ActivityCollector.getFirstActivity());
            Log.d(TAG,"this.getClass().getName():"+this.getClass().getName());

            if (ActivityCollector.getFirstActivity().equals("com.example.track.ForgetActivity")){
            int tag = msg.what;
            switch (tag){
                case 1:
                    int arg = msg.arg1;
                    if(arg==1){
                        getCode.setText("重新获取");//计时结束停止计时把值恢复
                        count = 60;
                        timer.cancel();
                        getCode.setEnabled(true);
                        getCode.setBackgroundResource(R.drawable.retangle2);
                    }else{
                        getCode.setText(count+"");
                    }
                    break;
                case 2:
                    Toast.makeText(ForgetActivity.this,"获取短信验证码成功",Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Log.i("Codr","获取短信验证码失败");
                    Toast.makeText(ForgetActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
                    //Toast.makeText(ForgetActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                    break;
                case 4:
//                    当成功时=4
//                    这里可以试试看
//                    Toast.makeText(ForgetActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
//                    codeFlag=0;
                    LoginService loginService = new LoginService();
                    loginService.modify(handler_modify, phoneStr, password1Str);
                    System.out.println("验证码正确Forget！！！！！");
                    break;
                case 5:
                    Log.i("Codr","输入的验证码不正确！");
                    Toast.makeText(ForgetActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
                    //Toast.makeText(ForgetActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            }
        };
    };
    //    修改密码
    private final Handler handler_modify = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0){
                Toast.makeText(ForgetActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
            }else if (msg.what == 1){
                Toast.makeText(ForgetActivity.this,"密码修改失败",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 2){
                Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
//                User user = (User) msg.obj;
//                intent.putExtra("user", (Serializable) user);
                if (UtilsFile.saveOpenFile(user.toString(),"user.csv",0,getApplicationContext())){
                    Log.d(TAG, "页面跳转到MainActivity");
                    ActivityCollector.finishOneActivity(LoginActivity.class.getName());
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgetActivity.this,"用户信息保存失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    //    线程问题检查当前手机号是否已经注册个
    private final Handler handler2_userJudge = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0){
                Toast.makeText(ForgetActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1){//当前用户存在时
                user = (User) msg.obj;
                CountdownStart();
                getCode.setBackgroundResource(R.drawable.retangle3);
                getVerificationCode("86",phoneStr);
            }else if (msg.what == 2)//没有当前用户时
                Toast.makeText(ForgetActivity.this,"当前用户没有注册",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        MobSDK.submitPolicyGrantResult(true,null);
        init();
    }
    public void init(){
        affirm = findViewById(R.id.forget_affirm);
        getCode =findViewById(R.id.forget_getCode);
        mimaStatus1=findViewById(R.id.forget_mima1_image);
        mimaStatus2=findViewById(R.id.forget_mima2_image);
        back=findViewById(R.id.forget_back);
        password1=findViewById(R.id.forget_password1);
        password2=findViewById(R.id.forget_password2);
        phone=findViewById(R.id.forget_phone);
        code=findViewById(R.id.forget_code);
        getCode.setOnClickListener(this::onClick);
        mimaStatus1.setOnClickListener(this::onClick);
        mimaStatus2.setOnClickListener(this::onClick);
        affirm.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);
        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = password1.getText().toString();
                String editable2 = password2.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if (editable.length()<8||editable2.length()<8||editablePhone.length()<11||editableCode.length()<6){
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm=0;
                }
                else{
                    flagAffirm=1;
                    affirm.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String editable = password1.getText().toString();
                String editable2 = password2.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if (editable.length()<8||editable2.length()<8||editablePhone.length()<11||editableCode.length()<6){
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm=0;
                }
                else{
                    flagAffirm=1;
                    affirm.setBackgroundResource(R.drawable.retangle);
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
                String editable = password1.getText().toString();
                String editable2 = password2.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if (editable.length()<8||editable2.length()<8||editablePhone.length()<11||editableCode.length()<6){
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm=0;
                }
                else{
                    flagAffirm=1;
                    affirm.setBackgroundResource(R.drawable.retangle);
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
                String editable = password1.getText().toString();
                String editable2 = password2.getText().toString();
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if (editable.length()<8||editable2.length()<8||editablePhone.length()<11||editableCode.length()<6){
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm=0;
                }
                else{
                    flagAffirm=1;
                    affirm.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {// TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
                Log.i("返回:",event+" | "+result+" | "+data.toString());
                if (result == SMSSDK.RESULT_COMPLETE) {//成功回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交短信、语音验证码成功
//                       Activity之间或者线程间传递数据
                        Bundle bundle = new Bundle();
//                      在验证码正确的前提下做判断，做出判断是注册还是登录
                        bundle.putString("code","登录成功");
                        System.out.println("code：登录成功");
                        Message message = new Message();
                        message.what = 4;
                        message.setData(bundle);
//                        ??????
                        handlerForget.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//不同验证类型
                        Message message = new Message();
                        message.what = 2;
                        handlerForget.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {//获取语音验证码成功
                        Message message = new Message();
                        message.what = 2;
                        handlerForget.sendMessage(message);
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {//失败回调
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交返回
                        Bundle bundle = new Bundle();
                        bundle.putString("code","输入的验证码不正确！");
                        Message message = new Message();
                        message.what = 5;
                        message.setData(bundle);
                        handlerForget.sendMessage(message);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("code",data.toString());
                        Message message = new Message();
                        message.what = 3;
                        message.setData(bundle);
                        handlerForget.sendMessage(message);
                    }

                } else {//其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }
    private void onClick(View view) {
        int id = view.getId(),typeNumber=0,typeAlphabet=0,typeSymbol=0;
        password1Str=password1.getText().toString().trim();
        password2Str=password2.getText().toString().trim();
        phoneStr=phone.getText().toString().trim();
        codeStr=code.getText().toString().trim();
        System.out.println("password1Str:"+password1Str+"--password2Str"+password2Str);
        for (int i=0;i<password1Str.length();i++){
            char c = password1Str.charAt(i);
            if ((c >= '0' && c <= '9'))
                typeNumber=1;
            else if ((c>='a' && c<='z')||(c>='A' && c<='Z'))
                typeAlphabet=1;
            else if (c==','||c=='.'||c=='!'||c=='@'||c=='#'||c=='$'||c=='%'||c=='^'||c=='*')
                typeAlphabet=1;
        }
        switch (id){
            case R.id.forget_getCode:
                if (!TextUtils.isEmpty(phoneStr)&&(phoneStr.length()==11)){//倒计时
//                    检查手机号状态
                    LoginService loginService = new LoginService();
                    loginService.userJudge(handler2_userJudge,phoneStr);
//                    ？？？？？？？？？？？
//                    CountdownStart();
//                    getCode.setBackgroundResource(R.drawable.retangle3);
//                    getVerificationCode("86",phoneStr);
                }else if (phoneStr.length()==0){
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"请检查手机号格式",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.forget_affirm:
//                点击查看密码隐藏密码
                System.out.println("forget_affirm");
                if (flagAffirm==0){
                    break;
                } else if (password1Str.equals(password2Str)||password1Str==password2Str){
                    if (typeNumber+typeAlphabet+typeSymbol<2) {
                        Toast.makeText(ForgetActivity.this,"密码过于简单",Toast.LENGTH_SHORT).show();}
                    else {
                        /**
                         * cn.smssdk.SMSSDK.class
                         * 提交验证码
                         * @param country   国家区号
                         * @param phone     手机号
                         * @param code      验证码
                         */
                        SMSSDK.submitVerificationCode("86",phoneStr,codeStr);//提交验证码
                    }
                }else {
                    Toast.makeText(ForgetActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forget_mima2_image:
//                点击查看密码隐藏密码
                TransformationMethod method = password2.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mimaStatus2.setBackgroundResource(R.mipmap.mimayincang);
                } else {
                    password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mimaStatus2.setBackgroundResource(R.mipmap.chakanmima);
                }
                break;
            case R.id.forget_mima1_image:
//                点击查看密码隐藏密码
                TransformationMethod method2 = password1.getTransformationMethod();
                if (method2 == HideReturnsTransformationMethod.getInstance()) {
                    password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mimaStatus1.setBackgroundResource(R.mipmap.mimayincang);
                } else {
                    password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mimaStatus1.setBackgroundResource(R.mipmap.chakanmima);
                }
                break;
            case R.id.forget_back:
                finish();
                break;
        }
    }
    /**
     * cn.smssdk.SMSSDK.class
     * 请求文本验证码
     * @param country   国家区号
     * @param phone     手机号
     */
    public static void getVerificationCode(String country, String phone){
        //获取短信验证码
        SMSSDK.getVerificationCode(country,phone);
    }

    /**cn.smssdk.SMSSDK.class
     * 请求文本验证码(带模板编号)
     * @param tempCode  模板编号
     * @param country   国家区号
     * @param phone     手机号
     */
    public static void getVerificationCode(String tempCode,String country, String phone){
        //获取短信验证码
        SMSSDK.getVerificationCode(tempCode,country,phone);
    }

    //倒计时函数
    private void CountdownStart(){
        getCode.setEnabled(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                if(count!=0){
                    count--;
                }else {
                    return;
                }
                handlerForget.sendMessage(message);
            }
        }, 1000,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}