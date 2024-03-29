package com.example.administrator.jipinshop.fragment.evaluationkt.hot

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.EvaHotBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */

class EvaHotPresenter {

    private var repository : Repository
    private lateinit var mView : EvaHotView

    fun setView(view : EvaHotView){
        mView = view
    }

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

    fun recommendList(page: Int, transformer: LifecycleTransformer<EvaHotBean>){
        repository.recommendList(page)
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

    /**
     * 添加关注
     */
    fun concernInsert(pos : Int,attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onAttent(pos)
                        }
                    }else{
                        if (mView != null){
                            mView.commenFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.commenFile(it.message)
                    }
                })
    }

    /**
     * 取消关注
     */
    fun concernDelete(pos : Int,attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onCancleAttent(pos)
                        }
                    }else{
                        if (mView != null){
                            mView.commenFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.commenFile(it.message)
                    }
                })
    }

}