package com.example.administrator.jipinshop.fragment.mine.team

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.FansBean
import com.example.administrator.jipinshop.bean.SubUserBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.HashMap
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/6/8
 * @Describe
 */
class TeamOnePresenter {

    private var repository: Repository
    private lateinit var mView: KTTeamView

    fun setView(view: KTTeamView){
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
            if (once[0] || mRecyclerView.visibility == View.GONE) {//数据为空时
                mSwipeToLoad.isRefreshEnabled = verticalOffset == 0
                mSwipeToLoad.isLoadMoreEnabled = false
            } else {
                when {
                    verticalOffset == 0 -> {
                        //展开
                        mSwipeToLoad.isRefreshEnabled = linearManager!!.findFirstCompletelyVisibleItemPosition() == 0
                        mSwipeToLoad.isLoadMoreEnabled = false
                    }
                    Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange() -> {
                        //折叠
                        mSwipeToLoad.isLoadMoreEnabled = isSlideToBottom(mRecyclerView)
                        mSwipeToLoad.isRefreshEnabled = false
                    }
                    else -> {
                        //过程
                        mSwipeToLoad.isRefreshEnabled = false
                        mSwipeToLoad.isLoadMoreEnabled = false
                    }
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

    fun getDate(page : Int , transformer: LifecycleTransformer<FansBean>){
        var map = HashMap<String,String>()
        map["page"] = "" + page
        map["type"] = "1"
        repository.getSubUserList(map)
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

    fun getSubUserDetail(subUserId : String ,transformer: LifecycleTransformer<SubUserBean>){
        repository.getSubUserDetail(subUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.subDetail(it)
                    }else{
                        ToastUtil.show(it.msg)
                    }
                }, Consumer {
                    ToastUtil.show(it.message)
                })
    }
}