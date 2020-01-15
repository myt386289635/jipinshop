package com.example.administrator.jipinshop.activity.sreach.result;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.TBSreachResultAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.bean.eventbus.SreachBus;
import com.example.administrator.jipinshop.databinding.ActivitySreachTbResultBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe 搜索结果页
 */
public class TBSreachResultActivity extends BaseActivity implements View.OnClickListener, TBSreachResultView, OnLoadMoreListener, OnRefreshListener, TBSreachResultAdapter.OnItem, ShareBoardDialog.onShareListener {

    @Inject
    TBSreachResultPresenter mPresenter;

    private ActivitySreachTbResultBinding mBinding;
    private Dialog mDialog;
    private String keyword = "";
    private String type = "";
    private String asc = "";
    private String orderByType = "0";
    private List<TBSreachResultBean.DataBean> mList;
    private TBSreachResultAdapter mAdapter;
    private int page = 1;
    private Boolean refresh = true;
    private List<TextView> mTextViews;
    private Boolean once = true;//是否是第一次进入页面
    private ShareBoardDialog mShareBoardDialog;
    private int sharePosition = -1;//分享的位置
    private String shareUrl = "";
    private String path = "";
    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_sreach_tb_result);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        keyword = getIntent().getStringExtra("content");
        type = getIntent().getStringExtra("type");
        mBinding.titleEdit.setText(keyword);
        mBinding.titleEdit.setSelection(mBinding.titleEdit.getText().length());//将光标移至文字末尾
        if (type.equals("1")){
            mBinding.sreachTypeJp.setTextColor(getResources().getColor(R.color.color_E25838));
            mBinding.sreachJpLine.setVisibility(View.VISIBLE);
            mBinding.sreachTypeTb.setTextColor(getResources().getColor(R.color.color_9D9D9D));
            mBinding.sreachTbLine.setVisibility(View.INVISIBLE);
        }else {
            mBinding.sreachTypeJp.setTextColor(getResources().getColor(R.color.color_9D9D9D));
            mBinding.sreachJpLine.setVisibility(View.INVISIBLE);
            mBinding.sreachTypeTb.setTextColor(getResources().getColor(R.color.color_E25838));
            mBinding.sreachTbLine.setVisibility(View.VISIBLE);
        }
        mTextViews = new ArrayList<>();
        mTextViews.add(mBinding.titleZh);
        mTextViews.add(mBinding.titleJg);
        mTextViews.add(mBinding.titleBt);
        mTextViews.add(mBinding.titleXl);
        initTitle(0);

        mBinding.swipeTarget.setLayoutManager(new GridLayoutManager(this,2));
        mList = new ArrayList<>();
        mAdapter = new TBSreachResultAdapter(mList,this);
        mAdapter.setLayoutType(1);//默认横向布局
        mAdapter.setOnItem(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> {
            mBinding.swipeToLoad.setRefreshing(true);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_sreach:
                if (TextUtils.isEmpty(mBinding.titleEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                keyword = mBinding.titleEdit.getText().toString();
                onRefresh();
                break;
            case R.id.sreach_jp:
                type = "1";
                mBinding.sreachTypeJp.setTextColor(getResources().getColor(R.color.color_E25838));
                mBinding.sreachJpLine.setVisibility(View.VISIBLE);
                mBinding.sreachTypeTb.setTextColor(getResources().getColor(R.color.color_9D9D9D));
                mBinding.sreachTbLine.setVisibility(View.INVISIBLE);
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.sreach_tb:
                type = "2";
                mBinding.sreachTypeJp.setTextColor(getResources().getColor(R.color.color_9D9D9D));
                mBinding.sreachJpLine.setVisibility(View.INVISIBLE);
                mBinding.sreachTypeTb.setTextColor(getResources().getColor(R.color.color_E25838));
                mBinding.sreachTbLine.setVisibility(View.VISIBLE);
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.title_zh:
                //综合
                orderByType = "0";
                asc = "";
                initTitle(0);
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.title_jg:
                //价格
                orderByType = "1";
                initTitle(1);
                if (TextUtils.isEmpty(asc) || asc.equals("0")){
                    asc = "1";
                }else {
                    asc = "0";
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.title_bt:
                //补贴
                orderByType = "2";
                asc = "";
                initTitle(2);
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.title_xl:
                //销量
                orderByType = "3";
                asc = "";
                initTitle(3);
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                onRefresh();
                break;
            case R.id.sreach_change:
                if (mAdapter.getLayoutType() == 1){
                    mAdapter.setLayoutType(2);//网格
                    mAdapter.notifyItemRangeChanged(0,mList.size());
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change1);
                }else {
                    mAdapter.setLayoutType(1);//横向
                    mAdapter.notifyItemRangeChanged(0,mList.size());
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change);
                }
                break;
        }
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        mPresenter.searchTBGoods(this,asc,keyword,orderByType,page,type,this.bindToLifecycle());
    }

    @Override
    public void onRefresh() {
        refresh = true;
        page = 1;
        mBinding.swipeTarget.scrollToPosition(0);
        mPresenter.searchTBGoods(this,asc,keyword,orderByType,page,type,this.bindToLifecycle());
    }

    @Override
    public void onSuccess(TBSreachResultBean bean) {
        EventBus.getDefault().post(new SreachBus(SreachActivity.sreachHistoryTag));//刷新历史搜索
        if(refresh) {
            if (bean.getData() != null && bean.getData().size() != 0){
                //有数据
                mList.clear();
                if (bean.getData().get(0).getGoodsType() == 3){//没有数据时
                    if (once && type.equals("1")){//第一次进入页面且type=1
                        ToastUtil.show("极品城暂无相关推荐");
                        type = "2";
                        mBinding.sreachTypeJp.setTextColor(getResources().getColor(R.color.color_9D9D9D));
                        mBinding.sreachJpLine.setVisibility(View.INVISIBLE);
                        mBinding.sreachTypeTb.setTextColor(getResources().getColor(R.color.color_E25838));
                        mBinding.sreachTbLine.setVisibility(View.VISIBLE);
                        onRefresh();
                        once = false;
                    }else {
                        dissRefresh();
                        TBSreachResultBean.DataBean headBean = new TBSreachResultBean.DataBean();
                        headBean.setGoodsType(4);
                        mList.add(headBean);
                        mList.addAll(bean.getData());
                    }
                }else {
                    dissRefresh();
                    for (int i = 0; i < bean.getData().size(); i++) {
                        if (i != 0 && bean.getData().get(i).getGoodsType() != bean.getData().get(i - 1).getGoodsType()){
                            TBSreachResultBean.DataBean headBean = new TBSreachResultBean.DataBean();
                            headBean.setGoodsType(4);
                            mList.add(headBean);
                            mList.add(bean.getData().get(i));
                        }else {
                            mList.add(bean.getData().get(i));
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }else {
            dissLoading();
            if (bean.getData() != null && bean.getData().size() != 0){
                //有数据
                for (int i = 0; i < bean.getData().size(); i++) {
                    if (bean.getData().get(i).getGoodsType() != mList.get(mList.size() - 1).getGoodsType()){
                        TBSreachResultBean.DataBean headBean = new TBSreachResultBean.DataBean();
                        headBean.setGoodsType(4);
                        mList.add(headBean);
                        mList.add(bean.getData().get(i));
                    }else {
                        mList.add(bean.getData().get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
        if (once){
            once = false;
        }
    }

    @Override
    public void onFile(String error) {
        if(refresh){
            dissRefresh();
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public void dissLoading(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
    }

    //初始化标题
    public void initTitle(int position){
        Drawable drawable= getResources().getDrawable(R.mipmap.sreach_price3);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBinding.titleJg.setCompoundDrawables(null,null,drawable,null);
        for (int i = 0; i < mTextViews.size(); i++) {
            mTextViews.get(i).setTextColor(getResources().getColor(R.color.color_9D9D9D));
            mTextViews.get(i).getPaint().setFakeBoldText(false);
        }
        for (int i = 0; i < mTextViews.size(); i++) {
            if (i == position){
                if (position == 1){
                    Drawable upDrawable;
                    if (TextUtils.isEmpty(asc) || asc.equals("0")){
                        upDrawable= getResources().getDrawable(R.mipmap.sreach_price);
                    }else {
                        upDrawable= getResources().getDrawable(R.mipmap.sreach_price1);
                    }
                    upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
                    mBinding.titleJg.setCompoundDrawables(null,null,upDrawable,null);
                }
                mTextViews.get(i).setTextColor(getResources().getColor(R.color.color_202020));
                mTextViews.get(i).getPaint().setFakeBoldText(true);
            }
        }
    }

    @Override
    public void onItemShare(int position) {
        sharePosition = position;
        if (mShareBoardDialog == null) {
            mShareBoardDialog = ShareBoardDialog.getInstance("","");
            mShareBoardDialog.setOnShareListener(this);
        }
        if (!mShareBoardDialog.isAdded()) {
            mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        path = "pages/list/main-v2-info/main?id=" + mList.get(sharePosition).getOtherGoodsId();
        shareImage =  mList.get(sharePosition).getImg();
        shareName = mList.get(sharePosition).getOtherName();
        shareContent = "【分享来自极品城APP】看评测选好物，省心更省钱";
        shareUrl = RetrofitModule.H5_URL + "share/tbGoodsDetail.html?id=" + mList.get(sharePosition).getOtherGoodsId();
        if (share_media.equals(SHARE_MEDIA.WEIXIN)){
            if(mDialog != null && !mDialog.isShowing()){
                mDialog.show();
            }
            mPresenter.getTbkGoodsPoster( mList.get(sharePosition).getOtherGoodsId() , this.bindToLifecycle());
        }else {
            MobLinkUtil.mobShare(mList.get(sharePosition).getOtherGoodsId(), "/tbkGoodsDetail", mobID -> {
                if (!TextUtils.isEmpty(mobID)){
                    shareUrl += "&mobid=" + mobID;
                }
                new ShareUtils(this, share_media,mDialog)
                        .shareWeb(this, shareUrl, shareName, shareContent, shareImage, R.mipmap.share_logo);
            });
        }
    }

    @Override
    public void onShareSuc(ImageBean bean) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWXMin1(this,shareUrl,bean.getData(),shareName,shareContent,path);
    }

    @Override
    public void onShareFile() {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWXMin1(this,shareUrl,shareImage,shareName,shareContent,path);
    }
}
