package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.view.View;

import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public interface ShoppingDetailView {
     void keyShow();
     void keyHint();

     void onSuccess(ShoppingDetailBean recommendFragmentBean);
     void onFile(String error);

     void onSucCollectInsert(SuccessBean successBean);

     void onSucCollectDelete(SuccessBean successBean);
     void onFileCollectDelete(String error);

     void onSucSnapInsert(View view , VoteBean successBean);

     void onSucSnapDelete(VoteBean successBean);

     void onSucComment(CommentBean commentBean);
     void onFileComment(String error);

     void onSucCommentInsert(SuccessBean successBean);
     void onFileCommentInsert(String error);

     void onSucCommentSnapIns(int position,VoteBean successBean);
     void onSucCommentSnapDel(int position,VoteBean successBean);

     void concerDelSuccess(SuccessBean successBean);
     void concerInsSuccess(SuccessBean successBean);

     void pagerStateSuccess(PagerStateBean pagerStateBean);
}
