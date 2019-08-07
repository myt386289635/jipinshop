package com.example.administrator.jipinshop.fragment.evaluationkt.attent

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaAttentPresenter {

    private var repository : Repository

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
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
}