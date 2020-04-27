package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.home.jd_pdd.KTJDDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.sp.CommonDate;

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
     * 首页弹窗dialog跳转逻辑
     */
    public static void openPager(Context context, String targetType, String target_id , String target_title ){
        Intent intent = new Intent();
        switch (targetType){
            case "1"://榜单首页
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.bangdan);
                context.startActivity(intent);
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
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.evaluation);
                context.startActivity(intent);
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
//                EventBus.getDefault().post(new ChangeHomePageBus(2));
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

    /**
     * 公用轮播图（广告位）跳转——首页模块
     */
    public static void openBanner(Context context, String targetType, String target_id , String target_title){
        Intent intent = new Intent();
        switch (targetType){
            case "2"://评测详情
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","2");
                context.startActivity(intent);
                break;
            case "11"://专题页面
                intent.setClass(context, HomeDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("title",target_title);
                context.startActivity(intent);
                break;
            case "12"://淘宝客详情页面
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", target_id);
                context.startActivity(intent);
                break;
            case "13"://H5页面
                intent.setClass(context, WebActivity.class);
                intent.putExtra(WebActivity.url, target_id);
                intent.putExtra(WebActivity.title,target_title);
                context.startActivity(intent);
                break;
            case "14"://极币商城
                intent.setClass(context, MallActivity.class);
                context.startActivity(intent);
                break;
            case "15"://任务中心
                intent.setClass(context, SignActivity.class);
                context.startActivity(intent);
                break;
            case "17"://榜单主页
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.bangdan);
                context.startActivity(intent);
                break;
            case "18"://特惠购列表
                intent.setClass(context, CheapBuyActivity.class);
                context.startActivity(intent);
                break;
            case "19"://0元购
                intent.setClass(context, NewPeopleActivity.class);
                context.startActivity(intent);
                break;
            case "20"://邀请页面
                intent.setClass(context, InvitationNewActivity.class);
                context.startActivity(intent);
                break;
            case "21"://京东
                intent.setClass(context, KTJDDetailActivity.class);
                intent.putExtra("name","京东");
                intent.putExtra("source","1");
                context.startActivity(intent);
                break;
            case "22"://拼多多
                intent.setClass(context, KTJDDetailActivity.class);
                intent.putExtra("name","拼多多");
                intent.putExtra("source","4");
                context.startActivity(intent);
                break;
            case "23"://跳转到评测主页
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.evaluation);
                context.startActivity(intent);
                break;
            case "24"://新手教程
                intent.setClass(context, WebActivity.class);
                intent.putExtra(WebActivity.url, RetrofitModule.H5_URL+"tbk-rule.html");
                intent.putExtra(WebActivity.title,"极品城省钱攻略");
                intent.putExtra(WebActivity.isShare,true);
                intent.putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？");
                intent.putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！");
                intent.putExtra(WebActivity.shareImage,"https://jipincheng.cn/shengqian.png");
                context.startActivity(intent);
                break;
        }
    }

    /**
     * 推送跳转
     */
    public static void openJPush(Context context, String targetType, String target_id , String target_title){
        Intent intent = new Intent();
        switch (targetType){
            case "2"://评测详情
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","2");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "11"://专题页面
                intent.setClass(context, HomeDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("title",target_title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "12"://淘宝客详情页面
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", target_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "13"://H5页面
                intent.setClass(context, WebActivity.class);
                intent.putExtra(WebActivity.url, target_id);
                intent.putExtra(WebActivity.title,target_title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "14"://极币商城
                intent.setClass(context, MallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "15"://任务中心
                intent.setClass(context, SignActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "16"://红包主页
                EventBus.getDefault().post(new ChangeHomePageBus(1));
                break;
            case "17"://榜单主页
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.bangdan);
                context.startActivity(intent);
                break;
            case "18"://特惠购列表
                intent.setClass(context, CheapBuyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            default://默认跳转首页
                intent.setClass(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

    /**
     * 首页宫格通用跳转
     */
    public static void openCommen(Context context, String targetType, String target_id , String target_title){
        Intent intent = new Intent();
        switch (targetType){
            case "3"://评测详情
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("type","2");
                context.startActivity(intent);
                break;
            case "1"://专题页面
                intent.setClass(context, HomeDetailActivity.class);
                intent.putExtra("id",target_id);
                intent.putExtra("title",target_title);
                context.startActivity(intent);
                break;
            case "2"://淘宝客详情页面
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", target_id);
                context.startActivity(intent);
                break;
            case "4"://H5页面
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, WebActivity.class);
                    intent.putExtra(WebActivity.url, target_id);
                    intent.putExtra(WebActivity.title,target_title);
                }
                context.startActivity(intent);
                break;
            case "5"://极币商城
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, MallActivity.class);
                }
                context.startActivity(intent);
                break;
            case "6"://任务中心
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, SignActivity.class);
                }
                context.startActivity(intent);
                break;
            case "8"://跳转到榜单页面
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.bangdan);
                context.startActivity(intent);
                break;
            case "9"://跳转到评测主页
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.evaluation);
                context.startActivity(intent);
                break;
            case "10"://特惠购
                intent.setClass(context, CheapBuyActivity.class);
                context.startActivity(intent);
                break;
            case "11"://0元购
                intent.setClass(context, NewPeopleActivity.class);
                context.startActivity(intent);
                break;
            case "12"://京东
                intent.setClass(context, KTJDDetailActivity.class);
                intent.putExtra("name","京东");
                intent.putExtra("source","1");
                context.startActivity(intent);
                break;
            case "13"://拼多多
                intent.setClass(context, KTJDDetailActivity.class);
                intent.putExtra("name","拼多多");
                intent.putExtra("source","4");
                context.startActivity(intent);
                break;
            case "14"://新手教程
                intent.setClass(context, WebActivity.class);
                intent.putExtra(WebActivity.url, RetrofitModule.H5_URL+"tbk-rule.html");
                intent.putExtra(WebActivity.title,"极品城省钱攻略");
                intent.putExtra(WebActivity.isShare,true);
                intent.putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？");
                intent.putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！");
                intent.putExtra(WebActivity.shareImage,"https://jipincheng.cn/shengqian.png");
                context.startActivity(intent);
                break;
            case "15"://邀请页面
                intent.setClass(context, InvitationNewActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
