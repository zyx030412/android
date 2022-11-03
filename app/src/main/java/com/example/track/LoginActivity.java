package com.example.track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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
                loginService.login(new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (result==null||result.equals("")) {
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else{
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                },usn,pwd);


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