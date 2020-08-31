package com.example.administrator.jipinshop.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.eventbus.PayBus;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 莫小婷
 * @create 2020/8/31
 * @Describe
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    public static final String pay_success = "pay_success";
    public static final String pay_faile = "pay_faile";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);

        api = WXAPIFactory.createWXAPI(this, "wxfd2e92db2568030a");
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

    }

    //发送到微信请求的响应结果将回调到 onResp 方法
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //微信支付的结果返回
            Log.d(TAG,"onPayFinish,errCode="+resp.errCode);
            if (resp.errCode == 0){
                //支付成功
                EventBus.getDefault().post(new PayBus(WXPayEntryActivity.pay_success));
            }else {
                //支付失败
                EventBus.getDefault().post(new PayBus(WXPayEntryActivity.pay_faile));
            }
            finish();
        }
    }
}
