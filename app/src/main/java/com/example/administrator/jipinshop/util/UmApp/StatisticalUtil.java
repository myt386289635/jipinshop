package com.example.administrator.jipinshop.util.UmApp;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * @author 莫小婷
 * @create 2019/5/7
 * @Describe 友盟统计工具
 *
 *  分为  计数事件  和  计算事件
 *
 */
public class StatisticalUtil {

    /**
     * 计数事件统计
     * @param context
     * @param eventID 为当前统计的事件ID。
     */
    public static void onEvent(Context context, String eventID){
        MobclickAgent.onEvent(context,eventID);
    }

    /**
     *  计数事件统计  只需要统计一个属性时
     * @param context
     * @param eventID 为当前统计的事件ID。
     * @param label  事件的标签属性。
     */
    public static void onEvent(Context context, String eventID, String label){
        MobclickAgent.onEvent(context,eventID,label);
    }

    /**
     * 计数事件统计
     * 统计点击行为各属性被触发的次数  考虑事件在不同属性上的取值 需要统计多个属性时
     * @param context
     * @param eventID 为当前统计的事件ID。
     * @param map 为当前事件的属性和取值（Key-Value键值对）。
     */
    public static void onEvent(Context context, String eventID, Map<String, String> map){
        MobclickAgent.onEvent(context,eventID,map);
    }

    /**
     * 计算事件统计
     * @param context
     * @param eventID 为当前统计的事件ID
     * @param map 为当前事件的属性和取值（Key-Value键值对）。
     * @param du 当前事件的数值，取值范围是-2,147,483,648 到 +2,147,483,647 之间的有符号整数，即int 32类型
     */
    public static void onEventValue(Context context, String eventID, Map<String, String> map, int du){
        MobclickAgent.onEventValue(context, eventID , map, du);
    }

}
