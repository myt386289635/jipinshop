package com.example.administrator.jipinshop.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.wellcome.WellComeActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        api = WXAPIFactory.createWXAPI(this, null);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    //微信发送的请求将回调到 onReq 方法
    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()){
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                finish();
                Intent intent = new Intent(this, WellComeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    //发送到微信请求的响应结果将回调到 onResp 方法
    @Override
    public void onResp(BaseResp baseResp) {

    }
}
