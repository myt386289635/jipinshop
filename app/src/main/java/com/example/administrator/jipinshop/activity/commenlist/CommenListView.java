package com.example.administrator.jipinshop.activity.commenlist;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2018/11/9
 * @Describe
 */
public interface CommenListView {
    void keyShow();
    void keyHint();

    void onSucComment(CommentBean commentBean);
    void onFileComment(String error);

    void onSucCommentInsert(SuccessBean commentInsertBean);
    void onFileCommentInsert(String error);

    void onSucCommentSnapIns(int position,VoteBean successBean);
    void onSucCommentSnapDel(int position,VoteBean successBean);
    void onFileSnap(String error);
}
