package com.example.administrator.jipinshop.activity.money.withdraw

import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 12:12
 * Description：
 */
class MoneyWithdrawPresenter {

    private var repository : Repository
    private lateinit var mView : MoneyWithdrawView

    fun setView(view : MoneyWithdrawView){
        mView = view
    }

    @Inject
    constructor(repository: Repository){
        this.repository = repository
    }

    fun withdraw( amount : String , transformer: LifecycleTransformer<SuccessBean>){
        repository.withdraw(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onSuccess()
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}