package com.example.administrator.jipinshop.activity.commenlist;

import android.graphics.Rect;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommentInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.FullScreenLinearLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public class CommenListPresenter {

    private Repository mRepository;
    private CommenListView mView;

    public void setView(CommenListView view) {
        mView = view;
    }

    @Inject
    public CommenListPresenter(Repository repository) {
        mRepository = repository;
    }


    /**
     * 查看评论列表
     */
    public void comment(String page ,String goodsId, LifecycleTransformer<CommentBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("articId",goodsId);
        hashMap.put("page",page);
        mRepository.comment(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commentBean -> {
                    if(commentBean.getCode() == 200){
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
    public void commentInsert(String articId,String content,String parentId , LifecycleTransformer<CommentInsertBean> transformer){
        mRepository.commentInsert(articId,content,parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commentInsertBean -> {
                    if(commentInsertBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucCommentInsert(commentInsertBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCommentInsert(commentInsertBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCommentInsert(throwable.getMessage());
                    }
                });
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
     * 添加点赞
     */
    public void snapInsert(int position, String id , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("commentId",id);
        mRepository.snapInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucCommentSnapIns(position,successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileSnap(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileSnap(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除点赞
     */
    public void snapDelete(int position, String id , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("commentId",id);
        mRepository.snapDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucCommentSnapDel(position,successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileSnap(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileSnap(throwable.getMessage());
                    }
                });
    }

}
