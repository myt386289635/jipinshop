package com.example.administrator.jipinshop.activity.home.evaluation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.EvaluationDetailBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/11/14
 * @Describe
 */
public class EvaluationDetailPresenter {

    private Repository mRepository;
    private EvaluationDetailView mView;

    public void setView(EvaluationDetailView view) {
        mView = view;
    }

    @Inject
    public EvaluationDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 状态栏
     */
    public void setStatusBarHight(LinearLayout StatusBar , ImageView imageView, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams1.topMargin = statusBarHeight;
        }
    }

    public void initWebView(WebView mWebView){
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用支持javascript
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
    }

    public void initDate(String evalWayId ,  LifecycleTransformer<EvaluationDetailBean> transformer){
        mRepository.evaluationDetail(evalWayId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(evaluationDetailBean -> {
                    if(evaluationDetailBean.getCode() == 200){
                        if (mView != null) {
                            mView.onSuccess(evaluationDetailBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(evaluationDetailBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 查询文章是否被收藏过
     */
    public void isCollect(String goodsId , LifecycleTransformer<SnapSelectBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",goodsId);
        mRepository.isCollect(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(mView != null){
                        mView.onSucIsCollect(successBean);
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileIsCollect(throwable.toString());
                    }
                });
    }

    /**
     * 查询文章是否被点赞过
     */
    public void snapSelect(String goodsId , LifecycleTransformer<SnapSelectBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",goodsId);
        mRepository.snapSelect(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(snapSelectBean -> {
                    if(mView != null){
                        mView.onSucIsSnap(snapSelectBean);
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileIsSnap(throwable.getMessage());
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
                        if(mView != null){
                            mView.onSucComment(commentBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileComment(commentBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileComment(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加收藏
     */
    public void collectInsert(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",goodsId);
        mRepository.collectInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200) {
                        if (mView != null) {
                            mView.onSucCollectInsert(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除收藏
     */
    public void collectDelete(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",goodsId);
        mRepository.collectDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200) {
                        if (mView != null) {
                            mView.onSucCollectDelete(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 添加点赞
     */
    public void snapInsert(View view , String id , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",id);
        mRepository.snapInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucSnapInsert(view,successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除点赞
     */
    public void snapDelete(String id , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
        hashMap.put("evalId",id);
        mRepository.snapDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucSnapDelete(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileCollectDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileCollectDelete(throwable.getMessage());
                    }
                });
    }

}
