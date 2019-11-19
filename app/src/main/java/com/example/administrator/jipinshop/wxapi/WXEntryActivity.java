package com.example.administrator.jipinshop.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.newpeople.detail.NewPeopleDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeNewDetailActivity;
import com.example.administrator.jipinshop.activity.wellcome.WellComeActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe 修复小程序无法打开安卓app的问题 需要安卓这边配合书写回调
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);//需要页面，否者打不开首页，会卡在小程序页面
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
                ShowMessageFromWX.Req showReq = (ShowMessageFromWX.Req) baseReq;
                WXMediaMessage wxMsg = showReq.message;
                String extDate= wxMsg.messageExt;//小程序传递过来的参数
                String[] str = extDate.split("&");
                String type = str[0].replace("type=","");
                String id = str[1].replace("id=","");
                switch (type){
                    case "0"://.新人免单详情页
                        Intent intent0 = new Intent(this, NewPeopleDetailActivity.class);
                        intent0.putExtra("freeId",id);
                        intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent0);
                        break;
                    case "1"://.老人免单详情页
                        Intent intent1 = new Intent(this, FreeNewDetailActivity.class);
                        intent1.putExtra("freeId",id);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                        default:
                            Intent intent = new Intent(this, WellComeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                }
                break;
        }
    }

    //发送到微信请求的响应结果将回调到 onResp 方法
    @Override
    public void onResp(BaseResp baseResp) {

    }
}
