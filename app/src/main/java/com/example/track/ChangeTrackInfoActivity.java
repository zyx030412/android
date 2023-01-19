package com.example.track;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.track.entity.CarInfo;
import com.example.track.service.StoreService;

public class ChangeTrackInfoActivity extends AppCompatActivity{
    private RelativeLayout carInfoSetArea;
    private ImageButton backButton;
    private TextView carNumber1,power1,power2,power3,power4,carType1,carType2,carType3,zhou1,zhou2,zhou3,zhou4,zhou5,submit;
    private EditText carNumber2,carNumber3,carNumber4,carNumber5,carNumber6,carNumber7,eSeek1,eSeek2,eSeek3,eSeek4,eSeek5;
    private SeekBar seek1,seek2,seek3,seek4,seek5;
    private TextView q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15,q16,q17,q18,q19,q20,q21,q22,q23,q24,q25,q26,q27,q28,q29,q30,q31;
    private String total_weight,approved_load,length,width,height;
    private int plateFlag = 0,carTypeFlag,powerTypeFlag,zhouFlag;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_car_info);
        init();

        submit = findViewById(R.id.change_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1;
                if (carNumber1.getText().toString().equals(""))
                    flag = 0;
                if (carNumber2.getText().toString().equals("") || carNumber3.getText().toString().equals("") || carNumber4.getText().toString().equals("") || carNumber5.getText().toString().equals("") || carNumber6.getText().toString().equals(""))
                    flag = 0;
                String s1 = carNumber2.getText().toString();
                if (carTypeFlag == 0)
                    flag = 0;
                if (powerTypeFlag == 0)
                    flag = 0;
                if (zhouFlag == 0)
                    flag = 0;

                total_weight = eSeek1.getText().toString();
                approved_load = eSeek2.getText().toString();
                length = eSeek3.getText().toString();
                width = eSeek4.getText().toString();
                height = eSeek5.getText().toString();

                if (flag == 1){
                    String license_plate = "";
                    if (carNumber7.getText().toString().equals("")){
                         license_plate = carNumber1.getText().toString() + carNumber2.getText().toString() + carNumber3.getText().toString() + carNumber4.getText().toString() + carNumber5.getText().toString() + carNumber6.getText().toString();
                    }else{
                        license_plate = carNumber1.getText().toString() + carNumber2.getText().toString() + carNumber3.getText().toString() + carNumber4.getText().toString() + carNumber5.getText().toString() + carNumber6.getText().toString() + carNumber7.getText().toString();
                    }
                    CarInfo carInfo = new CarInfo(license_plate,carTypeFlag,powerTypeFlag,Integer.parseInt(total_weight),Integer.parseInt(approved_load),Double.parseDouble(length),Double.parseDouble(width),Double.parseDouble(height),zhouFlag,MainActivity.getUser());
                    new StoreService().storeCarInfo(carInfo);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"错误，您填写的信息不完全!",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init(){

        backButton = findViewById(R.id.change_back);
        carInfoSetArea = findViewById(R.id.car_info_select_area_change);
        carNumber1 = findViewById(R.id.change_car_number1);
        carNumber2 = findViewById(R.id.change_car_number2);
        carNumber3 = findViewById(R.id.change_car_number3);
        carNumber4 = findViewById(R.id.change_car_number4);
        carNumber5 = findViewById(R.id.change_car_number5);
        carNumber6 = findViewById(R.id.change_car_number6);
        carNumber7 = findViewById(R.id.change_car_number7);
        carType1 = findViewById(R.id.change_car_type1);
        carType2 = findViewById(R.id.change_car_type2);
        carType3 = findViewById(R.id.change_car_type3);
        power1 = findViewById(R.id.change_power_type1);
        power2 = findViewById(R.id.change_power_type2);
        power3 = findViewById(R.id.change_power_type3);
        power4 = findViewById(R.id.change_power_type4);
        seek1 = findViewById(R.id.change_seek1);
        seek2 = findViewById(R.id.change_seek2);
        seek3 = findViewById(R.id.change_seek3);
        seek4 = findViewById(R.id.change_seek4);
        seek5 = findViewById(R.id.change_seek5);
        eSeek1 = findViewById(R.id.change_seek_1);
        eSeek2 = findViewById(R.id.change_seek_2);
        eSeek3 = findViewById(R.id.change_seek_3);
        eSeek4 = findViewById(R.id.change_seek_4);
        eSeek5 = findViewById(R.id.change_seek_5);
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        q6 = findViewById(R.id.q6);
        q7 = findViewById(R.id.q7);
        q8 = findViewById(R.id.q8);
        q9 = findViewById(R.id.q9);
        q10 = findViewById(R.id.q10);
        q11 = findViewById(R.id.q11);
        q12 = findViewById(R.id.q12);
        q13 = findViewById(R.id.q13);
        q14 = findViewById(R.id.q14);
        q15 = findViewById(R.id.q15);
        q16 = findViewById(R.id.q16);
        q17 = findViewById(R.id.q17);
        q18 = findViewById(R.id.q18);
        q19 = findViewById(R.id.q19);
        q20 = findViewById(R.id.q20);
        q21 = findViewById(R.id.q21);
        q22 = findViewById(R.id.q22);
        q23 = findViewById(R.id.q23);
        q24 = findViewById(R.id.q24);
        q25 = findViewById(R.id.q25);
        q26 = findViewById(R.id.q26);
        q27 = findViewById(R.id.q27);
        q28 = findViewById(R.id.q28);
        q29 = findViewById(R.id.q29);
        q30 = findViewById(R.id.q30);
        q31 = findViewById(R.id.q31);
        zhou1 = findViewById(R.id.change_zhou1);
        zhou2 = findViewById(R.id.change_zhou2);
        zhou3 = findViewById(R.id.change_zhou3);
        zhou4 = findViewById(R.id.change_zhou4);
        zhou5 = findViewById(R.id.change_zhou5);

        zhou1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhou1.setBackgroundResource(R.drawable.number_choose_button_blue);
                zhou2.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou3.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou4.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou5.setBackgroundResource(R.drawable.number_choose_button_white);
                zhouFlag = 1;
            }
        });

        zhou2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhou2.setBackgroundResource(R.drawable.number_choose_button_blue);
                zhou1.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou3.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou4.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou5.setBackgroundResource(R.drawable.number_choose_button_white);
                zhouFlag = 2;
            }
        });

        zhou3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhou3.setBackgroundResource(R.drawable.number_choose_button_blue);
                zhou2.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou1.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou4.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou5.setBackgroundResource(R.drawable.number_choose_button_white);
                zhouFlag = 3;
            }
        });

        zhou4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhou4.setBackgroundResource(R.drawable.number_choose_button_blue);
                zhou2.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou3.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou1.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou5.setBackgroundResource(R.drawable.number_choose_button_white);
                zhouFlag = 4;
            }
        });

        zhou5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhou5.setBackgroundResource(R.drawable.number_choose_button_blue);
                zhou2.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou3.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou4.setBackgroundResource(R.drawable.number_choose_button_white);
                zhou1.setBackgroundResource(R.drawable.number_choose_button_white);
                zhouFlag = 5;
            }
        });

        carType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carType1.setBackgroundResource(R.drawable.number_choose_button_blue);
                carType2.setBackgroundResource(R.drawable.number_choose_button_white);
                carType3.setBackgroundResource(R.drawable.number_choose_button_white);
                carTypeFlag = 1;
            }
        });

        carType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carType2.setBackgroundResource(R.drawable.number_choose_button_blue);
                carType1.setBackgroundResource(R.drawable.number_choose_button_white);
                carType3.setBackgroundResource(R.drawable.number_choose_button_white);
                carTypeFlag = 2;
            }
        });

        carType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carType3.setBackgroundResource(R.drawable.number_choose_button_blue);
                carType2.setBackgroundResource(R.drawable.number_choose_button_white);
                carType1.setBackgroundResource(R.drawable.number_choose_button_white);
                carTypeFlag = 3;
            }
        });

        power1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power1.setBackgroundResource(R.drawable.number_choose_button_blue);
                power2.setBackgroundResource(R.drawable.number_choose_button_white);
                power3.setBackgroundResource(R.drawable.number_choose_button_white);
                powerTypeFlag = 1;
            }
        });

        power1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power1.setBackgroundResource(R.drawable.number_choose_button_blue);
                power2.setBackgroundResource(R.drawable.number_choose_button_white);
                power3.setBackgroundResource(R.drawable.number_choose_button_white);
                power4.setBackgroundResource(R.drawable.number_choose_button_white);
                powerTypeFlag = 1;
            }
        });

        power2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power2.setBackgroundResource(R.drawable.number_choose_button_blue);
                power1.setBackgroundResource(R.drawable.number_choose_button_white);
                power3.setBackgroundResource(R.drawable.number_choose_button_white);
                power4.setBackgroundResource(R.drawable.number_choose_button_white);
                powerTypeFlag = 2;
            }
        });

        power3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power3.setBackgroundResource(R.drawable.number_choose_button_blue);
                power2.setBackgroundResource(R.drawable.number_choose_button_white);
                power1.setBackgroundResource(R.drawable.number_choose_button_white);
                power4.setBackgroundResource(R.drawable.number_choose_button_white);
                powerTypeFlag = 3;
            }
        });

        power4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power1.setBackgroundResource(R.drawable.number_choose_button_white);
                power2.setBackgroundResource(R.drawable.number_choose_button_white);
                power3.setBackgroundResource(R.drawable.number_choose_button_white);
                power4.setBackgroundResource(R.drawable.number_choose_button_blue);
                powerTypeFlag = 4;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        carNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plateFlag == 1){
                    plateFlag = 0;
                    carInfoSetArea.setVisibility(View.GONE);
                }else{
                    plateFlag = 1;
                    carInfoSetArea.setVisibility(View.VISIBLE);
                }
            }
        });

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q1.getText().toString());
            }
        });

        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q2.getText().toString());
            }
        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q3.getText().toString());
            }
        });

        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q4.getText().toString());
            }
        });

        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q5.getText().toString());
            }
        });

        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q6.getText().toString());
            }
        });

        q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q7.getText().toString());
            }
        });

        q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q8.getText().toString());
            }
        });

        q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q9.getText().toString());
            }
        });

        q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q10.getText().toString());
            }
        });

        q11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q11.getText().toString());
            }
        });

        q12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q12.getText().toString());
            }
        });

        q13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q13.getText().toString());
            }
        });

        q14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q14.getText().toString());
            }
        });

        q15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q15.getText().toString());
            }
        });

        q16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q16.getText().toString());
            }
        });

        q17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q17.getText().toString());
            }
        });

        q18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q18.getText().toString());
            }
        });

        q19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q19.getText().toString());
            }
        });

        q20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q20.getText().toString());
            }
        });

        q21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q21.getText().toString());
            }
        });

        q22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q22.getText().toString());
            }
        });

        q23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q23.getText().toString());
            }
        });

        q24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q24.getText().toString());
            }
        });

        q25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q25.getText().toString());
            }
        });

        q26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q26.getText().toString());
            }
        });

        q27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q27.getText().toString());
            }
        });

        q29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q29.getText().toString());
            }
        });

        q30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q30.getText().toString());
            }
        });

        q28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q28.getText().toString());
            }
        });

        q31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateFlag = 0;
                carInfoSetArea.setVisibility(View.GONE);
                carNumber1.setText(q31.getText().toString());
            }
        });

        seek1.setMin(0);
        seek1.setMax(60);
        seek2.setMin(0);
        seek2.setMax(60);
        seek3.setMin(20);
        seek3.setMax(180);
        seek4.setMin(15);
        seek4.setMax(30);
        seek5.setMin(15);
        seek5.setMax(50);


        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eSeek1.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eSeek2.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //2-18 - 20-180
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int a = progress/10;
                int b = progress%10;
                String str = a + "." + b + "0";
                eSeek3.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int a = progress/10;
                int b = progress%10;
                String str = a + "." + b + "0";
                eSeek4.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int a = progress/10;
                int b = progress%10;
                String str = a + "." + b + "0";
                eSeek5.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        carInfoSetArea.setVisibility(View.GONE);
    }
}
