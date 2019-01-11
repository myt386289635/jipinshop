package com.example.administrator.jipinshop.activity.home.article;

import android.view.View;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe
 */
public interface ArticleDetailView {
    void onSuccess(FindDetailBean bean);
    void onFile(String error);

    void onSucComment(CommentBean commentBean);
    void onFileComment(String error);

    void onSucCollectInsert(SuccessBean successBean);
    void onSucCollectDelete(SuccessBean successBean);
    void onFileCollectDelete(String error);

    void onSucSnapInsert(VoteBean successBean);

    void onSucSnapDelete(VoteBean successBean);

    void onSucCommentInsert(SuccessBean successBean);
    void onFileCommentInsert(String error);

    void keyShow();
    void keyHint();

    void concerDelSuccess(SuccessBean successBean);
    void concerInsSuccess(SuccessBean successBean);

    void onSucCommentSnapIns(int position,VoteBean successBean);
    void onSucCommentSnapDel(int position,VoteBean successBean);
}
