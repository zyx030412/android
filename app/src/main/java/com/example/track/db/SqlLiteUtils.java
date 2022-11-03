package com.example.track.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqlLiteUtils extends SQLiteOpenHelper {


    public SqlLiteUtils(Context context){
        super(context,"track.db",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //插入初始sql语句 该方法只会在数据库初次运行的时候调用一次
        db.execSQL("create table safety(int id,temperature varchar(255),insert_time varchar(60),warning_flag int)");
    }

    //对数据库进行操作
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     *查询 返回单个数据
     * @param column    字段名
     * @param databaseFile      数据库名
     * @param where     加where后面的字段名 例如: where where = ? 若不加where 则输入null或者""
     * @param bindArgs      字段值 为上面的 ?
     * @return
     */
    @SuppressLint("Range")
    public String queryData(String column,String databaseFile,String where,String[] bindArgs){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;
        String result = null;
        if (where==null||where.equals("")) {
            String sql = "SELECT " + column + " FROM " + databaseFile;
            cursor = database.rawQuery(sql, null);
            result = null;
        }
        else {
            String sql = "SELECT " + column + " FROM " + databaseFile + " WHERE " + where + " = ?";
            cursor = database.rawQuery(sql, bindArgs);
            result = null;
        }

        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex(column));
        }


        return result;
    }

    /**
     * 查询 返回list类型数据
     * @param column
     * @param databaseFile
     * @param where
     * @param bindArgs
     * @return
     */
    @SuppressLint("Range")
    public List<String> QueryData(String column, String databaseFile, String where, String[] bindArgs){
        SQLiteDatabase database = getWritableDatabase();
        List<String> vec = new ArrayList<String>();
        Cursor cursor = null;
        String result = null;
        if (where==null||where.equals("")) {
            String sql = "SELECT " + column + " FROM " + databaseFile;
            cursor = database.rawQuery(sql, null);
            result = null;
        }else {
            String sql = "SELECT " + column + " FROM " + databaseFile + " WHERE " + where + " = ?";
            cursor = database.rawQuery(sql, bindArgs);
            result = null;
        }


        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex(column));
            vec.add(result);
        }

        return vec;
    }

    /**
     * 模糊查询
     * @param column
     * @param databaseFile
     * @param where
     * @param bindArgs
     * @return
     */
    @SuppressLint("Range")
    public List<String> QueryLikeData(String column,String databaseFile,String where,String[] bindArgs){
        SQLiteDatabase database = getWritableDatabase();
        List<String> vec = new ArrayList<String>();
        Cursor cursor = null;
        String result = null;
        if (where==null||where.equals("")) {
            String sql = "SELECT " + column + " FROM " + databaseFile;
            cursor = database.rawQuery(sql, null);
            result = null;
        }else {
            String sql = "SELECT " + column + " FROM " + databaseFile + " WHERE " + where +" Like "+"'%"+bindArgs[0]+"%'";
            System.out.println(sql);
            cursor = database.rawQuery(sql, null);
            result = null;
        }


        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex(column));
            vec.add(result);
        }

        return vec;
    }


    /**
     * 插入语句
     * @param databaseFile  数据库名
     * @param column    
     * @param bindArgs
     */
    @SuppressLint("Range")
    public void insertData(String databaseFile,String[] column,String[] bindArgs){
        SQLiteDatabase database = getWritableDatabase();
        String tmp1 = "";
        String tmp2 = "";
        for(int i = 0;i<column.length;i++) {
            String temp1 = column[i];
            if (i == column.length - 1) {
                tmp1 += temp1;
            } else {
                tmp1 += temp1 + ",";
            }
        }
        for(int j = 0;j<bindArgs.length;j++){
            String temp2 = bindArgs[j];
            if (j==bindArgs.length-1) {
                tmp2 += "'"+temp2+"'";
            }else {
                tmp2 += "'"+temp2+"'" + ",";
            }
        }


        database.execSQL("INSERT INTO "+databaseFile+" ("+tmp1+") VALUES ("+tmp2+")");
    }

    /**
     * 删除操作
     * @param databaseFile  数据库名
     * @param condition     where后面
     * @param value     =后面
     * @return
     */
    public boolean deleteData(String databaseFile,String condition,String value){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+databaseFile+" WHERE "+condition+" = '"+value+"'");

        return true;
    }


    /**
     * list类型转化为String[]类型
     * @param list
     * @return
     */
    public String[] getList (List<String> list){
        String[] str = new String[list.size()];
        for (int i = 0;i<list.size();i++){
            str[i] = list.get(i);
        }
        return str;
    }

    /**
     * update操作
     * @param databaseFile  表明
     * @param set   set字段
     * @param bindArgs  set = ?
     * @param where     where 后面
     * @param condition    where = ?
     * @return
     */
    public boolean modifyData(String databaseFile,String set,String bindArgs,String where,String condition){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE "+databaseFile+" SET "+set+" = '"+bindArgs+"' WHERE "+where+" = '"+condition+"'";
        database.execSQL(sql);
        return true;
    }


}
