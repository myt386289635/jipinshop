package com.example.administrator.jipinshop.jpush.huawei;

import android.util.Log;

import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.huawei.hms.push.HmsMessageService;

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
