package com.example.administrator.jipinshop.activity.home.classification.questions.detail;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityQuestionDetailBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.relativeLayout.FullScreenRelativeLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe
 */
public class QuestionDetailPresenter {

    private Repository mRepository;
    private QuestionDetailView mView;

    public void setView(QuestionDetailView view) {
        mView = view;
    }

    @Inject
    public QuestionDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 判断RecyclerView是否滑动到底部
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
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

    public void initEdit(ActivityQuestionDetailBinding binding){
        binding.detailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.detailTotalNum.setText( s.length() + "/500");
            }
        });
    }

    public void answerList(int page , String questionId, LifecycleTransformer<SucBean<QuestionsBean.DataBean.AnswerBean>> transformer){
        mRepository.answerList(page, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(answerBeanSucBean -> {
                    if (answerBeanSucBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(answerBeanSucBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(answerBeanSucBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void addAnswer(String content , String questionId , LifecycleTransformer<SuccessBean> transformer){
        mRepository.addAnswer(content, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessComment();
                        }
                    }else {
                        if (mView != null){
                            mView.onFileComment(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFileComment(throwable.getMessage());
                    }
                });
    }
}
