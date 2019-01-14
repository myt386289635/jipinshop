package com.example.administrator.jipinshop.activity.message.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.MessageMsgBus;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe 系统消息详情页
 */
public class MsgDetailActivity extends BaseActivity implements MsgDetailView {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.detail_content)
    TextView mDetailContent;

    @Inject
    MsgDetailPresenter mPresenter;

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
        EventBus.getDefault().post(new MessageMsgBus(MessageActivity.tag,getIntent().getIntExtra("position",0)));
        EventBus.getDefault().post(JPushReceiver.TAG);//刷新首页未读消息
    }

    /**
     * 已读失败回调
     */
    @Override
    public void onFaile(String error) {
        ToastUtil.show(error);
    }
}
