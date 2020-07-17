package com.example.administrator.jipinshop.activity.minekt.recovery

import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TbOrderBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
class OrderRecoveryResultPesenter {

    private var repository: Repository
    private lateinit var mView: OrderRecoveryResultView

    @Inject
    constructor(Repository: Repository) {
        this.repository = Repository
    }

    fun setView(view: OrderRecoveryResultView){
        mView = view
    }

    //查询关联订单
    fun searchTbOrder(tradeId : String , transformer: LifecycleTransformer<TbOrderBean>){
        repository.searchTbOrder(tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onSuccess(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    //找回订单
    fun findBackTbOrder( tradeId :String , transformer: LifecycleTransformer<SuccessBean>){
        repository.findBackTbOrder(tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onFind()
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}