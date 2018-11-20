package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;

import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignPresenter {

    private Repository mRepository;
    private SignView mView;

    public void setSignView(SignView signView) {
        mView = signView;
    }

    @Inject
    public SignPresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 查询7天签到状态
     * @param transformer
     */
    public void signInfo(LifecycleTransformer<SignBean> transformer){
        mRepository.sign()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signBean -> {
                    if(signBean.getCode() == 200){
                        if(mView != null){
                            mView.getInfoSuc(signBean);
                        }
                    }else {
                        if(mView != null){
                            mView.getInfoFaile(signBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mView != null){
                        mView.getInfoFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 签到
     * @param transformer
     */
    public void sign(Dialog dialog,LifecycleTransformer<SignInsertBean> transformer){
        mRepository.signInsert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signInsertBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(signInsertBean.getCode() == 200){
                        if(mView != null){
                            mView.signSuc(signInsertBean);
                        }
                    }else {
                        if(mView != null){
                            mView.signFaile(signInsertBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView != null){
                        mView.signFaile(throwable.getMessage());
                    }
                });
    }


    /**
     * 补签
     */
    public void Supplement(Dialog dialog,LifecycleTransformer<SupplementBean> transformer){
        mRepository.Supplement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(supplementBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(supplementBean.getCode() == 200){
                        if(mView != null){
                            mView.SuppleSuc(supplementBean);
                        }
                    }else {
                        if(supplementBean.getCode() == 641){
                            if(mView != null){
                                mView.SuppleFaile("641");
                            }
                        }else {
                            if(mView != null){
                                mView.SuppleFaile(supplementBean.getMsg());
                            }
                        }
                    }

                }, throwable -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView != null){
                        mView.SuppleFaile(throwable.getMessage());
                    }
                });

    }

    /**
     * 抽奖
     */
    public void luckselect(Dialog dialogLuck,LifecycleTransformer<LuckselectBean> transformer){
        mRepository.luckselect()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(luckselectBean -> {
                    if(dialogLuck != null && dialogLuck.isShowing()){
                        dialogLuck.dismiss();
                    }
                    if(luckselectBean.getCode() == 200){
                        if(mView != null){
                            mView.LuckSuc(luckselectBean);
                        }
                    }else {
                        if(luckselectBean.getCode() == 642){
                            if(mView != null){
                                mView.LuckFaile("642");
                            }
                        }else {
                            if(mView != null){
                                mView.LuckFaile(luckselectBean.getMsg());
                            }
                        }
                    }
                }, throwable -> {
                    if(dialogLuck != null && dialogLuck.isShowing()){
                        dialogLuck.dismiss();
                    }
                    if(mView != null){
                        mView.LuckFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 初始化抽奖图片
     */
    public void luckImage(LifecycleTransformer<LuckImageBean> transformer){
        mRepository.luckselects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(luckImageBean -> {
                    if(luckImageBean.getCode() == 200){
                        if(mView != null){
                            mView.LuckImageSuc(luckImageBean);
                        }
                    }else {
                        if(mView != null){
                            mView.LuckImageFaile(luckImageBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.LuckImageFaile(throwable.getMessage());
                    }
                });
    }
}
