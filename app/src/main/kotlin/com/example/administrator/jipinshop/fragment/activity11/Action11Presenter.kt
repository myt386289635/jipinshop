package com.example.administrator.jipinshop.fragment.activity11

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.Action11Bean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/10/29
 * @Describe
 */
class Action11Presenter {

    private var repository : Repository
    private lateinit var mView: Action11View

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setView(view: Action11View){
        mView = view
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

    fun setDate(transformer : LifecycleTransformer<Action11Bean>){
        repository.activity11Index()
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

    fun setRefreshDate(page : Int ,  transformer : LifecycleTransformer<SucBean<Action11Bean.DataBean.GoodsDataListBean>>){
        repository.activity11GoodsList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onPageSuccess(it)
                        }
                    }else{
                        if (mView != null){
                            mView.onPageFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onPageFile(it.message)
                    }
                })
    }

    /**
     * 获取专属淘宝购买链接地址
     */
    fun goodsBuyLink(goodsId: String, transformer: LifecycleTransformer<ImageBean>) {
        repository.goodsBuyLink(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ imageBean ->
                    if (imageBean.code == 0) {
                        mView.onBuyLinkSuccess(imageBean)
                    } else {
                        mView.onFileCommentInsert(imageBean.msg)
                    }
                }, { throwable ->
                    mView.onFileCommentInsert(throwable.message)
                })
    }
}