package com.example.administrator.jipinshop.activity.message;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.message.other.OtherMessageActivity;
import com.example.administrator.jipinshop.activity.message.system.SystemMessageActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityMessageBinding;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 消息页面
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    MessagePresenter mPresenter;
    private ActivityMessageBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("消息");
        if(Build.VERSION.SDK_INT < 19){
            mBinding.messageTitle.setVisibility(View.VISIBLE);
        }else {
            if(NotificationManagerCompat.from(this).areNotificationsEnabled()){
                mBinding.messageTitle.setVisibility(View.GONE);
            }else {
                mBinding.messageTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_start:
                //跳转到系统设置页面
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", MessageActivity.this.getPackageName(), null));
                startActivity(localIntent);
                break;
            case R.id.title_close:
                mBinding.messageTitle.setVisibility(View.GONE);
                break;
            case R.id.system_container:
                //系统消息
                startActivity(new Intent(this, SystemMessageActivity.class));
                break;
            case R.id.action_container:
                //极品城活动
                startActivity(new Intent(this, OtherMessageActivity.class)
                        .putExtra("title","极品城活动")
                );
                break;
            case R.id.evaluating_container:
                //评测赢大奖
                startActivity(new Intent(this, OtherMessageActivity.class)
                        .putExtra("title","评测赢大奖")
                );
                break;
        }
    }
}
