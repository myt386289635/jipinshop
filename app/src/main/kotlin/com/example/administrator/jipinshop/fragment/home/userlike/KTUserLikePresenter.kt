package com.example.administrator.jipinshop.fragment.home.userlike

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.DeviceUuidFactory
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe
 */
class KTUserLikePresenter {

    private var repository: Repository
    private lateinit var mView: KTUserLikeView

    fun setView(view: KTUserLikeView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    //解决冲突问题
    fun solveScoll(mRecyclerView: RecyclerView, mSwipeToLoad: SwipeToLoadLayout, appBarLayout: AppBarLayout) {
        var layoutManager = mRecyclerView.layoutManager
        var linearManager = layoutManager as GridLayoutManager
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mSwipeToLoad.isRefreshEnabled = linearManager.findFirstCompletelyVisibleItemPosition() == 0
                mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
            }
        })
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                //展开
                mSwipeToLoad.isRefreshEnabled = linearManager.findFirstCompletelyVisibleItemPosition() == 0
                mSwipeToLoad.isLoadMoreEnabled = false
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                //折叠
                mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
                mSwipeToLoad.isRefreshEnabled = false
            } else {
                //过程
                mSwipeToLoad.isRefreshEnabled = false
                mSwipeToLoad.isLoadMoreEnabled = false
            }
        })
    }

    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    fun listSimilerGoods(context : Context,page : Int, transformer : LifecycleTransformer<SimilerGoodsBean> ){
        var map : MutableMap<String, String>? = DeviceUuidFactory.getIdfa(context)
        map?.put("page", "" + page)
        repository.listSimilerGoods(map)
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