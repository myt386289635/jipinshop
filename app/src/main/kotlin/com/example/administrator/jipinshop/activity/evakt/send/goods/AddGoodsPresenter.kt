package com.example.administrator.jipinshop.activity.evakt.send.goods

import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/14
 * @Describe
 */
class AddGoodsPresenter {

    private var repository: Repository
    private lateinit var mView: AddGoodsView

    fun setView(view: AddGoodsView){
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

    fun searchInventory(articleId :String ,keyword : String , page : Int , transformer: LifecycleTransformer<SreachResultGoodsBean>){
        repository.searchInventory(articleId, keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onSuccess(it)
                    } else {
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    fun  addGoods(position : Int ,articleId : String ,  goodsId :String , transformer: LifecycleTransformer<SuccessBean>){
        repository.addGoods(articleId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onAdd(position)
                    } else {
                        mView.onCommonFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommonFile(it.message)
                })
    }

    fun deleteGoods(position : Int ,articleId : String ,  goodsId :String , transformer: LifecycleTransformer<SuccessBean>){
        repository.deleteGoods(articleId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onDetele(position)
                    } else {
                        mView.onCommonFile(it.msg)
                    }
                }, Consumer {
                    mView.onCommonFile(it.message)
                })
    }
}