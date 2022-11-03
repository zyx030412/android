package com.example.track.model;


import static android.content.ContentValues.TAG;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import androidx.lifecycle.ViewModel;

import com.example.track.db.MySql_application;
import com.example.track.entity.Safety;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TemperatureListViewModel extends ViewModel {
    //变量定义
    private List<Safety> mSafetyList;
    private String str;
    private String search_time;
////    数据库驱动
//    private static final String HOST = "jdbc:mysql://120.25.145.148:3306/test?serverTimezone=GMT%2B8";
//    // 用户名
//    private static final String USER = "root";
//    // 密码
//    private static final String PASSWORD = "root";
//    // Java数据库连接JDBC驱动
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private Connection connection;
//
//    public static Connection getConn(){//数据库驱动连接
//        Connection connection = null;
//        try{
//            Class.forName(DRIVER);
//            connection= DriverManager.getConnection(HOST, USER, PASSWORD);
//
//            Log.e("数据库连接", "成功!");
//        } catch (Exception e) {
//            Log.e("数据库连接", "失败!");
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static  String convertList(ResultSet rs) throws SQLException {//返回List<T>(List<Bean>)数据
//        List list = new ArrayList();//??
//        ResultSetMetaData md = rs.getMetaData();//获取键名
//        int columnCount = md.getColumnCount();//获取行的数量
//        Log.d("数据库连接_数据条数", String.valueOf(columnCount));
//        int coun = 0;
//        while (rs.next()) {//遍历取数据
//            Map rowData = new HashMap();//声明Map用List
//            for (int i = 1; i <= columnCount; i++) {
//                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
//            }
//            list.add(rowData);
//            coun++;
//        }
//        String json = JSON.toJSONString(list);//List<Map<String, Object>> 转化为json格式的String
//               return json;
//    }

//    提取数据
    public List<Safety> getSafetyList(String time) throws InterruptedException {//给外部一个接口
        search_time=time;
        Thread thread2 = new Thread(new JoinRunnable2());
        thread2.start();
        thread2.join();
        Log.d(TAG, "线程名字:" + Thread.currentThread().getName());
        return mSafetyList;
    }
    class JoinRunnable2 implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "线程名字getText():" + Thread.currentThread().getName());
            String result  ;
            ResultSet rs = null;
                Connection connection= MySql_application.getConn();
            try {
                Statement stmt = connection.createStatement();//
                String sql="SELECT id,temperature,insert_time,warning_flag FROM safety_long WHERE insert_time LIKE '%"+search_time+"%'";
                if (search_time==null)
                    sql="SELECT id,temperature,insert_time,warning_flag FROM safety_long";
                Log.d("testid","sql:"+sql);
                rs = stmt.executeQuery(sql);
                result = MySql_application.convertList(rs);//直接转化为List<T>
                System.out.println("result:"+result);
//                将json格式的String转化为List<T>
                mSafetyList = JSONArray.parseArray(result, Safety.class);
                Log.d("test","class:"+Safety.class);
                System.out.println("mSafetyList"+mSafetyList.get(1).getInsert_time());//没有转化成功
                rs.close();
                stmt.close();
                connection.close();
                System.out.println("Database connected successfully!");
            } catch (SQLException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}