package com.example.administrator.jipinshop.fragment.home.main

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
class KTMainPresenter {

    private var repository: Repository
    private lateinit var mView: KTMainView

    fun setView(view: KTMainView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    //解决冲突问题
    fun solveScoll(mainBackground : ImageView, mRecyclerView: RecyclerView, mSwipeToLoad: SwipeToLoadLayout, appBarLayout: AppBarLayout) {
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
                mainBackground.visibility = View.VISIBLE
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                //折叠
                mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
                mSwipeToLoad.isRefreshEnabled = false
                mainBackground.visibility = View.INVISIBLE
            } else {
                //过程
                mSwipeToLoad.isRefreshEnabled = false
                mSwipeToLoad.isLoadMoreEnabled = false
                mainBackground.visibility = View.VISIBLE
            }
        })
    }

    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    fun getDate(type: String, transformer: LifecycleTransformer<TbkIndexBean>){
        repository.tbkIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onSuccess(type,it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    fun commendGoodsList(page : Int , asc : String , orderByType : String ,transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        repository.commendGoodsList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onDay(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}