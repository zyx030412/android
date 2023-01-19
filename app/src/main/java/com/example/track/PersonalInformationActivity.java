package com.example.track;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.track.entity.User;
import com.example.track.model.datepicker.CustomDatePicker;
import com.example.track.model.datepicker.DateFormatUtils;
import com.example.track.service.LoginService;
import com.example.track.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

//参考
//1.https://blog.csdn.net/JerryWu145/article/details/48214927?locationNum=2&fps=1&ops_request_misc=&request_id=&biz_id=102&utm_term=android%20%E4%BB%8E%E7%9B%B8%E5%86%8C%E9%80%89%E6%8B%A9%E5%9B%BE%E7%89%87%E5%A4%84%E7%90%86%E5%81%9A%E5%A4%B4%E5%83%8F&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-1-48214927.142^v70^control,201^v4^add_ask&spm=1018.2226.3001.4187
//https://www.cnblogs.com/022414ls/p/12945082.html
//https://www.php1.cn/detail/Android_ShiXianD_b262f6d4.html
//https://blog.csdn.net/duoduo_11011/article/details/105982796

//Android中选择器PickerView(第三方控件)的使用2----省市区选择
//https://it.cha138.com/shida/show-459008.html

//Android启动系统自带裁剪功能报错提示——无法加载此图片（已解决）
//https://blog.csdn.net/weixin_47971206/article/details/119972043
public class PersonalInformationActivity extends MyActivity implements Serializable {
    private String TAG = "PersonalInformationActivity";
    private CustomDatePicker mDatePicker;
    private ImageView Ibirthday, back;
    private LinearLayout Rname, Rphone, Rhead, Rsex, Rbirthday;
    private TextView takePhoto, takePic, takeCancel, affirm;
    private EditText name;
    private TextView phone, birthday;
    private String search_time;//时间选择器选择的时间
    private String imageString;//保存到数据库中的数据
    private String sexStr;
    private View view;
    private RadioButton radioMale, radioFemale;
    private RadioGroup mRadioGroup;
    private Uri imageUri, cropImgUri;
    private User user = MainActivity.user;
    private static final int PHOTO_REQUEST = 1;//相册
    private static final int CAMERA_REQUEST = 2;//相机
    private static final int PHOTO_CLIP = 3;
    private de.hdodenhof.circleimageview.CircleImageView head;
    private ShowBottomDialog mShowBottomDialog = new ShowBottomDialog();
    //    线程问题
    private Handler head_modify = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(PersonalInformationActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(PersonalInformationActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(PersonalInformationActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Handler user_info_modify = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //在这里即可以做一些UI操作了。。。
            if (msg.what == 0) {
                Toast.makeText(PersonalInformationActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(PersonalInformationActivity.this, "用户名已存在保存失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Intent intent = new Intent(PersonalInformationActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
//                Toast.makeText(PersonalInformationActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        init();
        initDatePicker();
        //屏蔽7.0中使用 Uri.fromFile爆出的FileUriExposureException
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= 24) {
            builder.detectFileUriExposure();
        }

    }

    private void init() {
        mRadioGroup = findViewById(R.id.personal_information_radioGroup);
        back = findViewById(R.id.personal_information_back);
        radioMale = findViewById(R.id.personal_information_radio_male);
        radioFemale = findViewById(R.id.personal_information_radio_female);
        Rname = findViewById(R.id.personal_information_name_layout);
        Rphone = findViewById(R.id.personal_information_phone_layout);
        Rhead = findViewById(R.id.personal_information_head_layout);
        Rsex = findViewById(R.id.personal_information_sex_layout);
        Rbirthday = findViewById(R.id.personal_information_birthday_layout);
        head = findViewById(R.id.personal_information_head);
        name = findViewById(R.id.personal_information_name);
        phone = findViewById(R.id.personal_information_phone);
        birthday = findViewById(R.id.personal_information_birthday);
        affirm = findViewById(R.id.personal_information_affirm);
        Ibirthday = findViewById(R.id.personal_information_birthday_image);
//        mRadioGroup
        Rphone.setOnClickListener(this::onClick);
        back.setOnClickListener(this::onClick);
        Rname.setOnClickListener(this::onClick);
        Rphone.setOnClickListener(this::onClick);
        Rhead.setOnClickListener(this::onClick);
        Rsex.setOnClickListener(this::onClick);
        Rbirthday.setOnClickListener(this::onClick);
        name.setOnClickListener(this::onClick);
        phone.setOnClickListener(this::onClick);
        birthday.setOnClickListener(this::onClick);
        affirm.setOnClickListener(this::onClick);
        Ibirthday.setOnClickListener(this::onClick);
        name.setText(user.getUsername());
        birthday.setText(user.getBirthday());
        String str = user.getPhone();
        StringBuffer buffer = new StringBuffer(str);
        buffer = buffer.replace(3, 7, "****");
        str = String.valueOf(buffer);
        phone.setText(str);
        if (user.getSex().equals("女"))
            radioFemale.setChecked(true);
        else//默认为男性
            radioMale.setChecked(true);
        Log.d(TAG,"user.getHead()"+user.getHead());
        if (user.getHead().equals("") || user.getHead() == null ||user.getHead().equals("null") ) {
            if (user.getSex().equals("男"))
                head.setImageResource(R.mipmap.yonghu_male);
            else
                head.setImageResource(R.mipmap.yonghu_female);
        } else {
            Bitmap bitmap = BitmapUtils.base642Bitmap(user.getHead());
            head.setImageBitmap(bitmap);
        }

    }

    private void onClick(View view) {
        int id = view.getId();
        System.out.println("view.getId():" + id);
        switch (id) {
            case R.id.personal_information_phone_layout:
                Intent intent = new Intent(PersonalInformationActivity.this, ModifyPhoneActivity.class);
//                没有结束当前页面
                startActivity(intent);
                break;
            case R.id.personal_information_back:
                finish();
                break;
            case R.id.personal_information_birthday_layout:
                // 日期格式为yyyy-MM-dd
                mDatePicker.show(birthday.getText().toString());
                break;
            case R.id.personal_information_birthday_image:
                mDatePicker.show(birthday.getText().toString());
                break;
            case R.id.personal_information_birthday:
                Log.d(TAG, "ll");
                mDatePicker.show(birthday.getText().toString());
                break;
            case R.id.personal_information_head_layout:
//                mShowBottomDialog.BottomDialog(PersonalInformationActivity.this);
                showBottomDialog();
                break;
            case R.id.personal_information_affirm:
                user.setUsername(name.getText().toString());
                Log.d(TAG, birthday.getText().toString());
                user.setBirthday(birthday.getText().toString());
                if (radioMale.isChecked() == true)
                    sexStr = "男";
                else
                    sexStr = "女";
                Log.d(TAG, "sexStr:" + sexStr);
                user.setSex(sexStr);
                LoginService loginService = new LoginService();
                loginService.userInfoModify(user_info_modify);
                //        保存到本地
                UtilsFile.saveOpenFile(user.toString(), MainActivity.fileUser, MainActivity.TypeUser, getApplicationContext());
                break;
        }
    }

    /**
     * 调用时间选择器
     */
    private void initDatePicker() {//????
        long beginTimestamp = DateFormatUtils.str2Long("2000-01-01", false);//设置可以选择的最早时间
        long endTimestamp = System.currentTimeMillis();//当前时间
//      初始化时间
//        ??????
//        birthday.setText(DateFormatUtils.long2Str(endTimestamp, false));
        search_time = DateFormatUtils.long2Str(endTimestamp, false);
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
//               点击的时间
                birthday.setText(DateFormatUtils.long2Str(timestamp, false));
                search_time = DateFormatUtils.long2Str(timestamp, false);
            }
        }, beginTimestamp, endTimestamp);
        // false不允许点击屏幕或物理返回键关闭;true允许
        mDatePicker.setCancelable(true);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    /**
     *
     */
    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        view = View.inflate(this, R.layout.dialog_picture, null);
        takePhoto = view.findViewById(R.id.tv_take_photo);
        takePic = view.findViewById(R.id.tv_take_pic);
        takeCancel = view.findViewById(R.id.tv_cancel);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "打开箱机");
                /* getExternalCacheDir访问的应用的私有目录(/sdcard/Android/data/<package name>/cache) 因此不需要动态权限申请. */
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                        outputImage.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                //兼容7.0的方式获取uri
                if (Build.VERSION.SDK_INT >= 24) {
                    Log.d(TAG, "Build.VERSION.SDK_INT >= 24");
                    imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.track.fileProvider", outputImage);
//                    cropImgUri = Uri.fromFile(outputImage);

                } else {//低版本的
                    Log.d(TAG, "Build.VERSION.SDK_INT <24");
                    imageUri = Uri.fromFile(outputImage);
                }
                Log.d(TAG, "imageUri:" + imageUri);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "打开相册");
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, PHOTO_REQUEST);
            }
        });

        takeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    /**
     * Bitmap是Android系统中的图像处理的最重要类之一。
     * 用它可以获取图像文件信息，进行图像剪切、旋转、缩放等操作，并可以指定格式保存图像文件。
     * onActivityResult页面跳转当结果返回后判断并执行操作
     *
     * @param requestCode 用于区分返回的页面
     * @param resultCode  用于区分返回的页面
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "resultCode:" + resultCode + "--" + "data:" + data);
//        resultCode:-1--data:Intent { act=inline-data (has extras) }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
//                    startPhotoCrop();
                    Log.d(TAG, "imageUri:" + imageUri);
//                    Bitmap bitmap= BitmapFactory.decodeFile("file:///storage/emulated/0/Android/data/com.example.track/cache/output_image.jpg");
                    photoClip(imageUri);
                    break;
                case PHOTO_REQUEST:
//                    content://media/external/images/media/98157
                    Log.d(TAG, "data.getData():" + data.getData());
                    Log.d(TAG, "相册Url:" + data.getData());
                    photoClip(data.getData());
                    break;
                case PHOTO_CLIP:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        head.setImageBitmap(photo);
                        saveImage(photo);
                    }
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(PersonalInformationActivity.this, "调取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(Bitmap bitmap) {
        String imageBase64 = BitmapUtils.bitmapBase64(bitmap);
        user.setHead(imageBase64);
//        保存到数据库
        LoginService loginService = new LoginService();
        loginService.headModify(head_modify, imageBase64);
//        保存到本地
        UtilsFile.saveOpenFile(user.toString(), MainActivity.fileUser, MainActivity.TypeUser, getApplicationContext());

    }

    //    --------------手机系统的照片剪辑处理功能()
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //设置读权限()
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }
}
