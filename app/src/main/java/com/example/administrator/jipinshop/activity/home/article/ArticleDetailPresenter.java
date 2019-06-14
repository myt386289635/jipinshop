package com.example.administrator.jipinshop.activity.home.article;

import android.graphics.Rect;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.relativeLayout.FullScreenRelativeLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/31
 * @Describe
 */
public class ArticleDetailPresenter {

    private Repository mRepository;
    private ArticleDetailView mView;

    public void setView(ArticleDetailView view) {
        mView = view;
    }

    @Inject
    public ArticleDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    public void setKeyListener(final FullScreenRelativeLayout mDetailContanier , final int[] usableHeightPrevious){
        mDetailContanier.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> possiblyResizeChildOfContent(mDetailContanier,usableHeightPrevious));
    }

    /****************监听软键盘的情况**********************/
    private void possiblyResizeChildOfContent(FullScreenRelativeLayout mDetailContanier , int[] usableHeightPrevious) {
        int usableHeightNow = computeUsableHeight(mDetailContanier);
        if (usableHeightNow != usableHeightPrevious[0]) {
            int usableHeightSansKeyboard = mDetailContanier.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 键盘弹出
                mView.keyShow();
            } else {
                // 键盘收起
                mView.keyHint();
            }
            mDetailContanier.requestLayout();
            usableHeightPrevious[0] = usableHeightNow;
        }
    }

    private int computeUsableHeight(FullScreenRelativeLayout mDetailContanier) {
        Rect r = new Rect();
        mDetailContanier.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    /**
     * 获取数据
     */
    public void getDetail(String id,String type, LifecycleTransformer<FindDetailBean> transformer){
        mRepository.findDetail(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if(bean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }


    /**
     * 查看评论列表
     */
    public void comment(String goodsId,String type, LifecycleTransformer<CommentBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type",type);
        hashMap.put("targetId",goodsId);
        mRepository.comment(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commentBean -> {
                    if(commentBean.getCode() == 0){
                        if(mView != null){
                            mView.onSucComment(commentBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileComment(commentBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileComment(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加评论
     */
    public void commentInsert(String type,String targetId,String toUserId,String content,String parentId , LifecycleTransformer<SuccessBean> transformer){
        mRepository.commentInsert(targetId,toUserId,content,parentId,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSucCommentInsert(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCommentInsert(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCommentInsert(throwable.getMessage());
                    }
                });
    }


    /**
     * 添加收藏
     */
    public void collectInsert(String goodsId , String type,LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("targetId",goodsId);
        mRepository.collectInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602) {
                        if (mView != null) {
                            mView.onSucCollectInsert(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除收藏
     */
    public void collectDelete(String goodsId , String type,LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("targetId",goodsId);
        mRepository.collectDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602) {
                        if (mView != null) {
                            mView.onSucCollectDelete(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加点赞
     */
    public void snapInsert(int position,String type, String id , LifecycleTransformer<VoteBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("targetId",id);
        mRepository.snapInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602){
                        if(type.equals("5")){
                            if(mView != null){
                                mView.onSucCommentSnapIns(position,successBean);
                            }
                        }else {
                            if(mView != null){
                                mView.onSucSnapInsert(successBean);
                            }
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除点赞
     */
    public void snapDelete(int position,String id ,String type, LifecycleTransformer<VoteBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("targetId",id);
        mRepository.snapDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602){
                        if(type.equals("5")){
                            if(mView != null){
                                mView.onSucCommentSnapDel(position,successBean);
                            }
                        }else {
                            if(mView != null){
                                mView.onSucSnapDelete(successBean);
                            }
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 取消关注
     */
    public void concernDelete(String attentionUserId, LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602){
                        if(mView  != null){
                            mView.concerDelSuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加关注
     */
    public void concernInsert(String attentionUserId,LifecycleTransformer<SuccessBean> transformer){
        mRepository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0 || successBean.getCode() == 602){
                        if(mView  != null){
                            mView.concerInsSuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 登陆后查询页面状态
     */
    public void pagerState(String type ,String targetId, LifecycleTransformer<PagerStateBean> transformer){
        mRepository.pagerState(type,targetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(pagerStateBean -> {
                    if(pagerStateBean.getCode() == 0){
                        if(mView  != null){
                            mView.pagerStateSuccess(pagerStateBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(pagerStateBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 阅读获取极币
     */
    public void taskFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("6")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> {
                    if(taskFinishBean.getCode() == 0){
                        ToastUtil.show(taskFinishBean.getMsg());
                    }
                }, throwable ->{

                });
    }

    /**
     * 分享获取极币
     */
    public void taskshareFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> {

                }, throwable ->{

                });
    }

    /**
     * 获取专属淘宝购买链接地址
     */
    public void goodsBuyLink(String goodsId, LifecycleTransformer<ImageBean> transformer){
        mRepository.goodsBuyLink(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(imageBean -> {
                    if (imageBean.getCode() == 0){
                        if (mView != null){
                            mView.onBuyLinkSuccess(imageBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCommentInsert(imageBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCommentInsert(throwable.getMessage());
                    }
                });
    }
}
