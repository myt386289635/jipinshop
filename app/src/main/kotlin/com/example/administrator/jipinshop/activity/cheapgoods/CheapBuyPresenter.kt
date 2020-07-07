package com.example.administrator.jipinshop.activity.cheapgoods

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe
 */
class CheapBuyPresenter {

    private var repository: Repository
    private lateinit var mView: CheapBuyView

    fun setView(view: CheapBuyView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun solveScoll(mRecyclerView: RecyclerView, mSwipeToLoad: SwipeToLoadLayout) {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManager : LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                mSwipeToLoad.isRefreshEnabled = (layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    fun setDate(transformer : LifecycleTransformer<NewPeopleBean>){
        repository.allowanceIndex()
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

    fun apply(allowanceGoodsId: String, transformer: LifecycleTransformer<ImageBean>) {
        repository.allowanceApply(allowanceGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.onBuySuccess(bean)
                    } else {
                        mView.onBuyFile(bean.msg)
                    }
                }, { throwable -> mView.onBuyFile(throwable.message) })
    }


    fun initShare(share_media: SHARE_MEDIA?, transformer: LifecycleTransformer<ShareInfoBean>) {
        repository.getShareInfo(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.initShare(share_media, bean)
                    } else {
                        mView.onBuyFile(bean.msg)
                    }
                }, { throwable -> mView.onBuyFile(throwable.message) })
    }
}