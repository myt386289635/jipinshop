package com.example.administrator.jipinshop.activity.shoppingdetail;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommentInsertBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.FullScreenLinearLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe
 */
public class ShoppingDetailPresenter {

    private Repository mRepository;
    private ShoppingDetailView mShoppingDetailView;

    public void setShoppingDetailView(ShoppingDetailView shoppingDetailView) {
        mShoppingDetailView = shoppingDetailView;
    }

    @Inject
    public ShoppingDetailPresenter(Repository repository) {
        mRepository = repository;
    }


    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point,LinearLayout mDetailPoint, ShoppingBannerAdapter mBannerAdapter){
        for (int i = 0; i < mBannerList.size(); i++) {
            ImageView imageView = new ImageView(context);

            point.add(imageView);

            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);

            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);

            mDetailPoint.addView(imageView, layoutParams);
        }
        mBannerAdapter.notifyDataSetChanged();
    }


    public void initTitleLayout( Context context ,TextView showTv, View showVi , TextView hintTv1,View hintVi1 , TextView hintTv2,View hintVi2 ){
        showTv.setTextColor(context.getResources().getColor(R.color.color_DE050505));
        showVi.setVisibility(View.VISIBLE);
        hintTv1.setTextColor(context.getResources().getColor(R.color.color_DEACACAC));
        hintVi1.setVisibility(View.INVISIBLE);
        hintTv2.setTextColor(context.getResources().getColor(R.color.color_DEACACAC));
        hintVi2.setVisibility(View.INVISIBLE);
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
                mShoppingDetailView.keyShow();
            } else {
                // 键盘收起
                mShoppingDetailView.keyHint();
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

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    public void getDate(String goodsId , LifecycleTransformer<ShoppingDetailBean> transformer){
        mRepository.goodsRankDetailList(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(shoppingDetailBean -> {
                    mShoppingDetailView.onSuccess(shoppingDetailBean);
                }, throwable -> mShoppingDetailView.onFile(throwable.getMessage()));
    }


    /**
     * 查询商品是否被收藏过
     */
    public void isCollect(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("projectId",goodsId);
        mRepository.isCollect(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onSucIsCollect(successBean);
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileIsCollect(throwable.toString());
                    }
                });
    }

    /**
     * 添加收藏
     */
    public void collectInsert(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("project_id",goodsId);
        mRepository.collectInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200) {
                        if (mShoppingDetailView != null) {
                            mShoppingDetailView.onSucCollectInsert(successBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除收藏
     */
    public void collectDelete(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("projectId",goodsId);
        mRepository.collectDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200) {
                        if (mShoppingDetailView != null) {
                            mShoppingDetailView.onSucCollectDelete(successBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }


    /**
     * 优化标题跟踪长度
     */
    public void initLine(LinearLayout layout , TextView textView , View shopView,View evaluationView,View commonView){
        int a = (int) textView.getPaint().measureText(textView.getText().toString());
        layout.post(() -> {
            int content = layout.getWidth() / 3;
            int dp10 = (content - a) / 2;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) shopView.getLayoutParams();
            params.leftMargin = dp10;
            params.rightMargin = dp10;
            shopView.setLayoutParams(params);
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) evaluationView.getLayoutParams();
            params1.leftMargin = dp10;
            params1.rightMargin = dp10;
            evaluationView.setLayoutParams(params1);
            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) commonView.getLayoutParams();
            params2.leftMargin = dp10;
            params2.rightMargin = dp10;
            commonView.setLayoutParams(params2);
        });
    }

    /**
     * 查询商品是否被点赞过
     */
    public void snapSelect(String goodsId , LifecycleTransformer<SnapSelectBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("goodsId",goodsId);
        mRepository.snapSelect(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(snapSelectBean -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onSucIsSnap(snapSelectBean);
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileIsSnap(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加点赞
     */
    public void snapInsert(View view , String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("goodsId",goodsId);
        mRepository.snapInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onSucSnapInsert(view,successBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除点赞
     */
    public void snapDelete(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("goodsId",goodsId);
        mRepository.snapDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onSucSnapDelete(successBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 查看评论列表
     */
    public void comment(String goodsId, LifecycleTransformer<CommentBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("articId",goodsId);
        mRepository.comment(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commentBean -> {
                    if(commentBean.getCode() == 200){
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onSucComment(commentBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileComment(commentBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileComment(throwable.getMessage());
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
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onSucCommentInsert(successBean);
                        }
                    }else {
                        if(mShoppingDetailView != null){
                            mShoppingDetailView.onFileCommentInsert(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mShoppingDetailView != null){
                        mShoppingDetailView.onFileCommentInsert(throwable.getMessage());
                    }
                });
    }
}
