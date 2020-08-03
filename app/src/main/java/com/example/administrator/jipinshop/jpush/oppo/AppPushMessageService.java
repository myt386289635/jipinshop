package com.example.administrator.jipinshop.jpush.oppo;

import android.content.Context;
import android.util.Log;

import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.DataMessageCallbackService;

/**
 * @author 莫小婷
 * @create 2020/8/3
 * @Describe
 */
public class AppPushMessageService extends DataMessageCallbackService {

    @Override
    public void processMessage(Context context, DataMessage dataMessage) {
        super.processMessage(context, dataMessage);
        Log.d("PushMessageService", dataMessage.getContent());
    }

}
