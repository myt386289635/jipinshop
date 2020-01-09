package com.example.administrator.jipinshop.activity.minekt.welfare

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.WelfareBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/9
 * @Describe
 */
class WelfarePresenter {

    private var repository: Repository
    private lateinit var mView: WelfareView

    fun setView(view: WelfareView){
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

    fun getDate(transformer: LifecycleTransformer<WelfareBean>){
        repository.welfareList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onSuccess(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

}