package com.example.track;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.track.service.RegisterService;

public class RegisterActivity extends AppCompatActivity {
    private Button cancel,getCode,radioButton,userAgreement,register;
    private EditText phone,code;
    private static int isSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancel = findViewById(R.id.register_cancel);
        getCode = findViewById(R.id.register_getCode);
        radioButton = findViewById(R.id.register_radio);
        userAgreement = findViewById(R.id.register_user);
        register = findViewById(R.id.register_register);

        phone = findViewById(R.id.register_phone);
        code = findViewById(R.id.register_code);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected == 0) {
                    radioButton.setBackground(getResources().getDrawable(R.drawable.radio_register_selected));
                    isSelected = 1;
                }else{
                    radioButton.setBackground(getResources().getDrawable(R.drawable.radio_register));
                    isSelected = 0;
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone = String.valueOf(phone.getText());
                String Code = String.valueOf(code.getText());
                if (isSelected == 1){
                    RegisterService registerService = new RegisterService();
//                    registerService.register();
                }else{
                    Toast.makeText(RegisterActivity.this,"请先同意用户条款",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSelected = 0;
    }
}
