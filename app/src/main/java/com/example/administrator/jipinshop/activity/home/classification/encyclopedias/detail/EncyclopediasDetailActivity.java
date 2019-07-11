package com.example.administrator.jipinshop.activity.home.classification.encyclopedias.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.ActivityEncyclopediasDetailBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/7/10
 * @Describe 百科详情页面
 */
public class EncyclopediasDetailActivity extends BaseActivity implements View.OnClickListener, EncyclopediasDetailView, ShoppingCommonAdapter.OnItemReply, ShoppingCommonAdapter.OnGoodItem, ShareBoardDialog.onShareListener {

    @Inject
    EncyclopediasDetailPresenter mPresenter;
    private ActivityEncyclopediasDetailBinding mBinding;
    private int[] usableHeightPrevious = {0};
    private Dialog mDialog;//加载框
    //用户评论
    private ShoppingCommonAdapter mCommonAdapter;
    private List<CommentBean.DataBean> mCommonList;

    private String attentionUserId = "";//评测用户id
    private boolean isCollect = false;//标志：是否收藏过此商品 false:没有
    private boolean isConcer = false;//标志文章作者是否被关注过
    private String shareTitle;
    private String shareContent;
    private String shareImg;
    private String shareUrl;
    private String parentId = "0";//父评论id
    private String toUserId = "0";//@回复的用户
    private ShareBoardDialog mShareBoardDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_encyclopedias_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();

