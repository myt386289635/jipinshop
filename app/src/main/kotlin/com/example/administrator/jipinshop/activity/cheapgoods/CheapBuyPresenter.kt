package com.example.administrator.jipinshop.activity.cheapgoods

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.example.administrator.jipinshop.bean.TaskFinishBean
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

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    //解决冲突问题
    fun solveScoll(mRecyclerView: RecyclerView, mSwipeToLoad: SwipeToLoadLayout,
                   appBarLayout: AppBarLayout, once: Array<Boolean>) {
        val layoutManager = mRecyclerView.layoutManager
        val gridLayoutManager = layoutManager as GridLayoutManager?
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mSwipeToLoad.isRefreshEnabled = gridLayoutManager!!.findFirstCompletelyVisibleItemPosition() == 0
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
                }
            } else {
                if (verticalOffset == 0) {
                    //展开
                    mSwipeToLoad.isRefreshEnabled = gridLayoutManager!!.findFirstCompletelyVisibleItemPosition() == 0
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

    fun isSlideToBottom(recyclerView: RecyclerView?) : Boolean{
        if (recyclerView == null){
            return false
        }
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true
        return false
    }

    fun setDate(page : Int ,transformer : LifecycleTransformer<NewPeopleBean>){
        repository.allowanceIndex(page)
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

    fun initShare(share_media: SHARE_MEDIA?, transformer: LifecycleTransformer<ShareInfoBean>) {
        repository.getShareInfo(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.initShare(share_media, bean)
                    } else {
                        mView.onFile(bean.msg)
                    }
                }, { throwable -> mView.onFile(throwable.message) })
    }

    //分享特惠购
    fun taskFinish(transformer: LifecycleTransformer<TaskFinishBean>) {
        repository.taskFinish("23")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ taskFinishBean ->
                }, { throwable -> })
    }
}