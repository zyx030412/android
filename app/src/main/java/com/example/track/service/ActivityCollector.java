package com.example.track.service;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *一个专门的集合类对所有的活动（ Activity）进行管理
 *
 * */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    /**
     * 添加Activity
     * @param activity 添加的Activity对象
     * */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 删除Activity
     * @param activity 删除的Activity对象
     * */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭指定的Activity
     * @param activityName 需要关闭的Activity包名类名
     * */
    public static void finishOneActivity(String activityName){
        //在activities集合中找到类名与指定类名相同的Activity就关闭
        for (Activity activity : activities){
            String name= activity.getClass().getName();//activity的包名+类名
            if(name.equals(activityName)){
                if(activity.isFinishing()){
                    //当前activity如果已经Finish，则只从activities清除就好了
                    activities.remove(activity);
                } else {
                    //没有Finish则Finish
                    activity.finish();
                }
            }
        }
    }
    /**
     * 关闭所有Activity
     * */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    /**
     * 只保留某个Activity，关闭其他所有Activity
     * @param activityName 要保留的Activity类名
     * */
    public static void finishOtherActivity(String activityName){
        for (Activity activity : activities){
            String name= activity.getClass().getName();//activity的类名
            if(!name.equals(activityName)){
                if(activity.isFinishing()){
                    //当前activity如果已经Finish，则只从activities清除就好了
                    activities.remove(activity);
                } else {
                    //没有Finish则Finish
                    activity.finish();
                }
            }
        }
    }
    /**
     * 返回栈顶的activity的名字
     */
    public static String getFirstActivity(){
        return activities.get(activities.size()-1).getClass().getName();
    }

}