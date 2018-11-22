package com.example.administrator.jipinshop.activity.message.system.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.message.system.SystemMessageActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.jpush.JPushReceiver;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe 系统消息详情页
 */
public class SystemMsgDetailActivity extends BaseActivity implements SystemMsgDetailView {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.detail_content)
    TextView mDetailContent;

    @Inject
    SystemMsgDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysmsg_detail);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mTitleBack.setOnClickListener(v -> finish());
        mTitleTv.setText(getIntent().getStringExtra("title"));
        mDetailContent.setText("\t\t\t\t" + getIntent().getStringExtra("content"));
        mPresenter.readMsg(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    /**
     * 已读成功回调
     */
    @Override
    public void onSuccess(SuccessBean successBean) {
        EventBus.getDefault().post(SystemMessageActivity.tag);
        EventBus.getDefault().post(JPushReceiver.TAG);//刷新首页未读消息
    }

    /**
     * 已读失败回调
     */
    @Override
    public void onFaile(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
