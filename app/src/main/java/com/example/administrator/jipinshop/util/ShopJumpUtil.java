package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.bean.JPushBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 莫小婷
 * @create 2018/8/10
 * @Describe
 */
public class ShopJumpUtil {


    /**
     * 直接跳转至应用宝
     *
     * @param context {@link Context}
     * @param appPkg  包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toQQDownload(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.tencent.android.qqdownloader");
    }

    /**
     * 直接跳转至360手机助手
     *
     * @param context {@link Context}
     * @param appPkg  包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean to360Download(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.qihoo.appstore");
    }

    /**
     * 直接跳转至豌豆荚
     *
     * @param context {@link Context}
     * @param appPkg  包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toWandoujia(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.wandoujia.phoenix2");
    }

    /**
     * 直接跳转至小米应用商店
     *
     * @param context {@link Context}
     * @param appPkg  包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toXiaoMi(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.xiaomi.market");
    }

    /**
     * 直接跳转至魅族应用商店
     *
     * @param context {@link Context}
     * @param appPkg  包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toMeizu(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.meizu.mstore");
    }


    /**
     * 跳转各个应用商店.
     *
     * @param context   {@link Context}
     * @param appPkg    包名
     * @param marketPkg 应用商店包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toMarket(Context context, String appPkg, String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
            intent.setPackage(marketPkg);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            Log.e("moxiaoting", "没有跳转成功");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 跳转文章逻辑
     */
    public static void jumpArticle(Context context , String id , String type ,int contentType){
        Intent intent = new Intent();
        switch (contentType){
            case 1://webview
                intent.setClass(context, ArticleDetailActivity.class);
                break;
            case 3://json
                intent.setClass(context, ReportDetailActivity.class);
                break;
        }
        intent.putExtra("id",id);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    /**
     * 公用跳转逻辑
     */
    public static void openPager(Context context, String targetType, String target_id , String target_title ){
        Intent intent = new Intent();
        switch (targetType){
            case "1"://榜单首页
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                break;
            case "11"://跳转到小分类榜单
                intent.setClass(context, ClassifyActivity.class);
                intent.putExtra("title",target_title + "榜单");
                intent.putExtra("id",target_id);
                context.startActivity(intent);
                break;
            case "12"://跳转到商品详情
                intent.setClass(context,  ShoppingDetailActivity.class);
                intent.putExtra("goodsId",target_id);
                context.startActivity(intent);
                break;
            case "2"://评测主页
                EventBus.getDefault().post(new ChangeHomePageBus(1));
                break;
            case "21"://测评文章
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","2");
                context.startActivity(intent);
                break;
            case "23"://清单详情web
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","7");
                context.startActivity(intent);
                break;
            case "24"://清单详情json
                intent.setClass(context, ReportDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","7");
                context.startActivity(intent);
                break;
            case "4"://免单主页
            case "3"://新品主页
                EventBus.getDefault().post(new ChangeHomePageBus(2));
                break;
            case "31"://试用商品详情(新品详情)
                intent.setClass(context,  TryDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("pos",-1);
                context.startActivity(intent);
                break;
            case "41"://免单详情
                intent.setClass(context,  FreeDetailActivity.class);
                intent.putExtra("id",target_id);
                context.startActivity(intent);
                break;
        }
    }
}
