package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.view.View;

import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;

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

     void onSucIsCollect(SuccessBean successBean);
     void onFileIsCollect(String error);

     void onSucCollectInsert(SuccessBean successBean);
     void onFileCollectInsert(String error);

     void onSucCollectDelete(SuccessBean successBean);
     void onFileCollectDelete(String error);

     void onSucIsSnap(SnapSelectBean snapSelectBean);
     void onFileIsSnap(String error);

     void onSucSnapInsert(View view , SuccessBean successBean);

     void onSucSnapDelete(SuccessBean successBean);
}
