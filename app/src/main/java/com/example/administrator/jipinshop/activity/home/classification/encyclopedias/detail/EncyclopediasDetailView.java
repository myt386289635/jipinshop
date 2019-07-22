package com.example.administrator.jipinshop.activity.home.classification.encyclopedias.detail;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2019/7/10
 * @Describe
 */
public interface EncyclopediasDetailView {

    void keyShow();
    void keyHint();

    void onSuccess(FindDetailBean bean);
    void onFile(String error);

    void onSucComment(CommentBean commentBean);
    void onFileComment(String error);

    void onSucCommentInsert(SuccessBean successBean);

    void onFileCommentInsert(String error);//公共的取消回调

    void onSucCommentSnapIns(int position, VoteBean successBean);
    void onSucCommentSnapDel(int position,VoteBean successBean);

    void concerDelSuccess();
    void concerInsSuccess();

}
