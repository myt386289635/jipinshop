package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityReshopingDetailBinding;
import com.example.administrator.jipinshop.fragment.shoppingdetail.commen.DetailCommenFragment;
import com.example.administrator.jipinshop.fragment.shoppingdetail.evaluation.DetailEvaluationFragment;
import com.example.administrator.jipinshop.fragment.shoppingdetail.shop.DetailShopFragment;
import com.example.administrator.jipinshop.util.MainThreadPoster;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe 商品详情的改革
 */
public class ReShoppingDetailActivity extends BaseActivity implements View.OnClickListener, ShareBoardDialog.onShareListener, ReShoppingDetailView, ViewPager.OnPageChangeListener {

    @Inject
    ReShoppingDetailPresentere mPresenter;

    private ActivityReshopingDetailBinding mBinding;
    //分享面板
    private ShareBoardDialog mShareBoardDialog;
    //打开第三方天猫时，为了提高用户体验设计的
    private Dialog mDialog;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    private int[] usableHeightPrevious = {0};
    private Boolean isShowKey = false;//判断软键盘是否显示出现了

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_reshoping_detail);
        mBinding.setLinstener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments.add(new DetailShopFragment());
        mFragments.add(new DetailEvaluationFragment());
        mFragments.add(new DetailCommenFragment());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.setOffscreenPageLimit(3);
        mPresenter.setView(this);
        mPresenter.initTabLayout(this,mBinding.tabLayout);
        //监听软键盘的弹出与收回
        mPresenter.setKeyListener(mBinding.layout, usableHeightPrevious);
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                finish();
                hideSoftInput();
                break;
            case R.id.detail_favor:
                //收藏
                break;
            case R.id.detail_share:
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_buy:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                if(mDialog != null && !mDialog.isShowing()){
                    mDialog.show();
                }
                openAliHomeWeb();
                break;
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, "https://www.baidu.com", "测试", "测试而已", "", R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /****
     * 跳转淘宝首页
     */
    public void openAliHomeWeb() {
        String url = "http://m.tb.cn/h.3fFY9hK";
        Boolean tb = true;

        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        if(tb){
            //淘宝协议
            alibcShowParams.setClientType("taobao_scheme");
        }else {
            //天猫协议
            alibcShowParams.setClientType("tmall_scheme");
        }

        //yhhpass参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        // 若非淘客taokeParams设置为null即可
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.adzoneid = "57328044";
        alibcTaokeParams.pid = "mm_26632322_6858406_23810104";
        alibcTaokeParams.subPid = "mm_26632322_6858406_23810104";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","23373400");

        AlibcTrade.show(this, new AlibcPage(url), alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                if (alibcTradeResult.resultType.equals(AlibcResultType.TYPECART)) {
                    //加购成功
                    Log.e("AlibcTradeSDK", "加购成功");
                } else if (alibcTradeResult.resultType.equals(AlibcResultType.TYPEPAY)) {
                    //支付成功
                    Log.e("AlibcTradeSDK", "支付成功,成功订单号为" + alibcTradeResult.payResult.paySuccessOrders);
                }
                Log.e("AlibcTradeSDK", "加购成功");
                Toast.makeText(ReShoppingDetailActivity.this, "进去了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errCode, String errMsg) {
                Log.e("AlibcTradeSDK", "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    /**
     * 键盘的显示
     * @param isShow
     */
    public void showkeyboard(boolean isShow){
        showKeyboard(isShow);
    }

    /**
     * 键盘的隐藏
     */
    public void hideSoftInput() {
        if (isShowKey) {
            // 如果开启
            mImm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0); //强制隐藏键盘
            isShowKey = false;//隐藏了
        }
    }

    /**
     * 键盘弹出
     */
    @Override
    public void keyShow() {
        isShowKey = true;//显示了
        mBinding.keyEdit.requestFocus();
        mBinding.bottomLayout.setVisibility(View.GONE);
        mBinding.detailKeyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 软键盘收起
     */
    @Override
    public void keyHint() {
        isShowKey = false;//隐藏了
        mBinding.detailKeyLayout.setVisibility(View.GONE);
        MainThreadPoster.postRunnableDelay(() -> mBinding.bottomLayout.setVisibility(View.VISIBLE), HANDLER_TOKEN, 50);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        hideSoftInput();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            hideSoftInput();
        }
        return super.dispatchTouchEvent(ev);
    }
}
