package com.example.administrator.jipinshop.activity.report.detail;

import android.graphics.Rect;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.FullScreenLinearLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/5/24
 * @Describe
 */
public class ReportDetailPresenter {

    private Repository mRepository;
    private ReportDetailView mView;

    public void setView(ReportDetailView view) {
        mView = view;
    }

    @Inject
    public ReportDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setKeyListener(final FullScreenLinearLayout mDetailContanier , final int[] usableHeightPrevious){
        mDetailContanier.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> possiblyResizeChildOfContent(mDetailContanier,usableHeightPrevious));
    }

    /****************监听软键盘的情况**********************/
    private void possiblyResizeChildOfContent(FullScreenLinearLayout mDetailContanier ,  int[] usableHeightPrevious) {
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

    private int computeUsableHeight(FullScreenLinearLayout mDetailContanier) {
        Rect r = new Rect();
        mDetailContanier.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
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
     * 获取数据
     */
    public void getDetail(String id, LifecycleTransformer<FindDetailBean> transformer){
        mRepository.reportDetail(id)
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

}
