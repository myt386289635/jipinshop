package com.example.administrator.jipinshop.activity.home.tb

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.DeviceUuidFactory
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/6/12
 * @Describe
 */
class KTTBDetailPresenter {

    private var mRepository: Repository
    private lateinit var mView: KTTBDetailView

    fun setView(view: KTTBDetailView){
        mView = view
    }

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun solveScoll(recyclerView: RecyclerView, swipeToLoadLayout: SwipeToLoadLayout){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManager : LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                swipeToLoadLayout.isRefreshEnabled = (layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                swipeToLoadLayout.isLoadMoreEnabled = isSlideToBottom(recyclerView)
            }
        })
    }

    fun isSlideToBottom(recyclerView: RecyclerView?) : Boolean{
        if (recyclerView == null){
            return false
        }
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true
        return false
    }

    fun listSimilerGoods(context : Context, page : Int, transformer : LifecycleTransformer<SimilerGoodsBean>){
        var map : MutableMap<String, String>? = DeviceUuidFactory.getIdfa(context)
        map?.put("page", "" + page)
        mRepository.listSimilerGoods(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.onSuccess(bean)
                    } else {
                        mView.onFile(bean.msg)
                    }
                }, { throwable -> mView.onFile(throwable.message) })
    }
}