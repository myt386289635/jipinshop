package com.example.administrator.jipinshop.util.UmApp;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 莫小婷
 * @create 2019/5/7
 * @Describe 统计代码工具
 */
public class UAppUtil {

    /**
     * 统计底部tab的点击量
     */
    public static void tab(Context context,int position){
        Map<String, String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("Tab","tab栏榜单");
                StatisticalUtil.onEvent(context,"ID1",map);
                break;
            case 1:
                map.put("Tab","tab栏发现");
                StatisticalUtil.onEvent(context,"ID2",map);
                break;
            case 2:
                map.put("Tab","tab栏评测");
                StatisticalUtil.onEvent(context,"ID3",map);
                break;
            case 3:
                map.put("Tab","tab栏试用");
                StatisticalUtil.onEvent(context,"ID4",map);
                break;
            case 4:
                map.put("Tab","tab栏我的");
                StatisticalUtil.onEvent(context,"ID5",map);
                break;
        }
    }

    /**
     * 榜单里的第一行tab：总榜、个护健康榜、厨房电器榜等等
     */
    public static void oneTab(Context context,String name){
        Map<String, String> map = new HashMap<>();
        map.put("oneTab", "榜单——" + name);
        StatisticalUtil.onEvent(context,"ID1",map);
    }

    /**
     * 发现里的第一行tab: 头条、百科、清单等等
     */
    public static void oneTab_find(Context context,String name){
        Map<String, String> map = new HashMap<>();
        map.put("oneTab", "发现——" + name);
        StatisticalUtil.onEvent(context,"ID2",map);
    }

    /**
     * 评测里的第一行tab: 头条、百科、清单等等
     */
    public static void oneTab_evaluation(Context context,String name){
        Map<String, String> map = new HashMap<>();
        map.put("oneTab", "评测——" + name);
        StatisticalUtil.onEvent(context,"ID3",map);
    }

    /**
     * 试用里的tab:  查看全部、查看更多
     */
    public static void oneTab_trier(Context context, int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("oneTab","试用——试用商品——查看全部");
                break;
            case 1:
                map.put("oneTab","试用——试用报告——查看更多");
                break;
        }
        StatisticalUtil.onEvent(context,"ID4",map);
    }

    /**
     * 统计榜单的消息中心
     */
    public static void message(Context context,int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("message","首页消息按钮");
                break;
            case 1:
                map.put("message","首页搜索框");
                break;
            case 2:
                map.put("message","首页搜索框--大家都在搜--第一位置");
                break;
            case 3:
                map.put("message","首页搜索框--大家都在搜--第二位置");
                break;
        }
        StatisticalUtil.onEvent(context,"ID1",map);
    }

    /**
     * 统计榜单二级菜单的点击量
     */
    public static void twoTab(Context context,String oneTabName ,String twoTabName){
        Map<String,String> map = new HashMap<>();
        map.put("twoTab",oneTabName + "——" + twoTabName);
        StatisticalUtil.onEvent(context,"ID1",map);
    }

    /**
     * 统计榜单三级菜单的点击量
     */
    public static void threeTab(Context context,String oneTabName ,String twoTabName , String threeTabName){
        Map<String,String> map = new HashMap<>();
        map.put("threeTab",oneTabName + "——" + twoTabName + "——" + threeTabName);
        StatisticalUtil.onEvent(context,"ID1",map);
    }

    /**
     * 我的频道点击
     */
    public static void mine(Context context, int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("mine","我的--极币数");
                break;
            case 1:
                map.put("mine","我的--关注");
                break;
            case 2:
                map.put("mine","我的--粉丝");
                break;
            case 3:
                map.put("mine","我的--点赞");
                break;
            case 4:
                map.put("mine","我的--邀请领奖");
                break;
            case 5:
                map.put("mine","我的--极币商城");
                break;
            case 6:
                map.put("mine","我的--我的订单");
                break;
            case 7:
                map.put("mine","我的--我的钱包");
                break;
            case 8:
                map.put("mine","我的--消息通知");
                break;
            case 9:
                map.put("mine","我的--我要赚极币");
                break;
            case 10:
                map.put("mine","我的--我的收藏");
                break;
            case 11:
                map.put("mine","我的--收货地址");
                break;
            case 12:
                map.put("mine","我的--设置");
                break;
            case 13:
                map.put("mine","我的--点击复制（邀请码）");
                break;
        }
        StatisticalUtil.onEvent(context,"ID5",map);
    }

    /**
     * 签到页面点击统计
     */
    public static void sign(Context context, int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("sign","我要赚极币--签到");
                break;
            case 1://邀请好友
                map.put("sign","我要赚极币--立即邀请");
                break;
            case 4://点赞
                map.put("sign","我要赚极币--立即点赞");
                break;
            case 5://分享
                map.put("sign","我要赚极币--立即分享");
                break;
            case 6://阅读文章
                map.put("sign","我要赚极币--立即阅读");
                break;
            case 7://评论文章
                map.put("sign","我要赚极币--立即评论");
                break;
        }
        StatisticalUtil.onEvent(context,"ID5",map);
    }

    /**
     * 统计榜单商品详情两个按钮的统计
     */
    public static void goods(Context context,int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("goods","榜单-商品详情-立即购买");
                break;
            case 1:
                map.put("goods","榜单-商品详情-分享给好友");
                break;
        }
        StatisticalUtil.onEvent(context,"ID1",map);
    }

    /**
     * 统计试用商品详情两个按钮的统计
     */
    public static void goods_trier(Context context,int position){
        Map<String,String> map = new HashMap<>();
        switch (position){
            case 0:
                map.put("goods","试用-商品详情-优惠购买");
                break;
            case 1:
                map.put("goods","试用-商品详情-申请试用");
                break;
        }
        StatisticalUtil.onEvent(context,"ID4",map);
    }
}
