package com.example.administrator.jipinshop.activity.report.detail;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2019/5/24
 * @Describe
 */
public interface ReportDetailView {

    void keyShow();
    void keyHint();

    void onSucComment(CommentBean commentBean);
    void onFileComment(String error);

    void onSucCommentSnapIns(int position,VoteBean successBean);
    void onSucCommentSnapDel(int position,VoteBean successBean);
    void onSucSnapInsert(VoteBean successBean);
    void onFileCollectDelete(String error);
    void onSucSnapDelete(VoteBean successBean);

    void onSucCollectInsert(SuccessBean successBean);
    void onSucCollectDelete(SuccessBean successBean);

    void concerDelSuccess(SuccessBean successBean);
    void concerInsSuccess(SuccessBean successBean);

    void onSucCommentInsert(SuccessBean successBean);
    void onFileCommentInsert(String error);

    void pagerStateSuccess(PagerStateBean pagerStateBean);

    void onSuccess(FindDetailBean bean);
    void onFile(String error);

    void onBuyLinkSuccess(ImageBean bean);
}
