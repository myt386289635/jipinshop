package com.example.administrator.jipinshop.activity.home.classification.encyclopedias;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.classification.encyclopedias.detail.EncyclopediasDetailActivity;
import com.example.administrator.jipinshop.adapter.EncyclopediasAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityEncyclopediasBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/7/10
 * @Describe 百科首页
 */
public class EncyclopediasActivity extends BaseActivity implements View.OnClickListener, EncyclopediasAdapter.OnClickRelatedItem, EncyclopediasView {

    @Inject
    EncyclopediasPresenter mPresenter;
    private ActivityEncyclopediasBinding mBinding;
    private String articleId = "";
    private List<FindDetailBean.DataBean.RelatedArticleListBean> mArticleListBeans;
    private EncyclopediasAdapter mArticleAdapter;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_encyclopedias);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra("title"));
        articleId = getIntent().getStringExtra("id");

        mPresenter.initWebView(mBinding.webView);
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mBinding.recyclerView.setFocusable(false);
        mArticleListBeans = new ArrayList<>();
        mArticleAdapter = new EncyclopediasAdapter(mArticleListBeans,this);
        mArticleAdapter.setOnClickRelatedItem(this);
        mBinding.recyclerView.setAdapter(mArticleAdapter);

        mPresenter.getDetail(articleId,this.bindToLifecycle());
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
    public void onClickItem(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            BigDecimal bigDecimal = new BigDecimal(mArticleListBeans.get(position).getPv());
            mArticleListBeans.get(position).setPv((bigDecimal.intValue() + 1));
            mArticleAdapter.notifyDataSetChanged();
            startActivity(new Intent(this, EncyclopediasDetailActivity.class)
                    .putExtra("title",getIntent().getStringExtra("title"))
                    .putExtra("id",mArticleListBeans.get(position).getArticleId())
            );
        }
    }

    @Override
    public void onSuccess(FindDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.webView.loadDataWithBaseURL(null, bean.getData().getContent(),
                "text/html", "utf-8", null);
        if (bean.getData().getRelatedArticleList() == null || bean.getData().getRelatedArticleList().size() == 0){
            mBinding.detailRelatedNoDate.setVisibility(View.VISIBLE);
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            mBinding.detailRelatedNoDate.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mArticleListBeans.addAll(bean.getData().getRelatedArticleList());
            mArticleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onDestroy() {
        //防止webView内存溺出
        if (mBinding.webView != null) {
            ViewParent parent = mBinding.webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.webView);
            }
            mBinding.webView.stopLoading();
            mBinding.webView.getSettings().setJavaScriptEnabled(false);
            mBinding.webView.clearHistory();
            mBinding.webView.clearView();
            mBinding.webView.removeAllViews();
            mBinding.webView.destroy();
        }
        super.onDestroy();
    }
}
