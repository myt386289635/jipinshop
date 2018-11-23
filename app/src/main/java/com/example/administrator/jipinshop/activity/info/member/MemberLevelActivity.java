package com.example.administrator.jipinshop.activity.info.member;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MemberLevelBean;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 会员中心
 */
public class MemberLevelActivity extends BaseActivity implements MemberLevelView {


    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.member_image)
    ImageView mMemberImage;
    @BindView(R.id.member_text)
    TextView mMemberText;
    @BindView(R.id.member_level)
    TextView mMemberLevel;

    @Inject
    MemberLevelPersenter mPersenter;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberlevel);
        mBaseActivityComponent.inject(this);
        mButterKnife =  ButterKnife.bind(this);
        mPersenter.setView(this);
        mTitleTv.setText("会员中心");
        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))){
            GlideApp.loderCircleImage(this,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg),mMemberImage,R.drawable.rlogo,R.drawable.rlogo);
//            ImageManager.displayCircleImage(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg),mMemberImage,R.drawable.rlogo,R.drawable.rlogo);
        }else {
            GlideApp.loderImage(this,R.drawable.rlogo,mMemberImage,R.drawable.rlogo,R.drawable.rlogo);
//            ImageManager.displayImage("drawable://" + R.drawable.rlogo,mMemberImage,R.drawable.rlogo,R.drawable.rlogo);
        }
        mMemberLevel.setText("v" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userMemberGrade));
        dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        dialog.show();
        mPersenter.getDate(this.bindToLifecycle());
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


    /**
     * 计算等级
     * @param level 积分数
     */
    public void getLevel(int level){
        if(level < 1000){
            mMemberText.setText("还需要"+(1000 - level)+"积分可升级到v1");
        }else if(level < 3000){
            mMemberText.setText("还需要"+(3000 - level)+"积分可升级到v2");
        }else if(level < 5000){
            mMemberText.setText("还需要"+(5000 - level)+"积分可升级到v3");
        }else if(level < 7000){
            mMemberText.setText("还需要"+(7000 - level)+"积分可升级到v4");
        }else if(level < 10000){
            mMemberText.setText("还需要"+(10000 - level)+"积分可升级到v5");
        }
    }

    @Override
    public void onSuccess(MemberLevelBean bean) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        int str = bean.getTotalAddPoint();
        getLevel(str);
    }

    @Override
    public void onFile(String error) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        int str = 0;
        getLevel(str);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
