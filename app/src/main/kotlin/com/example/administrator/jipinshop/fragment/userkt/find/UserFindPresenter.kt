package com.example.administrator.jipinshop.fragment.userkt.find

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/19
 * @Describe
 */
class UserFindPresenter {

    private var repository: Repository
    private lateinit var mView: UserFindView

    fun setView(view: UserFindView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    //解决冲突问题
    fun solveScoll(mRecyclerView: RecyclerView, mSwipeToLoad: SwipeToLoadLayout,
                   appBarLayout: AppBarLayout, once: Array<Boolean>) {
        val layoutManager = mRecyclerView.layoutManager
        val linearManager = layoutManager as LinearLayoutManager?
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mSwipeToLoad.isRefreshEnabled = linearManager!!.findFirstCompletelyVisibleItemPosition() == 0
                mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout1, verticalOffset ->
            if (once[0] || mRecyclerView.visibility == View.GONE) {
                mSwipeToLoad.isRefreshEnabled = true
                if (mRecyclerView.visibility == View.GONE) {
                    mSwipeToLoad.isLoadMoreEnabled = false
                    when {
                        verticalOffset == 0 -> mSwipeToLoad.isRefreshEnabled = true
                        Math.abs(verticalOffset) >= appBarLayout1.totalScrollRange -> mSwipeToLoad.isRefreshEnabled = false
                        else -> mSwipeToLoad.isRefreshEnabled = false
                    }
                }
            } else {
                if (verticalOffset == 0) {
                    //展开
                    mSwipeToLoad.isRefreshEnabled = linearManager!!.findFirstCompletelyVisibleItemPosition() == 0
                    mSwipeToLoad.isLoadMoreEnabled = false
                } else if (Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()) {
                    //折叠
                    mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
                    mSwipeToLoad.isRefreshEnabled = false
                } else {
                    //过程
                    mSwipeToLoad.isRefreshEnabled = false
                    mSwipeToLoad.isLoadMoreEnabled = false
                }
            }
        })
    }

    /**
     * 判断RecyclerView是否滑动到底部
     */
    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    fun getDate(page :Int ,targetUserId :String , type : String , transformer : LifecycleTransformer<SreachResultArticlesBean>){
        repository.userArticle(page, targetUserId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onSuccess(it)
                        }
                    }else{
                        if (mView != null){
                            mView.onFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onFile(it.message)
                    }
                })

    }
}