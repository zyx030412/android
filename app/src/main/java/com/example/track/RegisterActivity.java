package com.example.track;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
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

public class RegisterActivity extends MyActivity {
    private static final String TAG="RegisterActivity";
    private TextView affirm;
    private ImageView mimaStatus1,mimaStatus2,back;
    private EditText password1,password2;
    private String phone;
//    0代表不可以点击，1可以
    private int flagAffirm=0;
    private final Handler handler_regidter = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(RegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
//                为什么不销毁页面
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                User user = (User) msg.obj;
//                System.out.println("RegisterActivity--user.getUsername():" + user.getUsername());
//                intent.putExtra("user", (Serializable) user);
                if (UtilsFile.saveOpenFile(user.toString(),"user.csv",0,getApplicationContext())){
                    Log.d(TAG, "页面跳转到MainActivity");
                    //关闭Test2Acticity
                    ActivityCollector.finishOneActivity(LoginActivity.class.getName());
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterActivity.this,"用户信息保存失败",Toast.LENGTH_SHORT).show();
                }
            }else {//2时
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        //接收携带过来的数据
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        System.out.println("phone:"+phone);
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
                if (editable.length()<8||editable2.length()<8){
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
                if (editable.length()<8||editable2.length()<8){
                    affirm.setBackgroundResource(R.drawable.retangle3);
                    flagAffirm=0;
                }
                else{
                    flagAffirm=1;
                    affirm.setBackgroundResource(R.drawable.retangle);
                }
            }
        });
    }
    private void init(){
        affirm =findViewById(R.id.register_affirm);
        mimaStatus1=findViewById(R.id.register_mima1_image);
        mimaStatus2=findViewById(R.id.register_mima2_image);
        back = findViewById(R.id.register_back);
        password1 = findViewById(R.id.register_password1);
        password2 = findViewById(R.id.register_password2);
    }

    private void onClick(View view) {
        int id = view.getId(),typeNumber=0,typeAlphabet=0,typeSymbol=0;
        String password1Str=password1.getText().toString().trim();
        String password2Str=password2.getText().toString().trim();
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
            case R.id.register_affirm:
//                点击查看密码隐藏密码
              System.out.println("register_affirm");
              if (flagAffirm==0){
                  break;
              } else if (password1Str.equals(password2Str)||password1Str==password2Str){
                 if (typeNumber+typeAlphabet+typeSymbol<2) {
                      Toast.makeText(RegisterActivity.this,"密码过于简单",Toast.LENGTH_SHORT).show();
                  } else {
                      LoginService loginService = new LoginService();
                      loginService.register(handler_regidter, phone, password1Str);
                  }
              }else {
                  Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
              }
                break;
            case R.id.register_mima2_image:
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
            case R.id.register_mima1_image:
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
            case R.id.register_back:
                finish();
                break;
        }
    }
//    销毁的时候调用函数
//    public void finishAll() { //方式1
//        for (Activity activity : activityList) {
//            if (activity != null) {
//                activity.finish();
//            }
//        }
//    }
}
