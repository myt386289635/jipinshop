package com.example.administrator.jipinshop.activity.home.article;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.EvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.FindBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.ActivityFindDetailBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.RelatedGoodsDialog;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/30
 * @Describe 文章详情  2评测, 3发现
 */
@SuppressWarnings("all")
public class ArticleDetailActivity extends BaseActivity implements View.OnClickListener, ShareBoardDialog.onShareListener, ArticleDetailView, ShoppingCommonAdapter.OnItemReply, ShoppingCommonAdapter.OnGoodItem, RelatedGoodsDialog.OnItem {

    @Inject
    ArticleDetailPresenter mPresenter;
    private ActivityFindDetailBinding mBinding;

    private Dialog mDialog;//加载框
    private ShareBoardDialog mShareBoardDialog;
    private RelatedGoodsDialog mRelatedGoodsDialog;
    /**
     * 标志：是否点赞过此商品  false:没有
     */
    private boolean isSnap = false;
    /**
     * 标志：是否收藏过此商品 false:没有
     */
    private boolean isCollect = false;
    /**
     * 标志文章作者是否被关注过
     */
    private boolean isConcer = false;

    //用户评论
    private ShoppingCommonAdapter mCommonAdapter;
    private List<CommentBean.DataBean> mCommonList;
    private int[] usableHeightPrevious = {0};
    private String attentionUserId = "";//评测用户id

    /**
     * 父评论id
     */
    private String parentId = "0";
    private String toUserId = "0";//@回复的用户

    private Handler.Callback mCallback = msg -> {
        if (msg.what == 1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailWeb.getLayoutParams();
            layoutParams.height = (int) (msg.arg1 * getResources().getDisplayMetrics().density);
            mBinding.detailWeb.setLayoutParams(layoutParams);
        }
        return true;
    };
    private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    /**
     * 分享的东西
     */
    private String shareTitle;
    private String shareContent;
    private String shareImg;
    private String shareUrl;

    private ArrayList<FindDetailBean.DataBean.RelatedGoodsListBean> mBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(ArticleDetailActivity.this, "正在加载...");
        mDialog.show();

