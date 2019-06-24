package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.content.Context;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public class TaoBaoUtil {

    /****
     * 跳转淘宝首页
     */
    public static void openAliHomeWeb(Activity context, String url) {
        AlibcShowParams alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");
        //yhhpass参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTrade.show(context, new AlibcPage(url), alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
            }
        });
    }

}
