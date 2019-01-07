package com.example.administrator.jipinshop.activity.home.evaluation;

import android.view.View;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.EvaluationDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2018/11/16
 * @Describe
 */
public interface EvaluationDetailView {

    void onSuccess(EvaluationDetailBean bean);
    void onFile(String error);

    void onSucIsCollect(SnapSelectBean successBean);
    void onFileIsCollect(String error);

    void onSucIsSnap(SnapSelectBean snapSelectBean);
    void onFileIsSnap(String error);

    void onSucComment(CommentBean commentBean);
    void onFileComment(String error);

    void onSucCollectInsert(SuccessBean successBean);
    void onSucCollectDelete(SuccessBean successBean);
    void onFileCollectDelete(String error);

    void onSucSnapInsert(View view , VoteBean successBean);

    void onSucSnapDelete(SuccessBean successBean);
}
