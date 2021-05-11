package com.example.administrator.jipinshop.activity.sreach;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.relativeLayout.FullScreenRelativeLayout;
import com.google.android.flexbox.FlexboxLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/12/2
 * @Describe
 */
public class TBSreachPresenter {

    Repository mRepository;
    private TBSreachView mView;

    public void setView(TBSreachView view) {
        mView = view;
    }

    @Inject
    public TBSreachPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHot(final Context context, final FlexboxLayout flexboxLayout, List<SreachHistoryBean.DataBean.HotWordListBean> hotText) {
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy2, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            textView.setText(hotText.get(i).getWord());
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump(textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void initHistroy(final Context context, final FlexboxLayout flexboxLayout, List<SreachHistoryBean.DataBean.LogListBean> hotText) {
        flexboxLayout.removeAllViews();
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy2, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            textView.setText(hotText.get(i).getWord());
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump(textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
        flexboxLayout.post(() -> {
            ViewGroup.LayoutParams layoutParams =  flexboxLayout.getLayoutParams();
            if (flexboxLayout.getFlexLines().size() > 3){
                layoutParams.height = (int) context.getResources().getDimension(R.dimen.y265);
            }else {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            flexboxLayout.setLayoutParams(layoutParams);
        });

    }


    /**
     * 获取搜索记录
     */
    public void searchLog(String type ,LifecycleTransformer<SreachHistoryBean> transformer){
        mRepository.searchLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachHistoryBean -> {
                    if(sreachHistoryBean.getCode() == 0){
                        if(type.equals("1")){
                            if(mView != null){
                                mView.Success(sreachHistoryBean);
                            }
                        }else {
                            if(mView != null){
                                mView.SuccessHistory(sreachHistoryBean);
                            }
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(sreachHistoryBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除所有搜索记录
     */
    public void deleteAll(LifecycleTransformer<SuccessBean> transformer){
        mRepository.searchDeleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.SuccessDeleteAll(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 猜你喜欢
     */
    public void listSimilerGoods(Map<String,String> map, LifecycleTransformer<SimilerGoodsBean> transformer){
        Map<String,String> parament = new HashMap<>();
        parament.putAll(map);
        mRepository.listSimilerGoods(parament)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.LikeSuccess(bean);
                    }else {
                        mView.onFaile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFaile(throwable.getMessage());
                });
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

}
