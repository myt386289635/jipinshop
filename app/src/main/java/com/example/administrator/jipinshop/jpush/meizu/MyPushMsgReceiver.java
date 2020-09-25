package com.example.administrator.jipinshop.jpush.meizu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.jipinshop.bean.JPushBean;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.jpush.huawei.HmsActivity;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.google.gson.Gson;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/8/3
 * @Describe 魅族推送
 */
public class MyPushMsgReceiver extends MzPushMessageReceiver {

    @Inject
    Repository mRepository;

    /**
     * 调用订阅方法后，会在此方法回调结果
     * 订阅方法：PushManager.register(context, appId, appKey)
     */
    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        ApplicationComponent mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(context))
                        .build();
        mApplicationComponent.inject(this);
        sendRegTokenToServer(registerStatus.getPushId());
        Log.e("MyPushMsgReceiver", registerStatus.getPushId());
    }

    /**
     * 调用取消订阅方法后，会在此方法回调结果
     * 取消订阅方法：PushManager.unRegister(context, appId, appKey)
     */
    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) { }

    /**
     * 调用开关转换或检查开关状态方法后，会在此方法回调开关状态
     * 通知栏开关转换方法：PushManager.switchPush(context, appId, appKey, pushId, pushType, switcher)
     * 检查开关状态方法：PushManager.checkPush(context, appId, appKey, pushId)
     */
    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) { }

    /**
     * 调用标签订阅、取消标签订阅、取消所有标签订阅和获取标签列表方法后，会在此方法回调标签相关信息
     * 标签订阅方法：PushManager.subScribeTags(context, appId, appKey, pushId, tags)
     * 取消标签订阅方法：PushManager.unSubScribeTags(context, appId, appKey, pushId,tags)
     * 取消所有标签订阅方法：PushManager.unSubScribeAllTags(context, appId, appKey, pushId)
     * 获取标签列表方法：PushManager.checkSubScribeTags(context, appId, appKey, pushId)
     */
    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) { }

    /**
     * 调用别名订阅、取消别名订阅和获取别名方法后，会在此方法回调别名相关信息
     * 别名订阅方法：PushManager.subScribeAlias(context, appId, appKey, pushId, alias)
     * 取消别名订阅方法：PushManager.unSubScribeAlias(context, appId, appKey, pushId, alias)
     * 获取别名方法：PushManager.checkSubScribeAlias(context, appId, appKey, pushId)
     */
    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) { }

    /**
     * 当推送的通知栏消息展示后且应用进程存在时会在此方法回调
     * @param context
     * @param mzPushMessage
     */
    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        EventBus.getDefault().post(JPushReceiver.TAG);
    }


    /**
     * 当用户点击通知栏消息后会在此方法回调
     *  //如果不写回调，有时候点击没反应
     */
    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        Log.d("MyPushMsgReceiver", mzPushMessage.getContent());
        if (!TextUtils.isEmpty(mzPushMessage.getSelfDefineContentString())){
            JPushBean jPushBean = new Gson().fromJson(mzPushMessage.getSelfDefineContentString(), JPushBean.class);
            String targetType = jPushBean.getTargetType();
            String targetId = jPushBean.getTargetId();
            String targetTitle = jPushBean.getTargetTitle();
            String source= jPushBean.getSource();
            context.startActivity(new Intent(context, HmsActivity.class)
                    .putExtra("targetType",targetType)
                    .putExtra("targetId" , targetId)
                    .putExtra("targetTitle" , targetTitle)
                    .putExtra("source" , source)
            );
        }
    }

    private void sendRegTokenToServer(String token) {
        mRepository.addToken(3,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {}, throwable -> {});
    }
}
