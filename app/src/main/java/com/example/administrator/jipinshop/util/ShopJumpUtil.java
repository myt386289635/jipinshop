package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity;
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity;
import com.example.administrator.jipinshop.activity.balance.withdraw.detail.WithdrawDetailActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.home.jd_pdd.KTJDDetailActivity;
import com.example.administrator.jipinshop.activity.home.tb.KTTBDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.minekt.orderkt.KTMyOrderActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.school.SchoolSpecialActivity;
import com.example.administrator.jipinshop.activity.school.video.VideoActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.web.dzp.BigWheelWebActivity;
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
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


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

    //跳转到华为应用商城
    public static boolean toHuawei(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.huawei.appmarket");
    }

    //跳转到百度手机助手
    public static boolean toBaidu(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.baidu.appsearch");
    }

    //跳转到OPPO应用商城
    public static boolean toOppo(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.oppo.market");
    }

    //跳转到vivo应用商城
    public static boolean toVivo(Context context, String appPkg) {
        return toMarket(context, appPkg, "com.bbk.appstore");
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


    //根据手机型号进行跳转，跳到目标应用商城
    public static void jumpMarkets(Context context){
        Boolean isJump = false;//目标应用商城是否跳转成功
        String appPkg = "com.example.administrator.jipinshop";
        String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
        if (deviceBrand.equals("huawei")){
            isJump = toHuawei(context,appPkg);
        }else if (deviceBrand.equals("xiaomi")){
            isJump = toXiaoMi(context,appPkg);
        }else if (deviceBrand.equals("vivo")){
            isJump = toVivo(context,appPkg);
        }else if (deviceBrand.equals("meizu")){
            isJump = toMeizu(context,appPkg);
        }
        if (!isJump){
            //目标应用商城没有跳转到
            if (!toQQDownload(context,appPkg)){
                if (!to360Download(context,appPkg)){
                    if (!toBaidu(context,appPkg)){
                        if (!toMarket(context,appPkg,null)){
                            ToastUtil.show("没有找到您手机里的应用商店，请确认");
                        }
                    }
                }
            }
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
    public static void openPager(Context context, String targetType, String target_id , String target_title){
        openBanner(context,targetType,target_id,target_title, "");
    }

    /**
     * 公用轮播图（广告位）跳转——首页模块
     */
    public static void openBanner(Context context, String targetType, String target_id , String target_title,String source){
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
                intent.putExtra(WebActivity.source,source);
                context.startActivity(intent);
                break;
            case "14"://极币商城
                intent.setClass(context, MallActivity.class);
                context.startActivity(intent);
                break;
            case "15"://任务中心
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, SignActivity.class);
                }
                context.startActivity(intent);
                break;
            case "16"://只在极币中心可用 红包功能
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
                intent.setClass(context, NewFreeActivity.class);
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
            case "25"://京东商品详情
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", target_id);
                intent.putExtra("source","1");
                context.startActivity(intent);
                break;
            case "26"://拼多多商品详情
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", target_id);
                intent.putExtra("source","4");
                context.startActivity(intent);
                break;
            case "27"://会员中心
                intent.setClass(context, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.member);
                context.startActivity(intent);
                break;
            case "28"://淘宝主页
                intent.setClass(context, KTTBDetailActivity.class);
                context.startActivity(intent);
                break;
            case "29"://0元购
                intent.setClass(context, NewFreeActivity.class);
                context.startActivity(intent);
                break;
            case "30"://大转盘
                intent.setClass(context, BigWheelWebActivity.class);
                intent.putExtra(BigWheelWebActivity.url, target_id);
                intent.putExtra(BigWheelWebActivity.title,"大转盘");
                context.startActivity(intent);
                break;
            case "31"://我的团队
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, TeamActivity.class);
                }
                context.startActivity(intent);
                break;
            case "32"://我的订单
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, KTMyOrderActivity.class);
                }
                context.startActivity(intent);
                break;
            case "33"://我的收益
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, MyWalletActivity.class);
                }
                context.startActivity(intent);
                break;
            case "34"://提现明细
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, WithdrawDetailActivity.class);
                }
                context.startActivity(intent);
                break;
            case "35"://极币明细
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    intent.setClass(context, LoginActivity.class);
                }else {
                    intent.setClass(context, IntegralDetailActivity.class);
                }
                context.startActivity(intent);
                break;
            case "36"://H5直接跳app
                intent.setClass(context, WebActivity.class);
                intent.putExtra(WebActivity.url, target_id);
                intent.putExtra(WebActivity.title,target_title);
                intent.putExtra(WebActivity.source,source);
                intent.putExtra(WebActivity.go, true);
                context.startActivity(intent);
                break;
            case "37":
                //商学院专题页
                intent.setClass(context, SchoolSpecialActivity.class);
                intent.putExtra("categoryId",target_id);
                intent.putExtra("title",target_title);
                context.startActivity(intent);
                break;
            case "38":
                //商学院播放页
                intent.setClass(context, VideoActivity.class);
                intent.putExtra("courseId",target_id);
                context.startActivity(intent);
                break;
            case "101":
                //滑到首页第一页
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                break;
            case "102":
                //滑到首页第二页
                EventBus.getDefault().post(new ChangeHomePageBus(1));
                break;
            case "103":
                //滑到首页第三页
                EventBus.getDefault().post(new ChangeHomePageBus(2));
                break;
            case "104":
                //滑到首页第四页
                EventBus.getDefault().post(new ChangeHomePageBus(3));
                break;
            case "105":
                //滑到首页第五页
                EventBus.getDefault().post(new ChangeHomePageBus(4));
                break;
        }
    }

}
