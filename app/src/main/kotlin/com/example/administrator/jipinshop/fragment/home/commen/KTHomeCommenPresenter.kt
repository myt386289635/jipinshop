package com.example.administrator.jipinshop.fragment.home.commen

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe
 */
class KTHomeCommenPresenter {
    private var repository: Repository
    private lateinit var mView: KTHomeCommenView

    fun setView(view: KTHomeCommenView){
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

    fun getDate(page : Int , asc : String , orderByType : String , category1Id: String, transformer: LifecycleTransformer<TbCommonBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        map["category1Id"] = category1Id
        repository.getGoodsListByCategory1(map)
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
}