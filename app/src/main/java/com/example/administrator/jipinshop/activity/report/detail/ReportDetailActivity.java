package com.example.administrator.jipinshop.activity.report.detail;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity;
import com.example.administrator.jipinshop.adapter.RelatedArticleAdapter;
import com.example.administrator.jipinshop.adapter.ReportDetailAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommonAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.ActivityReportDetailBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.foval.find.FovalFindFragment;
import com.example.administrator.jipinshop.fragment.foval.tryout.FovalTryFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.RelatedGoodsDialog;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/5/24
 * @Describe json详情页书写
 */
public class ReportDetailActivity extends BaseActivity implements View.OnClickListener, ShoppingCommonAdapter.OnItemReply, ShoppingCommonAdapter.OnGoodItem, ReportDetailView, ShareBoardDialog.onShareListener, RelatedGoodsDialog.OnItem, RelatedArticleAdapter.OnClickRelatedItem {

    @Inject
    ReportDetailPresenter mPresenter;
    private ActivityReportDetailBinding mBinding;
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
    /**
     * 父评论id
     */
    private String parentId = "0";
    private String toUserId = "0";//@回复的用户

    //用户评论
    private ShoppingCommonAdapter mCommonAdapter;
    private List<CommentBean.DataBean> mCommonList;
    private int[] usableHeightPrevious = {0};
    private String attentionUserId = "";//评测用户id

    //相关推荐
    private List<FindDetailBean.DataBean.RelatedArticleListBean> mArticleListBeans;
    private RelatedArticleAdapter mArticleAdapter;

