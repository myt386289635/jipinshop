package com.example.administrator.jipinshop.activity

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.activity.home.classification.article.ArticleMoreView
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/7/11
 * @Describe
 */
class KTArticleMorePresenter {

    private lateinit var repository: Repository
    private lateinit var mView: ArticleMoreView

    fun setView(view: ArticleMoreView) {
        mView = view
    }

    /**
     * 坑： dragger注解的构造方法： 构造方法名前不能加 fun 标示，否者找不到构造方法
     */
    @Inject constructor(repository: Repository) {
        this.repository = repository
    }

   fun solveScoll(recyclerView: RecyclerView , swipeToLoadLayout: SwipeToLoadLayout){
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

    fun classiyArticleList(goodsCategoryId: String, page: Int, transformer: LifecycleTransformer<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>>) {
        repository.classiyArticleList(goodsCategoryId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>> { t ->
                    if (t.code == 0){
                        if (mView != null){
                            mView.onSuccess(t)
                        }
                    }else{
                        if (mView != null){
                            mView.onFile(t.msg)
                        }
                    }
                }, Consumer<Throwable> { t ->
                    if (mView != null){
                        mView.onFile(t.message)
                    }
                })
    }
}