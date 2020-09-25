package com.example.administrator.jipinshop.jpush.huawei;

import android.util.Log;

import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/7/23
 * @Describe 华为推送
 */
public class MyHmsMessageService extends HmsMessageService {

    @Inject
    Repository mRepository;

    //消息接收  App如果订阅了主题消息或者服务器主动推送的透传消息都需要开发者通过实现onMessageReceived()回调方法来接收消息
    //通知好像不走这里
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        EventBus.getDefault().post(JPushReceiver.TAG);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        ApplicationComponent mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                        .build();
        mApplicationComponent.inject(this);
        sendRegTokenToServer(token);
    }

    @Override
    public void onTokenError(Exception e) {
        super.onTokenError(e);
        Log.e("MyHmsMessageService", e.getMessage());
    }

    private void sendRegTokenToServer(String token) {
        mRepository.addToken(1,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {}, throwable -> {});
    }
}