        mBinding.inClude.titleTv.setText(getIntent().getStringExtra("title"));
        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);
        mPresenter.initWebView(mBinding.detailWeb);
        mBinding.detailWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //用户评论
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };
        mBinding.detailCommon.setLayoutManager(layoutManager1);
        mBinding.detailCommon.setFocusable(false);
        mCommonList = new ArrayList<>();
        mCommonAdapter = new ShoppingCommonAdapter(mCommonList, this);
        mCommonAdapter.setOnItemReply(this);
        mCommonAdapter.setOnGoodItem(this);
        mBinding.detailCommon.setAdapter(mCommonAdapter);

        mPresenter.getDetail(getIntent().getStringExtra("id"), this.bindToLifecycle());
        mPresenter.comment(getIntent().getStringExtra("id"), this.bindToLifecycle());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
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
            case R.id.detail_fovar:
                // TODO: 2019/7/10 收藏
                break;
            case R.id.detail_UserImage:
                // TODO: 2019/7/10 个人主页
                break;
            case R.id.detail_attention:
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                } else {
                    if (isConcer) {
                        //关注过了
                        mPresenter.concernDelete(attentionUserId, this.bindToLifecycle());
                    } else {
                        //没有关注
                        mPresenter.concernInsert(attentionUserId, this.bindToLifecycle());
                    }
                }
                break;
            case R.id.detail_commentTv:
            case R.id.detail_send:
                //评论
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                parentId = "0";
                toUserId = "0";
                mBinding.keyEdit.requestFocus();
                mBinding.keyEdit.setHint("回复楼层");
                showKeyboard(true);
                break;
            case R.id.detail_commonTotle:
                //跳转到评论列表
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position", -1)
                        .putExtra("id", getIntent().getStringExtra("id"))
                        .putExtra("type", "6")
                );
                break;
            case R.id.key_text:
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    ToastUtil.show("请输入评论");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.commentInsert(getIntent().getStringExtra("id"), toUserId, mBinding.keyEdit.getText().toString(), parentId, this.bindToLifecycle());
                break;
        }
    }

    public void hintKey() {
        if (mBinding.detailKeyLayout.getVisibility() == View.VISIBLE) {
            if (mImm.isActive()) {
                mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mBinding.detailKeyLayout.getVisibility() == View.VISIBLE && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = mBinding.detailKeyLayout;
            if (isHideInput(view, ev)) {
                hintKey();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof RelativeLayout)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyShow() {
        mBinding.detailBottom.setVisibility(View.INVISIBLE);
        mBinding.detailKeyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void keyHint() {
        mBinding.detailKeyLayout.setVisibility(View.GONE);
        mBinding.detailBottom.setVisibility(View.VISIBLE);
    }

    /**获取数据***/
    @Override
    public void onSuccess(FindDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        shareTitle =bean.getData().getShareTitle();
        shareContent = bean.getData().getShareContent();
        shareImg = bean.getData().getShareImg();
        shareUrl= bean.getData().getShareUrl();
        attentionUserId = bean.getData().getUserId();
        mBinding.detailTitle.setText(bean.getData().getTitle());
        mBinding.detailWeb.loadDataWithBaseURL(null, bean.getData().getContent(),
                "text/html", "utf-8", null);
        GlideApp.loderCircleImage(this, bean.getData().getUser().getAvatar(), mBinding.detailUserImage, R.mipmap.rlogo, 0);
        mBinding.detailUserName.setText(bean.getData().getUser().getNickname());
        mBinding.detailTime.setText(bean.getData().getCreateTime());
        if (bean.getData().getUser().getAuthentication() == 0) {
            //普通用户
            mBinding.itemGrade.setVisibility(View.GONE);
        } else if (bean.getData().getUser().getAuthentication() == 1) {
            //个人认证
            mBinding.itemGrade.setVisibility(View.VISIBLE);
            mBinding.itemGrade.setImageResource(R.mipmap.grade_peroson);
        } else {
            //企业认证
            mBinding.itemGrade.setVisibility(View.VISIBLE);
            mBinding.itemGrade.setImageResource(R.mipmap.grade_enterprise);
        }
        //是否收藏过
        if (bean.getData().getCollect() == 1) {
            isCollect = true;
            mBinding.detailFovar.setText("已收藏");
        } else {
            isCollect = false;
            mBinding.detailFovar.setText("收藏");
        }
        //是否关注过
        if (bean.getData().getUser().getFollow() == 0) {
            isConcer = false;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.detailAttention.setText("+关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_E31436));
        } else {
            isConcer = true;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.detailAttention.setText("已关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_white));
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
    }

    @Override
    public void onSucComment(CommentBean commentBean) {
        if (commentBean.getData() != null && commentBean.getData().size() != 0) {
            mBinding.detailCommon.setVisibility(View.VISIBLE);
            mBinding.detailCommonTotle.setVisibility(View.VISIBLE);
            mBinding.detailCommentTv.setVisibility(View.GONE);
            mBinding.detailSend.setVisibility(View.VISIBLE);
            mCommonList.clear();
            mCommonList.addAll(commentBean.getData());
            mCommonAdapter.notifyDataSetChanged();
            if (commentBean.getTotal() > 3) {
                mBinding.detailCommonTotle.setText("查看全部" + commentBean.getTotal() + "条评论");
            } else {
                mBinding.detailCommonTotle.setText("查看更多");
            }
        } else {
            mBinding.detailCommon.setVisibility(View.GONE);
            mBinding.detailCommonTotle.setVisibility(View.GONE);
            mBinding.detailCommentTv.setVisibility(View.VISIBLE);
            mBinding.detailSend.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFileComment(String error) {
        mBinding.detailCommon.setVisibility(View.GONE);
        mBinding.detailCommonTotle.setVisibility(View.GONE);
        mBinding.detailCommentTv.setVisibility(View.VISIBLE);
        mBinding.detailSend.setVisibility(View.GONE);
        ToastUtil.show(error);
    }

    /**获取数据结束***/

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onItemReply(int pos, TextView textView) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        parentId = mCommonList.get(pos).getCommentId();
        toUserId = "0";
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
        mBinding.keyEdit.setHint("回复" + mCommonList.get(pos).getUserNickname());
    }

    @Override
    public void onItemTwoReply(int pos) {
        //跳转到评论列表
        startActivity(new Intent(this, CommenListActivity.class)
                .putExtra("position", pos)
                .putExtra("id", getIntent().getStringExtra("id"))
                .putExtra("type", "6")
        );
    }

    @Override
    public void onGood(int flag, int position) {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        } else {
            if (flag == 1) {
                //取消点赞
                mPresenter.snapDelete(position, mCommonList.get(position).getCommentId(), this.bindToLifecycle());
            } else {
                //点赞
                mPresenter.snapInsert(position, mCommonList.get(position).getCommentId(), this.bindToLifecycle());
            }
        }
    }

    @Override
    public void onSucCommentInsert(SuccessBean successBean) {
        mPresenter.comment(getIntent().getStringExtra("id"),this.bindToLifecycle());
        mBinding.keyEdit.setText("");
        hintKey();
        if(!successBean.getMsg().equals("success")){
            ToastUtil.show(successBean.getMsg());
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onFileCommentInsert(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSucCommentSnapIns(int position, VoteBean successBean) {
        mCommonList.get(position).setVote(1);
        mCommonList.get(position).setVoteCount(successBean.getData());
        mCommonAdapter.notifyItemChanged(position);
        if(!successBean.getMsg().equals("success")){
            ToastUtil.show(successBean.getMsg());
        }
    }

    @Override
    public void onSucCommentSnapDel(int position, VoteBean successBean) {
        mCommonList.get(position).setVote(0);
        mCommonList.get(position).setVoteCount(successBean.getData());
        mCommonAdapter.notifyItemChanged(position);
    }

    @Override
    public void concerDelSuccess() {
        EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention,-1));
        isConcer = false;
        mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attention);
        mBinding.detailAttention.setText("+关注");
        mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_E31436));
    }

    @Override
    public void concerInsSuccess() {
        EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention,1));
        isConcer = true;
        mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attentioned);
        mBinding.detailAttention.setText("已关注");
        mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_white));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        if (mBinding.detailWeb != null) {
            ViewParent parent = mBinding.detailWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBinding.detailWeb);
            }
            mBinding.detailWeb.stopLoading();
            mBinding.detailWeb.getSettings().setJavaScriptEnabled(false);
            mBinding.detailWeb.clearHistory();
            mBinding.detailWeb.clearView();
            mBinding.detailWeb.removeAllViews();
            mBinding.detailWeb.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, shareUrl, shareTitle, shareContent, shareImg, R.mipmap.share_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //刷新评论列表
    @Subscribe
    public void commentResher(CommenBus commenBus){
        if(commenBus != null && commenBus.getTag().equals(CommenListActivity.commentResher)){
            mPresenter.comment(getIntent().getStringExtra("id"), this.bindToLifecycle());
        }
    }

}
