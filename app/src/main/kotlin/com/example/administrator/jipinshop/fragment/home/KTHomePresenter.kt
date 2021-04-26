package com.example.administrator.jipinshop.fragment.home

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.jipinshop.bean.SucBeanT
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import q.rorbin.badgeview.QBadgeView
import javax.inject.Inject


/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
class KTHomePresenter {

    private var repository: Repository
    private lateinit var mView: KTHomeView

    fun setView(view: KTHomeView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun initBadgeView(mQBadgeView: QBadgeView, imageView: ImageView) {
        mQBadgeView.bindTarget(imageView)
                .setBadgeTextSize(8f, true)
                .setBadgeGravity(Gravity.END or Gravity.TOP)
                .setBadgePadding(2f, true)
                .setGravityOffset(3f, 3f, true)
                .setBadgeBackgroundColor(-0x51e41)
                .setBadgeTextColor(-0x1da7c8)
                .setOnDragStateChangedListener(null)
    }

    fun setStatusBarHight( StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun getIndexActivityInfo(transformer : LifecycleTransformer<SucBeanT<TbkIndexBean.DataBean.Ad1ListBean>>){
        repository.getIndexActivityInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onAction(it)
                    }else{
                        mView.onEndAction()
                    }
                }, Consumer {
                    mView.onEndAction()
                })
    }

    fun closeIndexMessage(transformer : LifecycleTransformer<SuccessBean>){
        repository.closeIndexMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {}, Consumer {})
    }
}