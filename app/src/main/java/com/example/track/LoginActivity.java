package com.example.track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.track.entity.User;
import com.example.track.interfac.VolleyCallback;
import com.example.track.service.LoginService;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageButton help,qqLogin,wechatLogin,alipayLogin;
    private EditText username,password;
    private Button loginButton,forgetLogin,register,messageLogin;
    private Handler handler1 = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                Toast.makeText(LoginActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
            }else if (msg.what == 2){
                Toast.makeText(LoginActivity.this,"用户名或密码有误",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                User user = (User) msg.obj;
                intent.putExtra("user", (Serializable) user);
                finish();
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //绑定实例
        help = findViewById(R.id.login_help);
        qqLogin = findViewById(R.id.login_qq_login);
        wechatLogin = findViewById(R.id.login_wechat_login);
        alipayLogin = findViewById(R.id.login_alipay_login);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_login);
        forgetLogin = findViewById(R.id.login_forget);
        register = findViewById(R.id.login_register);
        messageLogin = findViewById(R.id.login_message_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = String.valueOf(username.getText());
                String pwd = String.valueOf(password.getText());
                LoginService loginService = new LoginService();
                loginService.login(handler1,usn,pwd);


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register_intent);
            }
        });


    }




}