package com.example.administrator.jipinshop.activity.newpeople;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.newpeople.detail.NewPeopleDetailActivity;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.adapter.NewPeopleAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.databinding.ActivityNewPeopleBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe 新人专区
 */
public class NewPeopleActivity extends BaseActivity implements OnRefreshListener, NewPeopleAdapter.OnClickItem, NewPeopleView, View.OnClickListener {

    @Inject
    NewPeoplePresenter mPresenter;

    private ActivityNewPeopleBinding mBinding;
    private List<NewPeopleBean.DataBean.NewAllowanceGoodsListBean> mList;//零元购商品
    private List<NewPeopleBean.DataBean.AllowanceGoodsListBean> mList2;//特惠购商品
    private List<NewPeopleBean.DataBean.NewAllowanceGoodsListBean> mTempList;//临时数据
    private NewPeopleAdapter mAdapter;
    private String officialWeChat = "jpkele";//客服微信
    private Dialog mDialog;
    private Boolean startPop = true;//是否弹出关闭确认弹窗
    private Boolean jumpPage = false;//是否跳转特惠购页面
    private List<String> strings;
    private String allowance = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_people);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mBinding.inClude.titleTv.setText("新人0元专区");
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_white));

        strings = new ArrayList<>();
        mList = new ArrayList<>();
        mList2 = new ArrayList<>();
        mTempList = new ArrayList<>();
        mAdapter = new NewPeopleAdapter(mList, mList2, this);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onRefresh() {
        mPresenter.getData(this.bindToLifecycle());
    }


    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    public void stopResher() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    @Override
    public void onBuy(int position) {
        //进入新人详情页
        mBinding.bgGuide1.setVisibility(View.GONE);
        mBinding.bgGuide2.setVisibility(View.GONE);
        startActivity(new Intent(this, NewPeopleDetailActivity.class)
                .putExtra("freeId",mList.get(position).getId())
                .putExtra("otherGoodsId",mList.get(position).getOtherGoodsId())
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (jumpPage){
            //跳转到特惠购页面
            startActivity(new Intent(this, CheapBuyActivity.class));
            finish();
            jumpPage = false;
        }
    }

    @Override
    public void onCopy() {
        ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("jipinshop", officialWeChat);
        clip.setPrimaryClip(clipData);
        ToastUtil.show("微信号复制成功");
        SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat);
    }

    @Override
    public void onRule() {
        startActivity(new Intent(this, WebActivity.class)
                .putExtra(WebActivity.url, RetrofitModule.H5_URL + "new-rule-app.html")
                .putExtra(WebActivity.title, "规则说明")
        );
    }

    @Override
    public void onMore() {
        if (mTempList.size() == 0) {
            ToastUtil.show("没有更多数据");
            return;
        }
        if (mList.size() <= 4) {
            mList.addAll(mTempList);
            mAdapter.notifyItemRangeInserted(5, mTempList.size());
            mAdapter.notifyItemChanged(mList.size() + 1);
        } else {
            //批量删除
            mAdapter.notifyItemRangeRemoved(5, mTempList.size());
            mList.removeAll(mTempList);
            mAdapter.notifyItemRangeChanged(5, mAdapter.getItemCount());
        }
    }

    @Override
    public void onzBuy(int position) {
        DialogUtil.LoginDialog(this, "您将前往淘宝购买此商品，\n使用津贴立减￥"+ mList2.get(position).getUseAllowancePrice() +"，无需等待返现",
                "确认", "取消", R.color.color_202020, R.color.color_202020,
                false, v -> {
                    String specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "");
                    if (TextUtils.isEmpty(specialId) || specialId.equals("null")) {
                        TaoBaoUtil.aliLogin(topAuthCode -> {
                            startActivity(new Intent(this, TaoBaoWebActivity.class)
                                    .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                                    .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                            );
                        });
                    } else {
                        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                        if (mDialog != null && !mDialog.isShowing()) {
                            mDialog.show();
                        }
                        mPresenter.apply(mList2.get(position).getId(), this.bindToLifecycle());
                    }
                });
    }

    @Override
    public void onSuccess(NewPeopleBean bean) {
        stopResher();
        if (bean.getData() != null && bean.getData().getNewAllowanceGoodsList().size() != 0) {
            officialWeChat = bean.getData().getOfficialWeChat();
            allowance = bean.getData().getAllowance();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            strings.clear();
            if (bean.getData().getNewAllowanceGoodsList().size() >= 5){
                for (int i = 2; i < 5; i++) {
                    strings.add(bean.getData().getNewAllowanceGoodsList().get(i).getImg());
                }
            }else if (bean.getData().getNewAllowanceGoodsList().size() >= 3){
                for (int i = 0; i < 3; i++) {
                    strings.add(bean.getData().getNewAllowanceGoodsList().get(i).getImg());
                }
            }
            mList.clear();
            mList2.clear();
            mTempList.clear();
            for (int i = 0; i < 4 && i < bean.getData().getNewAllowanceGoodsList().size(); i++) {
                mList.add(bean.getData().getNewAllowanceGoodsList().get(i));
            }
            for (int i = 4; i < bean.getData().getNewAllowanceGoodsList().size(); i++) {
                mTempList.add(bean.getData().getNewAllowanceGoodsList().get(i));
            }
            mList2.addAll(bean.getData().getAllowanceGoodsList());
            mAdapter.setAllowance(bean.getData().getAllowance());
            mAdapter.notifyDataSetChanged();
        } else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }
        if(SPUtils.getInstance().getBoolean(CommonDate.FIRSTNEWPEOPLE,true)){
            SPUtils.getInstance().put(CommonDate.FIRSTNEWPEOPLE,false);
            mBinding.bgGuide1.setVisibility(View.VISIBLE);
            mBinding.bgGuide2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show(error);
    }

    @Override
    public void onBuySuccess(ImageBean bean) {
        jumpPage = true;
        TaoBaoUtil.openAliHomeWeb(this, bean.getData(), bean.getOtherGoodsId());
    }

    @Override
    public void onBuyFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                if (startPop){
                    if (strings.size() != 0){
                        DialogUtil.outDialog(this, strings, allowance, v1 -> {
                            finish();
                        });
                    }
                    startPop = false;
                }else {
                    finish();
                }
                break;
            case R.id.bg_guide1:
            case R.id.bg_guide3:
            case R.id.bg_guide4:
                mBinding.bgGuide1.setVisibility(View.GONE);
                mBinding.bgGuide2.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mBinding.bgGuide1.getVisibility() == View.VISIBLE ||
                mBinding.bgGuide2.getVisibility() == View.VISIBLE){
            mBinding.bgGuide1.setVisibility(View.GONE);
            mBinding.bgGuide2.setVisibility(View.GONE);
        }else if (startPop){
            if (strings.size() != 0){
                DialogUtil.outDialog(this, strings, allowance, v1 -> {
                    finish();
                });
            }
            startPop = false;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
    }
}
