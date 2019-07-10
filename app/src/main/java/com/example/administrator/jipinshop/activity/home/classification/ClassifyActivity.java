package com.example.administrator.jipinshop.activity.home.classification;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.article.ArticleMoreActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.QuestionsActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.ClassifyAdapter;
import com.example.administrator.jipinshop.adapter.ClassifyTabAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ClassifyTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityClassifyBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/7/1
 * @Describe 小分类榜单
 */
public class ClassifyActivity extends BaseActivity implements View.OnClickListener, ClassifyTabAdapter.OnClickItem, ClassifyAdapter.OnClickView, ClassifyView {

    @Inject
    ClassifyPresenter mPresenter;

    private String title = "小分类榜单";//标题
    private String id = "";
    private ActivityClassifyBinding mBinding;
    private ClassifyAdapter mAdapter;
    private List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> mGoodsString;//商品列表数据
    private List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean> mPageList;//各种page榜单
    private List<TopCategoryDetailBean.DataBean.RelatedArticleListBean> mArticleList;//评测推荐列表
    private List<String> relatedQuestionList;//话题问答
    private List<String> relatedPediaList;//百科

    private List<ClassifyTabBean> mTitleList;//二级标题
    private ClassifyTabAdapter mTabAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int set = 0;// 记录上一个位置

    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_classify);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleLine.setVisibility(View.GONE);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        mBinding.inClude.titleTv.setText(title);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGoodsString = new ArrayList<>();
        mPageList = new ArrayList<>();
        mArticleList = new ArrayList<>();
        relatedQuestionList = new ArrayList<>();
        relatedPediaList = new ArrayList<>();
        mAdapter = new ClassifyAdapter(this);
        mAdapter.setGoodsString(mGoodsString);
        mAdapter.setPageList(mPageList);
        mAdapter.setArticleList(mArticleList);
        mAdapter.setRelatedQuestionList(relatedQuestionList);
        mAdapter.setRelatedPediaList(relatedPediaList);
        mAdapter.setView(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mTitleList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        mBinding.tabLayout.setLayoutManager(mLinearLayoutManager);
        mTabAdapter = new ClassifyTabAdapter(mTitleList,this);
        mTabAdapter.setOnClickItem(this);
        mBinding.tabLayout.setAdapter(mTabAdapter);

        mPresenter.getTopCategoryDetail(id,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onClickItem(int pos) {
        if (!mTitleList.get(pos).getTag()){
            final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            int des = 0;
            if((pos - 1) - firstPosition > 0){
                ////从左边到点击到右边
                des = mBinding.tabLayout.getChildAt((pos - 1) - firstPosition).getLeft();
                mBinding.tabLayout.smoothScrollBy(des,0);
            }else if(pos - 1 >= 0 && (pos - 1) - firstPosition <= 0){
                //从右边点击到左边
                mBinding.tabLayout.smoothScrollToPosition(pos - 1);
            }
            mTitleList.get(set).setTag(false);
            mTitleList.get(pos).setTag(true);
            mTabAdapter.notifyDataSetChanged();
            set = pos;
            //刷新
            mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
            mDialog.show();
            mPresenter.goodsListByOrderbyCategoryId(mTitleList.get(pos).getString().getOrderbyCategoryId(),this.bindToLifecycle());
        }
    }

    /**
     * 点击商品
     */
    @Override
    public void onClickGoods(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(this, ShoppingDetailActivity.class)
                    .putExtra("goodsId",mGoodsString.get(position).getGoodsId())
            );
        }
    }

    /**
     * 百科
     */
    @Override
    public void onEncyclopedias() {
        ToastUtil.show("百科");
    }

    /**
     * 话题回答
     */
    @Override
    public void onQuestions() {
        startActivity(new Intent(this, QuestionsActivity.class)
                .putExtra("title",title.replace("榜单","") + "问答区")
                .putExtra("id",id)
        );
    }

    /**
     * 评测文章
     */
    @Override
    public void onClickArticle(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            BigDecimal bigDecimal = new BigDecimal(mArticleList.get(position).getPv());
            mArticleList.get(position).setPv((bigDecimal.intValue() + 1));
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(this, ArticleDetailActivity.class)
                    .putExtra("id",mArticleList.get(position).getArticleId())
                    .putExtra("type",mArticleList.get(position).getType())
            );
        }
    }

    /**
     * 跳转评测更多列表
     */
    @Override
    public void onMore() {
        startActivity(new Intent(this, ArticleMoreActivity.class)
                .putExtra("title",title.replace("榜单","") + "评测推荐")
                .putExtra("id",id)
        );
    }

    @Override
    public void onSuccess(TopCategoryDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (bean.getData().getRelatedGoodsList().size() != 0){
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mGoodsString.addAll(bean.getData().getRelatedGoodsList());
            mPageList.addAll(bean.getData().getRelatedItemCategotyList());
            mArticleList.addAll(bean.getData().getRelatedArticleList());
            for (int i = 0; i < bean.getData().getRelatedItemList().size(); i++) {
                if (i == 0){
                    mTitleList.add(new ClassifyTabBean(bean.getData().getRelatedItemList().get(i),true));
                }else {
                    mTitleList.add(new ClassifyTabBean(bean.getData().getRelatedItemList().get(i),false));
                }
            }
            for (TopCategoryDetailBean.DataBean.RelatedQuestionListBean relatedQuestionListBean : bean.getData().getRelatedQuestionList()) {
                relatedQuestionList.add(relatedQuestionListBean.getTitle());
            }
            if (bean.getData().getRelatedPedia() != null && !TextUtils.isEmpty(bean.getData().getRelatedPedia().getSubtitle())){
                String[] pedia = bean.getData().getRelatedPedia().getSubtitle().split("\\|");
                for (int i = 0; i < pedia.length; i++) {
                    relatedPediaList.add(pedia[i]);
                }
            }
            mAdapter.setTitle(bean.getData().getCategoryName());
            mTabAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
        }else {
            mBinding.recyclerView.setVisibility(View.GONE);
            for (int i = 0; i < bean.getData().getRelatedItemList().size(); i++) {
                if (i == 0){
                    mTitleList.add(new ClassifyTabBean(bean.getData().getRelatedItemList().get(i),true));
                }else {
                    mTitleList.add(new ClassifyTabBean(bean.getData().getRelatedItemList().get(i),false));
                }
            }
            mTabAdapter.notifyDataSetChanged();
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.tabLayout.setVisibility(View.GONE);
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，重新开启页面试试");
    }

    @Override
    public void onSuccessSed(SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> bean) {
        mBinding.recyclerView.scrollToPosition(0);
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mGoodsString.clear();
        mGoodsString.addAll(bean.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFlieSed(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }
}
