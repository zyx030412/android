package com.example.track.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSaveUtil {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public DataSaveUtil(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存data
     * @param key
     * @param value
     */
//    public  void setDataValue(String key,String value) {
//        if (Tools.INSTANCE.isEmpty(value))
//            return;
//        Gson gson = new Gson();
//        //转换成json数据，再保存
////        String strJson = gson.toJson(datalist);
//        editor.clear();
//        editor.putString(key, value);
//        editor.commit();
//
//    }

    /**
     * 获取data
     * @param key
     * @return
     */
    public  String getDataValue(String key) {
        String strJson = preferences.getString(key, null);
        if (null == strJson) {
            return "";
        }
//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<AddressDTO>>() {
//        }.getType());
        return strJson;

    }

    /**
     * 删除指定的信息
     * ***/

    public void removeUserInfo(String key){
        editor.remove(key);
        editor.commit();
    }
}
