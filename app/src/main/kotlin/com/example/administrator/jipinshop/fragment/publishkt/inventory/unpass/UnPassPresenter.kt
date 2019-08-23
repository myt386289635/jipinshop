package com.example.administrator.jipinshop.fragment.publishkt.inventory.unpass

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe
 */
class UnPassPresenter {

    private var mRepository : Repository
    private lateinit var mView: UnPassView

    fun setView(view: UnPassView){
        mView = view
    }

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
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

    fun getDate(page :Int ,status :String, transformer : LifecycleTransformer<SreachResultArticlesBean>){
        mRepository.publishListing(page, status)
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

    /**
     * 发布清单
     */
    fun submitReport(position: Int,content : String , title : String , img : String ,  articleId : String  , transformer: LifecycleTransformer<SuccessBean>){
        mRepository.publishListing(articleId, content, img, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onSave(position)
                    } else {
                        mView.onFileCommon(it.msg)
                    }
                }, Consumer {
                    mView.onFileCommon(it.message)
                })
    }

    /**
     * 删除清单
     */
    fun  ListingDelete(position: Int, articleId : String , transformer: LifecycleTransformer<SuccessBean> ){
        mRepository.ListingDelete(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onDelete(position)
                    } else {
                        mView.onFileCommon(it.msg)
                    }
                }, Consumer {
                    mView.onFileCommon(it.message)
                })
    }
}