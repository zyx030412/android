package com.example.track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.track.entity.Temperature;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DemoActivity extends AppCompatActivity {

    Button b1;
    TextView t1;
    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                System.out.println(msg.obj);
            } else if (msg.what == 2) {
                System.out.println(msg.obj);
            } else if (msg.what == 3) {
                System.out.println(msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        b1 = findViewById(R.id.b1);
        t1 = findViewById(R.id.t1);

        ThreadPoolExecutor pool1 = new ThreadPoolExecutor(3,9,1,
                TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(128));


        ThreadPoolExecutor pool2 = new ScheduledThreadPoolExecutor(3) ;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 30; i++) {
                    Message msg1 = new Message();
                    Message msg2 = new Message();
                    Message msg3 = new Message();
                    Runnable runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            msg1.what = 1;
                            msg1.obj = "1";
                            handler.sendMessage(msg1);
                        }
                    };

                    Runnable runnable2 = new Runnable() {
                        @Override
                        public void run() {
                            msg2.what = 2;
                            msg2.obj = "2";
                            handler.sendMessage(msg2);
                        }
                    };

                    Runnable runnable3 = new Runnable() {
                        @Override
                        public void run() {
                            msg3.what = 3;
                            msg3.obj = "3";
                            handler.sendMessage(msg3);
                        }
                    };
                    pool1.execute(runnable1);
                    pool1.execute(runnable2);
                    pool1.execute(runnable3);
                }
            }
        });

    }

    /**
     * corePoolSize  线程池中核心线程的数量
     *
     * maximumPoolSize  线程池中最大线程数量
     *
     * keepAliveTime 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
     *
     * unit 第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
     *
     * workQueue 线程池中的任务队列，该队列主要用来存储已经被提交但是尚未执行的任务。存储在这里的任务是由ThreadPoolExecutor的execute方法提交来的。
     *
     * threadFactory  为线程池提供创建新线程的功能，这个我们一般使用默认即可
     *
     * handler 拒绝策略，当线程无法执行新任务时（一般是由于线程池中的线程数量已经达到最大数或者线程池关闭导致的），默认情况下，当线程池无法处理新线程时，会抛出一个
     */



    public void btnClick(View view) {

    }
}