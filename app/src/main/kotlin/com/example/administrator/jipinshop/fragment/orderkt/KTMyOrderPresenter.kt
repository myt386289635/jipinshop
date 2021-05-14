package com.example.administrator.jipinshop.fragment.orderkt

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.OrderTBBean
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe
 */
class KTMyOrderPresenter {

    private var mRepository: Repository
    private lateinit var mView: KTMyOrderView

    fun setView(view: KTMyOrderView){
        mView = view
    }

    @Inject
    constructor(repository: Repository){
        mRepository = repository
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

    fun getDate( page :Int , source : String , status : String , transformer : LifecycleTransformer<OrderTBBean>){
        mRepository.myTaobaoOrderList(page,  status , source)
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

    fun tbGoodsDetail(position : Int , otherGoodsId: String, source: String, transformer: LifecycleTransformer<TBShoppingDetailBean>) {
        mRepository.tbGoodsDetail(otherGoodsId, source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.onNext(position)
                    } else {
                        mView.onCommonFile(bean.msg)
                    }
                }, { throwable -> mView.onCommonFile(throwable.message) })
    }


}