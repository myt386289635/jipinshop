package com.example.administrator.jipinshop.fragment.sale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.TBCategoryBean;
import com.example.administrator.jipinshop.databinding.FragmentSaleBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2021/4/25
 * @Describe
 */
public class SaleHotPresenter {

    private Repository mRepository;
    private SaleHotView mView;

    public void setView(SaleHotView view) {
        mView = view;
    }

    @Inject
    public SaleHotPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initTitle(Context context,FragmentSaleBinding mBinding , int left ){
        if (left == 0){
            mBinding.saleTitleLeft.setBackgroundResource(R.drawable.bg_e25838_8);
            mBinding.saleTitleLeft.setTextColor(context.getResources().getColor(R.color.color_white));
            Drawable drawable = context.getResources().getDrawable(R.mipmap.sale_1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.color_white));
            mBinding.saleTitleLeft.setCompoundDrawables(drawable,null,null,null);
            mBinding.saleTitleRight.setBackgroundResource(R.drawable.bg_e25838_90);
            mBinding.saleTitleRight.setTextColor(context.getResources().getColor(R.color.color_E25838));
        }else {
            mBinding.saleTitleLeft.setBackgroundResource(R.drawable.bg_e25838_9);
            mBinding.saleTitleLeft.setTextColor(context.getResources().getColor(R.color.color_E25838));
            Drawable drawable = context.getResources().getDrawable(R.mipmap.sale_1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.color_E25838));
            mBinding.saleTitleLeft.setCompoundDrawables(drawable,null,null,null);
            mBinding.saleTitleRight.setBackgroundResource(R.drawable.bg_e25838_4);
            mBinding.saleTitleRight.setTextColor(context.getResources().getColor(R.color.color_white));
        }
        mView.refresh();
    }

    public void topCategory(LifecycleTransformer<TBCategoryBean> transformer){
        mRepository.topCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void topGoodsList(String cid , int page,  String saleType, String source
            , LifecycleTransformer<SimilerGoodsBean> transformer){
        HashMap<String,String> map  = new HashMap<>();
        map.put("page", page + "");
        map.put("saleType" , saleType);
        map.put("source", source);
        if (!TextUtils.isEmpty(cid)){
            map.put("cid",cid);
        }
        mRepository.topGoodsList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onGoodsList(bean);
                    }else {
                        mView.onGoodsFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onGoodsFile(throwable.getMessage());
                });
    }
}