    private Handler.Callback mCallback = msg -> {
       if (msg.what == 100){
            //阅读获取极币
            mPresenter.taskFinish(this.bindToLifecycle());
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
    private String ShareBoardTitle = "";
    private String ShareBoardContent = "";

    private ArrayList<FindDetailBean.DataBean.RelatedGoodsListBean> mBeans;
    //商品详情
    private ReportDetailAdapter mRVAdapter;
    private List<TryDetailBean.DataBean.GoodsContentListBean> mList;

    private String type  = "";
    private String articleId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_report_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.setView(this);
        type = getIntent().getStringExtra("type");
        articleId = getIntent().getStringExtra("id");
//        if (type.equals("4")){
//            mBinding.titleTv.setText("试用报告");
//        }else {
//            mBinding.titleTv.setText("清单详情");
//        }

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mRVAdapter = new ReportDetailAdapter(this,mList);
        mBinding.recyclerView.setAdapter(mRVAdapter);

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

        //相关推荐
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        };
        mBinding.detailRelated.setLayoutManager(layoutManager);
        mBinding.detailRelated.setFocusable(false);
        mArticleListBeans = new ArrayList<>();
        mArticleAdapter = new RelatedArticleAdapter(mArticleListBeans,this);
        mArticleAdapter.setOnClickRelatedItem(this);
        mBinding.detailRelated.setAdapter(mArticleAdapter);

        mBinding.scrollView.setOnScrollListener((scrollY) -> {
            if (mBinding.scrollView.getScrollY() >= mBinding.recyclerView.getTop()) {
//                mBinding.titleTv.setVisibility(View.GONE);
                mBinding.headContanier.setVisibility(View.VISIBLE);
            } else {
//                mBinding.titleTv.setVisibility(View.VISIBLE);
                mBinding.headContanier.setVisibility(View.GONE);
            }
        });

        mBeans = new ArrayList<>();

        mPresenter.getDetail(articleId, type, this.bindToLifecycle());
        mPresenter.comment(articleId,type, this.bindToLifecycle());

        handler.sendEmptyMessageDelayed(100,1000 * 15);//阅读15秒
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AlibcTradeSDK.destory();
        UMShareAPI.get(this).release();
        handler.removeCallbacksAndMessages(null);
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
                    mShareBoardDialog = ShareBoardDialog.getInstance(ShareBoardTitle,ShareBoardContent);
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_buy:
                if(mBeans != null && mBeans.size() != 0 && mBeans.size() > 1){
                    //购买
                    if (mRelatedGoodsDialog == null) {
                        mRelatedGoodsDialog = RelatedGoodsDialog.getInstance(mBeans);
                        mRelatedGoodsDialog.setOnItem(this);
                    }
                    if (!mRelatedGoodsDialog.isAdded()) {
                        mRelatedGoodsDialog.show(getSupportFragmentManager(), "RelatedGoodsDialog");
                    }
                }
                break;
            case R.id.detail_buyOne:
                if (mBeans != null && mBeans.size() != 0 && mBeans.size() == 1){
                    //直接购买
                    if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                        startActivity(new Intent(this, LoginActivity.class));
                        return;
                    }
                    TaoBaoUtil.openTB(this, () -> {
                        mDialog = (new ProgressDialogView()).createOtherDialog(this,"淘宝",R.mipmap.dialog_tb);
                        mDialog.show();
                        mPresenter.goodsBuyLink(mBeans.get(0).getGoodsId(),this.bindToLifecycle());
                    });
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
                        mPresenter.snapDelete(0, articleId, type, this.bindToLifecycle());
                    } else {
                        //没有点赞
                        mPresenter.snapInsert(0, type, articleId, this.bindToLifecycle());
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
                        mPresenter.collectDelete(articleId, type, this.bindToLifecycle());
                    } else {
                        //没有收藏
                        mPresenter.collectInsert(articleId, type, this.bindToLifecycle());
                    }
                }
                break;
            case R.id.head_userImage:
            case R.id.detail_UserImage:
                //跳转到个人主页
                startActivity(new Intent(this, UserActivity.class)
                        .putExtra("userid",attentionUserId)
                );
                break;
            case R.id.key_text:
                //发送按钮
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    ToastUtil.show("请输入评论");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.commentInsert(type, articleId, toUserId, mBinding.keyEdit.getText().toString(), parentId, this.bindToLifecycle());
                break;
            case R.id.detail_commonTotle:
                //跳转到评论列表
                startActivity(new Intent(this, CommenListActivity.class)
                        .putExtra("position", -1)
                        .putExtra("id", articleId)
                        .putExtra("type", type)
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
        MobLinkUtil.mobShare(articleId, "/listing2", mobID -> {
            if (!TextUtils.isEmpty(mobID)){
                shareUrl += "&mobid=" + mobID;
            }
            mPresenter.taskshareFinish(this.bindUntilEvent(ActivityEvent.DESTROY));
            if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
                String path = "pages/ev/ev-info/main?evListVal=" + articleId + "&type=" + type;
                new ShareUtils(this, share_media)
                        .shareWXMin1(this, shareUrl,shareImg, shareTitle, shareContent, path);
            } else {
                new ShareUtils(this, share_media)
                        .shareWeb(this, shareUrl, shareTitle, shareContent, shareImg, R.mipmap.share_logo);
            }
        });
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
                .putExtra("id", articleId)
                .putExtra("type", type)
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

    @Override
    public void keyShow() {
        mBinding.detailBottom.setVisibility(View.INVISIBLE);
        mBinding.detailBottom2.setVisibility(View.INVISIBLE);
        mBinding.detailKeyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void keyHint() {
        mBinding.detailKeyLayout.setVisibility(View.GONE);
        mBinding.detailBottom.setVisibility(View.VISIBLE);
        if (mBeans == null || mBeans.size() == 0){
            mBinding.detailBottom.setVisibility(View.VISIBLE);
            mBinding.detailBottom2.setVisibility(View.GONE);
        }else if (mBeans.size() > 1){
            mBinding.detailBottom2.setVisibility(View.GONE);
            mBinding.detailBottom.setVisibility(View.VISIBLE);
        }else if (mBeans.size() == 1){
            mBinding.detailBottom2.setVisibility(View.VISIBLE);
            mBinding.detailBottom.setVisibility(View.INVISIBLE);
        }
    }

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

    @Override
    public void onFileComment(String error) {
        mBinding.detailCommon.setVisibility(View.GONE);
        mBinding.detailCommonTotle.setVisibility(View.GONE);
        mBinding.detailCommentLayout.setVisibility(View.VISIBLE);
        mBinding.detailSend.setVisibility(View.GONE);
        ToastUtil.show(error);
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
            if(!successBean.getMsg().equals("success")){
                ToastUtil.show(successBean.getMsg());
            }
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
     * 添加点赞成功回调
     */
    @Override
    public void onSucSnapInsert(VoteBean successBean) {
        if (successBean.getCode() == 0) {
            isSnap = true;
            mBinding.detailGood.setText(successBean.getData() + "");
            Drawable drawable = getResources().getDrawable(R.mipmap.question_good);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_like);
            if(!successBean.getMsg().equals("success")){
                ToastUtil.show(successBean.getMsg());
            }
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
            Drawable drawable = getResources().getDrawable(R.mipmap.question_ungood);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable, null, null, null);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }
    /**
     * 添加收藏成功回调
     */
    @Override
    public void onSucCollectInsert(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            if (type.equals("4")){
                EventBus.getDefault().post(FovalTryFragment.CollectResher);//刷新我的收藏列表
            }else {
                EventBus.getDefault().post(FovalFindFragment.CollectResher);//刷新我的收藏列表
            }
            isCollect = true;
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_red);
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
            if (type.equals("4")){
                EventBus.getDefault().post(FovalTryFragment.CollectResher);//刷新我的收藏列表
            }else {
                EventBus.getDefault().post(FovalFindFragment.CollectResher);//刷新我的收藏列表
            }
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_break);
        } else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
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
     * 添加评论成功
     */
    @Override
    public void onSucCommentInsert(SuccessBean successBean) {
        mPresenter.comment(articleId, type, this.bindToLifecycle());
        mBinding.keyEdit.setText("");
        hintKey();
        if(!successBean.getMsg().equals("success")){
            ToastUtil.show(successBean.getMsg());
        }
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
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_red);
        }else {
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_break);
        }
        if(pagerStateBean.getVote() == 1){
            isSnap = true;
            Drawable drawable= getResources().getDrawable(R.mipmap.question_good);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_like);
        }else {
            isSnap = false;
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_ACACAC));
            mBinding.detailGood.setBackgroundResource(R.drawable.bg_nolike);
            Drawable drawable= getResources().getDrawable(R.mipmap.question_ungood);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBinding.detailGood.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void onSuccess(FindDetailBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ShareBoardTitle = "一边分享  一边赚";
        ShareBoardContent = bean.getContent();
        shareTitle =bean.getData().getShareTitle();
        shareContent = bean.getData().getShareContent();
        shareImg = bean.getData().getShareImg();
        shareUrl= bean.getData().getShareUrl();
        if (bean.getData().getRelatedGoodsList() != null){
            mBeans.addAll(bean.getData().getRelatedGoodsList());
        }
        attentionUserId = bean.getData().getUserId();
        if(bean.getData().getRelatedGoodsList() == null || bean.getData().getRelatedGoodsList().size() == 0){
            mBinding.detailBottom.setVisibility(View.VISIBLE);
            mBinding.detailBuy.setText("暂无商品");
            ShareBoardTitle = "分享";
            ShareBoardContent = "";
        }else if (bean.getData().getRelatedGoodsList().size() > 1){
            mBinding.detailBottom2.setVisibility(View.GONE);
            mBinding.detailBottom.setVisibility(View.VISIBLE);
        }else if (bean.getData().getRelatedGoodsList().size() == 1){
            if (bean.getFee() != 0){
                mBinding.detailFee.setVisibility(View.VISIBLE);
                mBinding.detailFee.setText("补贴 ¥" + bean.getFee());
            }else {
                mBinding.detailFee.setVisibility(View.GONE);
            }
            if (!bean.getData().getRelatedGoodsList().get(0).getCouponPrice().equals("0")){
                mBinding.detailCoupon.setVisibility(View.VISIBLE);
                mBinding.detailCoupon.setText("优惠券 ¥" + bean.getData().getRelatedGoodsList().get(0).getCouponPrice());
            }else {
                mBinding.detailCoupon.setVisibility(View.GONE);
            }
            if (bean.getFee() == 0 && bean.getData().getRelatedGoodsList().get(0).getCouponPrice().equals("0")){
                mBinding.detailOtherPrice.setVisibility(View.GONE);
            }else {
                mBinding.detailOtherPrice.setVisibility(View.VISIBLE);
                mBinding.detailOtherPrice.setTv(true);
                mBinding.detailOtherPrice.setColor(R.color.color_ACACAC);
                mBinding.detailOtherPrice.setText("¥" +bean.getData().getRelatedGoodsList().get(0).getOtherPrice());
            }
            mBinding.detailActualPrice.setText("¥" +bean.getData().getRelatedGoodsList().get(0).getBuyPrice());
            mBinding.detailBottom2.setVisibility(View.VISIBLE);
            mBinding.detailBottom.setVisibility(View.INVISIBLE);
        }
        mBinding.detailTitle.setText(bean.getData().getTitle());
        mList.clear();
        mList.addAll(new Gson().fromJson(bean.getData().getContent(),new TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>(){}.getType()));
        mRVAdapter.notifyDataSetChanged();
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
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_red);
        } else {
            isCollect = false;
            mBinding.bottomFavor.setImageResource(R.mipmap.collect_break);
        }
        //是否点赞过
        if (bean.getData().getVote() == 1) {
            isSnap = true;
            mBinding.detailGood.setText(bean.getData().getVoteCount() + "");
            mBinding.detailGood.setTextColor(getResources().getColor(R.color.color_E31436));
            Drawable drawable = getResources().getDrawable(R.mipmap.question_good);
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
            Drawable drawable = getResources().getDrawable(R.mipmap.question_ungood);
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
        //相关推荐
        if (bean.getData().getRelatedArticleList() == null || bean.getData().getRelatedArticleList().size() == 0){
            mBinding.detailRelatedNoDate.setVisibility(View.VISIBLE);
            mBinding.detailRelated.setVisibility(View.GONE);
        }else {
            mBinding.detailRelatedNoDate.setVisibility(View.GONE);
            mBinding.detailRelated.setVisibility(View.VISIBLE);
            mArticleListBeans.addAll(bean.getData().getRelatedArticleList());
            mArticleAdapter.notifyDataSetChanged();
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
    public void onBuyLinkSuccess(ImageBean bean) {
        TaoBaoUtil.openAliHomeWeb(this,bean.getData(),bean.getOtherGoodsId());
    }

    /**
     * 跳转到淘宝
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        TaoBaoUtil.openTB(this, () -> {
            mDialog = (new ProgressDialogView()).createOtherDialog(this,"淘宝",R.mipmap.dialog_tb);
            mDialog.show();
            mPresenter.goodsBuyLink(mBeans.get(position).getGoodsId(),this.bindToLifecycle());
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
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
            mPresenter.pagerState(type,articleId,this.bindToLifecycle());
            mPresenter.comment(articleId, type, this.bindToLifecycle());
        }
    }

    //获取评论列表_试用报告详情
    @Subscribe
    public void commentResher(CommenBus commenBus){
        if(commenBus != null && commenBus.getTag().equals(CommenListActivity.commentResher)){
            mPresenter.comment(articleId, type, this.bindToLifecycle());
        }
    }


    /**
     * 相关商品跳转到文章详情页面
     * @param position
     */
    @Override
    public void onClickItem(int position) {
        ShopJumpUtil.jumpArticle(this,mArticleListBeans.get(position).getArticleId(),
                mArticleListBeans.get(position).getType(),mArticleListBeans.get(position).getContentType());
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


}
