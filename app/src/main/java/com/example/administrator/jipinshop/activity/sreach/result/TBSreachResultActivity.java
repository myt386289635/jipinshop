package com.example.administrator.jipinshop.activity.sreach.result;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.KTTabAdapter4;
import com.example.administrator.jipinshop.adapter.TBSreachResultAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.bean.eventbus.SreachBus;
import com.example.administrator.jipinshop.databinding.ActivitySreachTbResultBinding;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe 搜索结果页
 */
public class TBSreachResultActivity extends BaseActivity implements View.OnClickListener, TBSreachResultView, OnLoadMoreListener, OnRefreshListener, TBSreachResultAdapter.OnItem, KTTabAdapter4.OnItem {

    @Inject
    TBSreachResultPresenter mPresenter;

    private ActivitySreachTbResultBinding mBinding;
    private Dialog mDialog;
    private String keyword = "";
    private String asc = "";
    private String orderByType = "0";
    private List<TBSreachResultBean.DataBean> mList;
    private TBSreachResultAdapter mAdapter;
    private int page = 1;
    private Boolean refresh = true;
    private List<TextView> mTextViews;
    private Boolean once = true;//是否是第一次进入页面
    //tab
    private KTTabAdapter4 mTabAdapter;
    private List<String> titles;
    private String type = "";//搜索类型

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
        //选择tab
        CommonNavigator commonNavigator = new CommonNavigator(this);
        titles = new ArrayList<>();
        titles.add("淘宝");
        titles.add("京东");
        titles.add("拼多多");
        mTabAdapter = new KTTabAdapter4(titles);
        mTabAdapter.setOnClick(this);
        mTabAdapter.setColor(getResources().getColor(R.color.color_202020),
                getResources().getColor(R.color.color_E25838));
        mTabAdapter.setTextSize(16f);
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.titleContainer.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable(){
            @Override
            public int getIntrinsicWidth() {
                return (int) getResources().getDimension(R.dimen.x130);
            }
        });
        if (type.equals("2")){ //淘宝
            mBinding.titleContainer.onPageSelected(0);
            mBinding.titleContainer.onPageScrolled(0, 0.0F, 0);
        }else if (type.equals("1")){//京东
            mBinding.titleContainer.onPageSelected(1);
            mBinding.titleContainer.onPageScrolled(1, 0.0F, 0);
        }else {//拼多多
            mBinding.titleContainer.onPageSelected(2);
            mBinding.titleContainer.onPageScrolled(2, 0.0F, 0);
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
        mPresenter.searchTBGoods(this,asc,keyword ,orderByType,page,type,this.bindToLifecycle());
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
                if (bean.getData().get(0).getGoodsType() == 3){
                    //淘宝没有数据时
                    dissRefresh();
                    TBSreachResultBean.DataBean headBean = new TBSreachResultBean.DataBean();
                    headBean.setGoodsType(4);
                    mList.add(headBean);
                    mList.addAll(bean.getData());
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
            }else {
                //京东、拼多多没有数据时
                dissRefresh();
                mList.clear();
                TBSreachResultBean.DataBean headBean = new TBSreachResultBean.DataBean();
                headBean.setGoodsType(5);
                mList.add(headBean);
                mList.addAll(bean.getData());
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
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (TextUtils.isEmpty(mList.get(position).getSource()) || mList.get(position).getSource().equals("2")){
            TaoBaoUtil.openTB(this, () -> {
                startActivity(new Intent(TBSreachResultActivity.this, ShareActivity.class)
                        .putExtra("otherGoodsId",mList.get(position).getOtherGoodsId())
                        .putExtra("source",mList.get(position).getSource())
                );
            });
        }else {
            startActivity(new Intent(this, ShareActivity.class)
                    .putExtra("otherGoodsId",mList.get(position).getOtherGoodsId())
                    .putExtra("source",mList.get(position).getSource())
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(int position) {
        mBinding.titleContainer.onPageSelected(position);
        mBinding.titleContainer.onPageScrolled(position, 0.0F, 0);
        if (position == 0){
            //搜淘宝
            type = "2";
        }else if (position == 1){//京东
            type = "1";
        }else {//拼多多
            type = "4";
        }
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        onRefresh();
    }
}
