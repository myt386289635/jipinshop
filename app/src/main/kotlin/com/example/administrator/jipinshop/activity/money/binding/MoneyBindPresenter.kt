package com.example.administrator.jipinshop.activity.money.binding

import com.example.administrator.jipinshop.bean.SucBeanT
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe
 */
class MoneyBindPresenter {

    private var mRepository: Repository
    private lateinit var mView: MoneyBindView

    fun setView(view: MoneyBindView){
        mView = view
    }

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun alipayLogin(authCode : String ,transformer: LifecycleTransformer<SucBeanT<String>>){
        mRepository.alipayLogin(authCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onSuccess(it.data)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    fun bindingAlipay(realname : String ,transformer: LifecycleTransformer<SuccessBean>){
        mRepository.bindingAlipay(realname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.binding()
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    fun getAlipayAuthInfo(transformer: LifecycleTransformer<SucBeanT<String>>){
        mRepository.getAlipayAuthInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.info(it.data)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}