package com.example.administrator.jipinshop.activity.home

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TaskFinishBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
class HomeDetailPresenter {

    private var repository: Repository
    private lateinit var mView: HomeDetailView

    fun setView(view: HomeDetailView){
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

    fun getDate(page : Int , asc : String , orderByType : String , subjectId: String, transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        map["subjectId"] = subjectId
        repository.getGoodsListBySubjectId(map)
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


    fun getGoodsListByCategory2(page : Int , asc : String , orderByType : String , category2Id: String, transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        map["category2Id"] = category2Id
        repository.getGoodsListByCategory2(map)
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

    //浏览商品
    fun taskFinish(transformer: LifecycleTransformer<TaskFinishBean>) {
        repository.taskFinish("17")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ taskFinishBean ->
                    if (taskFinishBean.code == 0 && taskFinishBean.msg != "success") {
                        ToastUtil.show(taskFinishBean.msg)
                    }
                }, { throwable -> })
    }

}