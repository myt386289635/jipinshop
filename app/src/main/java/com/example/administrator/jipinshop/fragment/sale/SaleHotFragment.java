package com.example.administrator.jipinshop.fragment.sale;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.adapter.KTTabAdapter;
import com.example.administrator.jipinshop.adapter.KTTabAdapter4;
import com.example.administrator.jipinshop.adapter.KTUserLikeAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBCategoryBean;
import com.example.administrator.jipinshop.databinding.FragmentSaleBinding;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/4/25
 * @Describe 热销页面
 */
public class SaleHotFragment extends DBBaseFragment implements View.OnClickListener, SaleHotView, KTTabAdapter4.OnItem, KTUserLikeAdapter.OnItem, OnLoadMoreListener, OnRefreshListener, KTTabAdapter.OnClickItem {

    @Inject
    SaleHotPresenter mPresenter;

    private FragmentSaleBinding mBinding;
    private int left = 0; //0是左边  1是右边  默认是0
    private String source = "2";//2是淘宝  1是京东  4是拼多多
    private int category = 0;//淘宝分类位置  默认是0
    private Dialog mDialog;
    private Boolean once = true;
    private int page = 1;
    private Boolean refresh = true;
    //tab
    private KTTabAdapter4 mTabAdapter;
    private List<String> titles;
    //列表
    private List<SimilerGoodsBean.DataBean> mList;
    private KTUserLikeAdapter mAdapter;
    //淘宝分类
    private List<TBCategoryBean.DataBean> mTabTBTitle;
    private KTTabAdapter mTabTBAdapter;

    public static SaleHotFragment getInstance(){
        return new SaleHotFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once){
            mBinding.swipeToLoad.post(() -> {
                mBinding.swipeToLoad.setRefreshing(true);
            });
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sale,container,false);
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        //选择tab
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        titles = new ArrayList<>();
        titles.add("淘宝");
        titles.add("京东");
        titles.add("拼多多");
        mTabAdapter = new KTTabAdapter4(titles);
        mTabAdapter.setOnClick(this);
        mTabAdapter.setColor(getResources().getColor(R.color.color_565252),
                getResources().getColor(R.color.color_E25838));
        mTabAdapter.setTextSize(16f);
        mTabAdapter.isBold(true);
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.titleContainer.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable(){
            @Override
            public int getIntrinsicWidth() {
                return (int) getResources().getDimension(R.dimen.x150);
            }
        });
        mBinding.titleContainer.onPageSelected(0);
        mBinding.titleContainer.onPageScrolled(0, 0.0F, 0);
        //淘宝分类tab
        mBinding.saleTbCategoryContainer.setVisibility(View.GONE);
        CommonNavigator tbNavigator = new CommonNavigator(getContext());
        tbNavigator.setLeftPadding((int) getResources().getDimension(R.dimen.x15));
        tbNavigator.setRightPadding((int) getResources().getDimension(R.dimen.x15));
        mTabTBTitle = new ArrayList<>();
        mTabTBAdapter = new KTTabAdapter(mTabTBTitle, this);
        tbNavigator.setAdapter(mTabTBAdapter);
        mBinding.saleTbCategory.setNavigator(tbNavigator);
        mBinding.saleTbCategory.onPageSelected(0);
        mBinding.saleTbCategory.onPageScrolled(0, 0.0F, 0);
        //列表
        mBinding.swipeTarget.setLayoutManager(new GridLayoutManager(getContext(),2));
        mList = new ArrayList<>();
        mAdapter = new KTUserLikeAdapter(mList,getContext());
        mAdapter.setOnItem(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sale_titleLeft:
                if (left != 0){
                    left = 0;
                    mPresenter.initTitle(getContext(),mBinding,left);
                }
                break;
            case R.id.sale_titleRight:
                if (left != 1){
                    left = 1;
                    mPresenter.initTitle(getContext(),mBinding,left);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        refresh = true;
        page = 1;
        mBinding.swipeTarget.scrollToPosition(0);
        if (source.equals("2")){
            //先请求tab
            mBinding.saleTbCategoryContainer.setVisibility(View.VISIBLE);
            mPresenter.topCategory(this.bindToLifecycle());
        }else {
            //请求列表
            mBinding.saleTbCategoryContainer.setVisibility(View.GONE);
            mPresenter.topGoodsList(mTabTBTitle.get(category).getCid(),page, (left+ 1) + "" , source,this.bindToLifecycle());
        }
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        mPresenter.topGoodsList(mTabTBTitle.get(category).getCid(),page, (left+ 1) + "" , source,this.bindToLifecycle());
    }


    @Override
    public void refresh() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        onRefresh();
    }

    @Override
    public void onSuccess(TBCategoryBean bean) {
        mTabTBTitle.clear();
        mTabTBTitle.addAll(bean.getData());
        mTabTBAdapter.notifyDataSetChanged();
        mPresenter.topGoodsList(mTabTBTitle.get(category).getCid(),page, (left+ 1) + "" , source,this.bindToLifecycle());
    }

    @Override
    public void onFile(String error) {
        stopRefresh();
        ToastUtil.show(error);
    }

    @Override
    public void onGoodsList(SimilerGoodsBean bean) {
        if(refresh) {
            stopRefresh();
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            dissLoading();
            if (bean.getData() != null && bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                page-- ;
                ToastUtil.show("已经是最后一页了");
            }
        }
        once = false;
    }

    @Override
    public void onGoodsFile(String error) {
        if(refresh){
            stopRefresh();
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    //停止刷新
    public void stopRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
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

    @Override
    public void onClick(int position) {
        mBinding.titleContainer.onPageSelected(position);
        mBinding.titleContainer.onPageScrolled(position, 0.0F, 0);
        if (position == 0){
            //搜淘宝
            source = "2";
        }else if (position == 1){//京东
            source = "1";
        }else {//拼多多
            source = "4";
        }
        refresh();
    }

    @Override
    public void onItemShare(int position) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (TextUtils.isEmpty(mList.get(position).getSource()) || mList.get(position).getSource().equals("2")){
            TaoBaoUtil.openTB(getContext(), () -> {
                startActivity(new Intent(getContext(), ShareActivity.class)
                        .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                        .putExtra("source",mList.get(position).getSource())
                );
            });
        }else{
            startActivity(new Intent(getContext(), ShareActivity.class)
                    .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                    .putExtra("source",mList.get(position).getSource())
            );
        }
    }

    @Override
    public void onMenu(int index) {
        mBinding.saleTbCategory.onPageSelected(index);
        mBinding.saleTbCategory.onPageScrolled(index, 0.0F, 0);
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        refresh = true;
        page = 1;
        category = index;
        mBinding.swipeTarget.scrollToPosition(0);
        //请求列表
        mPresenter.topGoodsList(mTabTBTitle.get(category).getCid(),page, (left+ 1) + "" , source,this.bindToLifecycle());
    }
}
