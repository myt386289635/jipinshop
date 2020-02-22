package com.example.administrator.jipinshop.fragment.money

import android.content.Context
import android.widget.LinearLayout
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoneyPresenter {

    private var repository: Repository
    private lateinit var mView: MoneyView

    fun setView(view: MoneyView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun setDate(transformer: LifecycleTransformer<MoneyBean>){
        repository.hongbaoIndex()
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

    fun shareInfo(type :String , share_media: SHARE_MEDIA? ,transformer: LifecycleTransformer<ShareInfoBean>){
        repository.shareInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onPic(type,share_media,it)
                    }else{
                        mView.onCommenFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommenFile(it.message)
                })
    }

    fun openMoney(id : String ,set: Int , money : Int , transformer: LifecycleTransformer<SuccessBean>){
        repository.openMoney(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.open(set , money)
                    }else{
                        mView.onCommenFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommenFile(it.message)
                })
    }

    fun popInfo(transformer: LifecycleTransformer<MoneyPopBean>){
        repository.hongbaoPopInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.popInfo(it)
                    }else{
                        mView.onCommenFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommenFile(it.message)
                })
    }

    fun openAll(money: String, transformer: LifecycleTransformer<SuccessBean>){
        repository.openAllMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.openAll(money)
                    }else{
                        mView.onCommenFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommenFile(it.message)
                })
    }
}