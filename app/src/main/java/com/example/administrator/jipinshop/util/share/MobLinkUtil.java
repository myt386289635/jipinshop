package com.example.administrator.jipinshop.util.share;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.mob.moblink.ActionListener;
import com.mob.moblink.MobLink;
import com.mob.moblink.Scene;

import java.util.HashMap;

/**
 * @author 莫小婷
 * @create 2019/9 /17
 * @Describe mobLink的SDK需要，  这里是为了唤醒app，接moblink是因为可以在微信直接唤起app
 */
public class MobLinkUtil {

    public static void mobShare(String id, String path, OnShareLinsenter shareLinsenter) {
        HashMap senceParams = new HashMap();
        senceParams.put("id", id);
        // 新建场景
        Scene s = new Scene();
        s.path = path;
        s.params = senceParams;
        // 请求场景ID
        MobLink.getMobID(s, new ActionListener<String>() {
            public void onResult(String mobID) {
                shareLinsenter.onShareMob(mobID);
            }

            public void onError(Throwable throwable) {
                shareLinsenter.onShareMob("");
            }
        });
    }

    public interface OnShareLinsenter {
        void onShareMob(String mobID);
    }

    public static void openStartActivity(Context context, String path, String id) {
        Intent intent = new Intent();
        switch (path) {
            case "/goods":
                intent.setClass(context, ShoppingDetailActivity.class);
                intent.putExtra("goodsId", id);
                context.startActivity(intent);
                break;
            case "/evaluation":
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", "2");
                context.startActivity(intent);
                break;
            case "/listing1":
                intent.setClass(context, ArticleDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", "7");
                context.startActivity(intent);
                break;
            case "/listing2":
                intent.setClass(context, ReportDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", "7");
                context.startActivity(intent);
                break;
            case "/tbkGoodsDetail":
                intent.setClass(context, TBShoppingDetailActivity.class);
                intent.putExtra("otherGoodsId", id);
                context.startActivity(intent);
                break;
        }
    }

}
