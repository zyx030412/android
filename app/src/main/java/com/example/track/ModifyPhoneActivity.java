package com.example.track;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class ModifyPhoneActivity extends MyActivity {
    private static final String TAG = "ModifyPhoneActivity";
    private TextView affirm, getCode;
    private ImageView back;
    private EditText phone, code;
    private String phoneStr, codeStr;
    private User user=MainActivity.user;
    Timer timer;
    int count = 60;
    EventHandler eventHandler;
    //    判断记录验证是否正确0代表正确
    private int codeFlag = -1;
    //    0代表不可以点击，1可以
    private int flagAffirm = 0;
    @SuppressLint("HandlerLeak")
    private final Handler handlermodify_phone = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
//            this.toString():Handler (com.example.track.modify_phoneActivity$1) {2cea1d9}
//            ActivityCollector.getFirstActivity():com.example.track.modify_phoneActivity@f8cb6f6
            if (ActivityCollector.getFirstActivity().equals("com.example.track.ModifyPhoneActivity")) {
                int tag = msg.what;
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
                        Toast.makeText(ModifyPhoneActivity.this, "获取短信验证码成功", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Log.i("Codr", "获取短信验证码失败");
                        Toast.makeText(ModifyPhoneActivity.this, msg.getData().getString("code"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(ModifyPhoneActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                        break;
                    case 4:
//                    当成功时=4
//                    这里可以试试看
//                    Toast.makeText(ModifyPhoneActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
//                    codeFlag=0;
//                        user.setPhone(phoneStr);
                        LoginService loginService = new LoginService();
                        loginService.phoneModify(phone_modify,phoneStr);
                        System.out.println("验证码正确modify_phone！！！！！");
                        break;
                    case 5:
                        Log.i("Codr", "输入的验证码不正确！");
                        Toast.makeText(ModifyPhoneActivity.this, msg.getData().getString("code"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(ModifyPhoneActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        }

        ;
    };
    //    修改密码
    private final Handler phone_modify = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(ModifyPhoneActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(ModifyPhoneActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                if (UtilsFile.saveOpenFile(user.toString(), "user.csv", 0, getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(ModifyPhoneActivity.this, "用户信息保存失败", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    //    线程问题检查当前手机号是否已经注册个
    private final Handler handler2_userJudge = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(ModifyPhoneActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {//当前用户不存在时
                CountdownStart();
                getCode.setBackgroundResource(R.drawable.retangle3);
                getVerificationCode("86", phoneStr);
            } else if (msg.what == 1)//用户存在
                Toast.makeText(ModifyPhoneActivity.this, "此手机号已经注册过", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        MobSDK.submitPolicyGrantResult(true, null);
        init();
    }

    public void init() {
        affirm = findViewById(R.id.modify_phone_affirm);
        getCode = findViewById(R.id.modify_phone_getCode);
        back = findViewById(R.id.modify_phone_back);
        phone = findViewById(R.id.modify_phone_phone);
        code = findViewById(R.id.modify_phone_code);
        getCode.setOnClickListener(this::onClick);
        affirm.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);
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
                if (editablePhone.length() < 11 || editableCode.length() < 6) {
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
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
                String editablePhone = phone.getText().toString();
                String editableCode = code.getText().toString();
                if (editablePhone.length() < 11 || editableCode.length() < 6) {
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm = 0;
                } else {
                    flagAffirm = 1;
                    affirm.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
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
//                        ??????
                        handlermodify_phone.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//不同验证类型
                        Message message = new Message();
                        message.what = 2;
                        handlermodify_phone.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {//获取语音验证码成功
                        Message message = new Message();
                        message.what = 2;
                        handlermodify_phone.sendMessage(message);
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {//失败回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交返回
                        Bundle bundle = new Bundle();
                        bundle.putString("code", "输入的验证码不正确！");
                        Message message = new Message();
                        message.what = 5;
                        message.setData(bundle);
                        handlermodify_phone.sendMessage(message);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("code", data.toString());
                        Message message = new Message();
                        message.what = 3;
                        message.setData(bundle);
                        handlermodify_phone.sendMessage(message);
                    }

                } else {//其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    private void onClick(View view) {
        int id = view.getId();
        phoneStr = phone.getText().toString().trim();
        codeStr = code.getText().toString().trim();
        switch (id) {
            case R.id.modify_phone_getCode:
                if (!TextUtils.isEmpty(phoneStr) && (phoneStr.length() == 11)) {//倒计时
//                    检查手机号状态
                    LoginService loginService = new LoginService();
                    loginService.userJudge(handler2_userJudge, phoneStr);
//                    ？？？？？？？？？？？
//                    CountdownStart();
//                    getCode.setBackgroundResource(R.drawable.retangle3);
//                    getVerificationCode("86",phoneStr);
                } else if (phoneStr.length() == 0) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "请检查手机号格式", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.modify_phone_affirm:
//                点击查看密码隐藏密码
                System.out.println("modify_phone_affirm");
                if (flagAffirm == 0) {
                    break;
                } else {
                    /**
                     * cn.smssdk.SMSSDK.class
                     * 提交验证码
                     * @param country   国家区号
                     * @param phone     手机号
                     * @param code      验证码
                     */
                    SMSSDK.submitVerificationCode("86", phoneStr, codeStr);//提交验证码
                }
                break;
            case R.id.modify_phone_back:
                finish();
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
                handlermodify_phone.sendMessage(message);
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}