        mPresenter.setView(this);
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
                mBinding.detailWeb.loadUrl("javascript:window.android.getBodyHeight(document.body.scrollHeight)");
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
        //监听软键盘的弹出与收回
        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);

        mBinding.scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getScrollY() >= mBinding.detailWeb.getTop()) {
                mBinding.titleTv.setVisibility(View.GONE);
                mBinding.headContanier.setVisibility(View.VISIBLE);
            } else {
                mBinding.titleTv.setVisibility(View.VISIBLE);
                mBinding.headContanier.setVisibility(View.GONE);
            }
        });

        if(getIntent().getStringExtra("type").equals("2")){
            mBinding.titleTv.setText("评测详情");
        }else {
            mBinding.titleTv.setText("发现详情");
        }
        mBeans = new ArrayList<>();

        mPresenter.getDetail(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
        mPresenter.comment(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AlibcTradeSDK.destory();
        UMShareAPI.get(this).release();
        handler.removeCallbacksAndMessages(null);
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
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.detail_buy:
                //购买
                if (mRelatedGoodsDialog == null) {
                    mRelatedGoodsDialog = RelatedGoodsDialog.getInstance(mBeans);
                    mRelatedGoodsDialog.setOnItem(this);
                }
                if (!mRelatedGoodsDialog.isAdded()) {
                    mRelatedGoodsDialog.show(getSupportFragmentManager(), "RelatedGoodsDialog");
                }
                break;
            case R.id.detail_send:
            case R.id.detail_commentTv:
                //评论
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                parentId = "0";
                toUserId = "0";
                mBinding.keyEdit.requestFocus();
                showKeyboard(true);
                break;
            case R.id.detail_good:
                //喜欢
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                } else {
                    if (isSnap) {
                        //点赞过了
                        mPresenter.snapDelete(0, getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
                    } else {
                        //没有点赞
                        mPresenter.snapInsert(0, getIntent().getStringExtra("type"), getIntent().getStringExtra("id"), this.bindToLifecycle());
                    }
                }
                break;
            case R.id.head_attention:
            case R.id.detail_attention:
                //关注
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
            case R.id.bottom_favor:
                //收藏
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                } else {
                    if (isCollect) {
                        //收藏过了
                        mPresenter.collectDelete(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
                    } else {
                        //没有收藏
                        mPresenter.collectInsert(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
                    }
                }
                break;
            case R.id.head_userImage:
            case R.id.detail_UserImage:
                //跳转到个人主页
                // TODO: 2019/1/11 个人主页
                break;
            case R.id.key_text:
                //发送按钮
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    ToastUtil.show("请输入评论");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.commentInsert(getIntent().getStringExtra("type"), getIntent().getStringExtra("id"), toUserId, mBinding.keyEdit.getText().toString(), parentId, this.bindToLifecycle());
                break;
            case R.id.detail_commonTotle:
                //跳转到评论列表
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position", -1)
                        .putExtra("id", getIntent().getStringExtra("id"))
                        .putExtra("type", getIntent().getStringExtra("type"))
                );
                break;
        }
    }

    /**
     * 分享
     *
     * @param share_media
     */
    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(this, share_media)
                .shareWeb(this, shareUrl, shareTitle, shareContent, shareImg, R.mipmap.share_logo);
    }

    /**
     * 获取详情页数据
     */
    @Override
    public void onSuccess(FindDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        shareTitle =bean.getData().getShareTitle();
        shareContent = bean.getData().getShareContent();
        shareImg = bean.getData().getShareImg();
        shareUrl= bean.getData().getShareUrl();
        mBeans.addAll(bean.getData().getRelatedGoodsList());
        attentionUserId = bean.getData().getUserId();
        mBinding.detailTitle.setText(bean.getData().getTitle());
        mBinding.detailWeb.loadDataWithBaseURL(null, bean.getData().getContent(),
                "text/html", "utf-8", null);
        GlideApp.loderCircleImage(this, bean.getData().getUser().getAvatar(), mBinding.detailUserImage, R.mipmap.rlogo, 0);
        GlideApp.loderCircleImage(this, bean.getData().getUser().getAvatar(), mBinding.headUserImage, R.mipmap.rlogo, 0);
        mBinding.headUserName.setText(bean.getData().getUser().getNickname());
        mBinding.detailUserName.setText(bean.getData().getUser().getNickname());
        mBinding.detailTime.setText(bean.getData().getCreateTime());
        if (bean.getData().getUser().getAuthentication() == 0) {
            //普通用户
            mBinding.itemGrade.setVisibility(View.GONE);
            mBinding.headGrade.setVisibility(View.GONE);
        } else if (bean.getData().getUser().getAuthentication() == 1) {
            //个人认证
            mBinding.itemGrade.setVisibility(View.VISIBLE);
            mBinding.itemGrade.setImageResource(R.mipmap.grade_peroson);
            mBinding.headGrade.setVisibility(View.VISIBLE);
            mBinding.headGrade.setImageResource(R.mipmap.grade_peroson);
        } else {
            //企业认证
            mBinding.itemGrade.setVisibility(View.VISIBLE);
            mBinding.itemGrade.setImageResource(R.mipmap.grade_enterprise);
            mBinding.headGrade.setVisibility(View.VISIBLE);
            mBinding.headGrade.setImageResource(R.mipmap.grade_enterprise);
        }
        //是否收藏过
        if (bean.getData().getCollect() == 1) {
            isCollect = true;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_sel);
        } else {
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
        }
        //是否点赞过
        if (bean.getData().getVote() == 1) {
            isSnap = true;
            mBinding.detailGood.setText(bean.getData().getVoteCount() + "");
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            Drawable drawable = getResources().getDrawable(R.mipmap.like_sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_like);
        } else {
            isSnap = false;
            if (bean.getData().getVoteCount() != 0) {
                mBinding.detailGood.setText(bean.getData().getVoteCount() + "");
            } else {
                mBinding.detailGood.setText("喜欢");
            }
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_nolike);
            Drawable drawable = getResources().getDrawable(R.mipmap.like_nor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
        }
        //是否关注过
        if (bean.getData().getUser().getFollow() == 0) {
            isConcer = false;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.detailAttention.setText("+关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.headAttention.setText("+关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_E31436));
        } else {
            isConcer = true;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.detailAttention.setText("已关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_white));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.headAttention.setText("已关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_white));
        }
    }

    /**
     * 获取详情页数据
     */
    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
    }

    /**
     * 获取评论列表成功
     */
    @Override
    public void onSucComment(CommentBean commentBean) {
        if (commentBean.getData() != null && commentBean.getData().size() != 0) {
            mBinding.detailCommon.setVisibility(View.VISIBLE);
            mBinding.detailCommonTotle.setVisibility(View.VISIBLE);
            mBinding.detailCommentLayout.setVisibility(View.GONE);
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
            mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
            mBinding.detailSend.setVisibility(View.GONE);
        }
    }

    /**
     * 获取评论列表失败
     */
    @Override
    public void onFileComment(String error) {
        mBinding.detailCommon.setVisibility(View.GONE);
        mBinding.detailCommonTotle.setVisibility(View.GONE);
        mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
        mBinding.detailSend.setVisibility(View.GONE);
        ToastUtil.show(error);
    }

    /**
     * 添加收藏成功回调
     */
    @Override
    public void onSucCollectInsert(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            EventBus.getDefault().post(FovalArticleFragment.CollectResher);//刷新我的收藏列表
            isCollect = true;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_sel);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 删除收藏成功回调
     */
    @Override
    public void onSucCollectDelete(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            EventBus.getDefault().post(FovalArticleFragment.CollectResher);//刷新我的收藏列表
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.tab_favor_nor);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 失败回调
     */
    @Override
    public void onFileCollectDelete(String error) {
        ToastUtil.show(error);
    }

    /**
     * 添加点赞成功回调
     */
    @Override
    public void onSucSnapInsert(VoteBean successBean) {
        if (successBean.getCode() == 0) {
            isSnap = true;
            mBinding.detailGood.setText(successBean.getData() + "");
            Drawable drawable = getResources().getDrawable(R.mipmap.like_sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_like);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 删除点赞成功回调
     */
    @Override
    public void onSucSnapDelete(VoteBean successBean) {
        if (successBean.getCode() == 0) {
            isSnap = false;
            if (successBean.getData() != 0) {
                mBinding.detailGood.setText(successBean.getData() + "");
            } else {
                mBinding.detailGood.setText("喜欢");
            }
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_nolike);
            Drawable drawable = getResources().getDrawable(R.mipmap.like_nor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 添加评论成功
     */
    @Override
    public void onSucCommentInsert(SuccessBean successBean) {
        mPresenter.comment(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
        mBinding.keyEdit.setText("");
        hintKey();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 添加评论失败
     */
    @Override
    public void onFileCommentInsert(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
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

    /**
     * 删除关注成功
     */
    @Override
    public void concerDelSuccess(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention,-1));
            isConcer = false;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.detailAttention.setText("+关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.headAttention.setText("+关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_E31436));
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 添加关注成功
     */
    @Override
    public void concerInsSuccess(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            EventBus.getDefault().post(new FollowBus(AttentionFragment.refreshAttention,1));
            isConcer = true;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.detailAttention.setText("已关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_white));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.headAttention.setText("已关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_white));
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 评论点赞成功
     */
    @Override
    public void onSucCommentSnapIns(int position, VoteBean successBean) {
        if (successBean.getCode() == 0) {
            mCommonList.get(position).setVote(1);
            mCommonList.get(position).setVoteCount(successBean.getData());
            mCommonAdapter.notifyItemChanged(position);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 评论点赞删除
     */
    @Override
    public void onSucCommentSnapDel(int position, VoteBean successBean) {
        if (successBean.getCode() == 0) {
            mCommonList.get(position).setVote(0);
            mCommonList.get(position).setVoteCount(successBean.getData());
            mCommonAdapter.notifyItemChanged(position);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 登陆后改变状态
     */
    @Override
    public void pagerStateSuccess(PagerStateBean pagerStateBean) {
        if (pagerStateBean.getFollow() == 0) {
            isConcer = false;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.detailAttention.setText("+关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attention);
            mBinding.headAttention.setText("+关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_E31436));
        }else {
            isConcer = true;
            mBinding.detailAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.detailAttention.setText("已关注");
            mBinding.detailAttention.setTextColor(getResources().getColor(R.color.color_white));
            mBinding.headAttention.setBackgroundResource(R.drawable.bg_attentioned);
            mBinding.headAttention.setText("已关注");
            mBinding.headAttention.setTextColor(getResources().getColor(R.color.color_white));
        }
        if(pagerStateBean.getCollect() == 1){
            isCollect = true;
            mBinding.bottomFavor.setImageResource(R.mipmap.score_sel);
        }else {
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.nav_favor);
        }
        if(pagerStateBean.getVote() == 1){
            isSnap = true;
            Drawable drawable= getResources().getDrawable(R.mipmap.like_sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_like);
        }else {
            isSnap = false;
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_nolike);
            Drawable drawable= getResources().getDrawable(R.mipmap.like_nor);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }
    }

    /**
     * 点击一级回复
     */
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

    /**
     * 点击二级评论更多按钮
     */
    @Override
    public void onItemTwoReply(int pos) {
        //跳转到评论列表
        startActivity(new Intent(this, CommenListActivity.class)
                .putExtra("position", pos)
                .putExtra("id", getIntent().getStringExtra("id"))
                .putExtra("type", getIntent().getStringExtra("type"))
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
                mPresenter.snapDelete(position, mCommonList.get(position).getCommentId(), "5", this.bindToLifecycle());
            } else {
                //点赞
                mPresenter.snapInsert(position, "5", mCommonList.get(position).getCommentId(), this.bindToLifecycle());
            }
        }
    }

    /**
     * 跳转到淘宝
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        openAliHomeWeb(mBeans.get(position).getGoodsBuyLink());
    }


    public class WebViewJavaScriptFunction {
        /**
         * 高度
         */
        @JavascriptInterface
        public void getBodyHeight(String number) {
            int webViewHeight = Integer.parseInt(number.split("[.]")[0]);
            Log.e("lgqqqqq======", "webViewHeight" + webViewHeight);

            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = webViewHeight;
            handler.sendMessage(msg);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    /****
     * 跳转淘宝首页
     */
    public void openAliHomeWeb(String url) {
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");

        //yhhpass参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        AlibcTrade.show(this, new AlibcPage(url), alibcShowParams, null, exParams, new AlibcTradeCallback() {
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
                ToastUtil.show("进去了");
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
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    //获取评论列表——发现详情
    @Subscribe
    public void commentResher(FindBus findBus) {
        if (findBus != null) {
            mPresenter.comment(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
        }
    }

    //获取评论列表_评测详情
    @Subscribe
    public void commentResher(EvaluationBus evaluationBus) {
        if (evaluationBus != null) {
            mPresenter.comment(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
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

    /**
     * 登陆时刷新 关注 收藏 点赞 评论
     */
    @Subscribe
    public void refreshPage(CommonEvaluationBus commonEvaluationBus){
        if(commonEvaluationBus != null && commonEvaluationBus.getRefersh().equals(LoginActivity.refresh)){
            mPresenter.pagerState(getIntent().getStringExtra("type"),getIntent().getStringExtra("id"),this.bindToLifecycle());
            mPresenter.comment(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"), this.bindToLifecycle());
        }
    }
}
