package com.example.administrator.jipinshop.activity.mall.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.mall.exchange.ExchangeActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivityMallDetailBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城详情页
 */
public class MallDetailActivity extends BaseActivity implements View.OnClickListener, MallDetailView {

    @Inject
    MallDetailPresenter mPresenter;
    private ActivityMallDetailBinding mBinding;

    //商品id
    private String goodsId = "";
    //加载框
    private Dialog mDialogProgress;
    //banner
    private CommenBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 100){
                if (mBinding.viewPager != null) {
                    mBinding.viewPager.setCurrentItem(mBinding.viewPager.getCurrentItem() + 1);
                }
            }else if(msg.what == 1){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailWeb.getLayoutParams();
                layoutParams.height = (int) (msg.arg1 * getResources().getDisplayMetrics().density);
                mBinding.detailWeb.setLayoutParams(layoutParams);
            }else if(msg.what == 101){
                mBinding.detailBottom.setVisibility(View.VISIBLE);
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean stopThread = true;
    private MallDetailBean.DataBean mMallDetailBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_mall_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("商品详情");
        goodsId = getIntent().getStringExtra("goodsId");//商品id
        mDialogProgress = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialogProgress.show();
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mPresenter.setStatusBarHight(mBinding.statusBar2,this);
        mPresenter.initHeight(this,mBinding);

        mBannerAdapter = new CommenBannerAdapter(this);
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBannerAdapter.setImgCenter(true);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        mBinding.viewPager.setCurrentItem(mBannerList.size() * 10);

        mPresenter.initWebView(mBinding.detailWeb);
        mBinding.detailWeb.addJavascriptInterface(new WebViewJavaScriptFunction(), "android");
        mBinding.detailWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                mBinding.detailWeb.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
            }
        });

        mBinding.srcollView.setOnScrollListener(top -> {
            //控制头布局显示与消失情况
            float a = top;
            float b = a / 1000;
            float max = (float) Math.min(1, b * 2);
            mBinding.headContanier.setAlpha(max);
        });

        //网络请求数据
        mPresenter.getDate(goodsId,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_finish:
            case R.id.title_back:
                finish();
                break;
            case R.id.net_clude:
                if(mBinding.netClude.errorTitle.getText().toString().equals("网络出错")){
                    mDialogProgress = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                    mDialogProgress.show();
                    mPresenter.getDate(goodsId,this.bindToLifecycle());
                }
                break;
            case R.id.detail_bottom:
                if(mBinding.detailBottom.getText().toString().equals("立即兑换")){
                    startActivityForResult(new Intent(this, ExchangeActivity.class)
                            .putExtra("date",mMallDetailBean)
                    ,300);
                }else if (mBinding.detailBottom.getText().toString().equals("极币不足,去赚取")){
                    startActivity(new Intent(this, SignActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        stopThread = false;
        mHandler.removeCallbacksAndMessages(null);
        //防止webView内存溺出
        if (mBinding.detailWeb != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mBinding.detailWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.detailWeb);
            }
            mBinding.detailWeb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mBinding.detailWeb.getSettings().setJavaScriptEnabled(false);
            mBinding.detailWeb.clearHistory();
            mBinding.detailWeb.clearView();
            mBinding.detailWeb.removeAllViews();
            mBinding.detailWeb.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSuccess(MallDetailBean shoppingDetailBean) {
        if(shoppingDetailBean.getCode() == 0){
            mMallDetailBean = shoppingDetailBean.getData();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            //初始值
            mBinding.detailName.setText(shoppingDetailBean.getData().getGoodsName());
            mBinding.detailCode.setText(shoppingDetailBean.getData().getExchangePoint() + "极币");
            mBinding.detailEffective.setText(shoppingDetailBean.getData().getStartTime().split(" ")[0] + "至" +
            shoppingDetailBean.getData().getEndTime().split(" ")[0]);
            if (getIntent().getIntExtra("isActivityGoods",0) == 1 && shoppingDetailBean.getData().getHasBuy() == 1){
                mBinding.detailBottom.setText("已兑换（新人仅享兑换一次）");
                mBinding.detailBottom.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
            }else if(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) < shoppingDetailBean.getData().getExchangePoint()){
                mBinding.detailBottom.setText("极币不足,去赚取");
                mBinding.detailBottom.setBackgroundColor(getResources().getColor(R.color.color_E31436));
            }else if(shoppingDetailBean.getData().getTotal() == 0){
                mBinding.detailBottom.setText("已售空");
                mBinding.detailBottom.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
            }else {
                mBinding.detailBottom.setText("立即兑换");
                mBinding.detailBottom.setBackgroundColor(getResources().getColor(R.color.color_E31436));
            }
            //轮播图设置值
            if(shoppingDetailBean.getData().getImgList().size() == 1){
                mBinding.viewPager.setVisibility(View.GONE);
                mBinding.detailPoint.setVisibility(View.GONE);
                mBinding.pagerImage.setVisibility(View.VISIBLE);
                GlideApp.loderImage(this,shoppingDetailBean.getData().getImgList().get(0),mBinding.pagerImage,0,0);
            }else {
                mBinding.viewPager.setVisibility(View.VISIBLE);
                mBinding.detailPoint.setVisibility(View.VISIBLE);
                mBinding.pagerImage.setVisibility(View.GONE);
                mBannerList.addAll(shoppingDetailBean.getData().getImgList());
                mPresenter.initBanner(mBannerList, this, point, mBinding.detailPoint, mBannerAdapter);
                new Thread(new MyRunble()).start();
            }

            mBinding.detailWeb.loadDataWithBaseURL(null,
                    shoppingDetailBean.getData().getContent(),
                    "text/html", "utf-8", null);

            if (mDialogProgress != null && mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
        }else {
            if (mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
            ToastUtil.show(shoppingDetailBean.getMsg());
            initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        ToastUtil.show(error);
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    /******************轮播图需要*********************/
    public class MyRunble implements Runnable {
        @Override
        public void run() {
            while (stopThread) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(100);
            }
        }
    }

    public class WebViewJavaScriptFunction {
        /**
         * 高度
         */
        @JavascriptInterface
        public void getBodyHeight(String number) {
            int webViewHeight = Integer.parseInt(number.split("[.]")[0]);

            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = webViewHeight;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 300){
            setResult(300);
            finish();
        }
    }

    /**
     * 每日任务里点击跳转后，需要关闭的页面
     */
    @Subscribe
    public void changePage(ChangeHomePageBus bus){
        if(bus != null){
            finish();
        }
    }
}
