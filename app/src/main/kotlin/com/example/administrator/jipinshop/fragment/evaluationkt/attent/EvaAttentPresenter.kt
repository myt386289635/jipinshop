package com.example.administrator.jipinshop.fragment.evaluationkt.attent

import android.support.v7.widget.RecyclerView
import android.view.View
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaAttentPresenter {

    private var repository : Repository
    private lateinit var mView : EvaAttentView

    fun setView(view : EvaAttentView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun solveScoll(recyclerView: RecyclerView, swipeToLoadLayout: SwipeToLoadLayout){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            private var firstChild : View? = null

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //解决单个item超过整个屏幕的时候
                if (recyclerView.childCount > 0) {
                    firstChild = recyclerView.getChildAt(0)
                }
                var firstChildPosition =  if ( firstChild == null){
                    0
                } else{
                    recyclerView.getChildLayoutPosition(firstChild!!)
                }
                if ( firstChild == null){
                    val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount === 0) 0 else recyclerView.getChildAt(0).top
                    swipeToLoadLayout.isRefreshEnabled = (topRowVerticalPosition >= 0)
                }else{
                    swipeToLoadLayout.isRefreshEnabled = (firstChildPosition == 0 && firstChild!!.top >= 0)
                }

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

    fun myfollowList(page: Int, transformer: LifecycleTransformer<EvaAttentBean>){
        repository.myfollowList(page)
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

    /**
     * 添加关注
     */
    fun concernInsert2(pos : Int , fpos : Int,attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onAttent2(pos,fpos)
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
    fun concernDelete2(pos : Int, fpos : Int ,attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onCancleAttent2(pos,fpos)
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