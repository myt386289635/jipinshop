package com.example.administrator.jipinshop.activity.balance;

import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe
 */
public class MyWalletPresenter {

    private Repository mRepository;
    private MyWalletView mView;

    public void setView(MyWalletView view) {
        mView = view;
    }

    @Inject
    public MyWalletPresenter(Repository repository) {
        mRepository = repository;
    }

    public void myCommssionSummary(LifecycleTransformer<MyWalletBean> transformer){
//        mRepository.myCommssionSummary()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(transformer)
//                .subscribe(myWalletBean -> {
//                    if (myWalletBean.getCode() == 0){
//                        if (mView != null){
//                            mView.onSuccess(myWalletBean);
//                        }
//                    }else {
//                        if (mView != null){
//                            mView.onFile(myWalletBean.getMsg());
//                        }
//                    }
//                }, throwable -> {
//                    if (mView != null){
//                        mView.onFile(throwable.getMessage());
//                    }
//                });
    }

    public void addScore(String content, int score,Boolean scoreFlag,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addScore(content, score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (scoreFlag){
                            ToastUtil.show("感谢您的反馈");
                        }
                    }
                }, throwable -> {

                });
    }

    public void getScoreStatus(LifecycleTransformer<ScoreStatusBean> transformer){
        mRepository.getScoreStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(scoreStatusBean -> {
                    if (scoreStatusBean.getCode() == 0){
                        if (mView != null){
                            mView.onScoreSuc(scoreStatusBean);
                        }
                    }
                }, throwable -> {

                });
    }
}
