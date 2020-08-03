package com.example.administrator.jipinshop.jpush.oppo;

import android.content.Context;
import android.util.Log;

import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.CompatibleDataMessageCallbackService;

/**
 * @author 莫小婷
 * @create 2020/8/3
 * @Describe
 */
public class PushMessageService extends CompatibleDataMessageCallbackService {

    @Override
    public void processMessage(Context context, DataMessage dataMessage) {
        super.processMessage(context, dataMessage);
        Log.d("PushMessageService", dataMessage.getContent());
    }

}
