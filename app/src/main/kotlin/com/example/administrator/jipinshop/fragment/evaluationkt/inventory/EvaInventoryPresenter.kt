package com.example.administrator.jipinshop.fragment.evaluationkt.inventory

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaluationListBean
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
class EvaInventoryPresenter {

    private var repository : Repository
    private lateinit var mView: EvaInventoryView

    fun setView(view: EvaInventoryView){
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

    fun initTab(context: Context?, tabLayout: TabLayout, mTitleList: MutableList<EvaluationTabBean.DataBean>){
        for (i in mTitleList.indices) {
            tabLayout.addTab(tabLayout.newTab())
        }
        for (i in mTitleList.indices){
            val view = LayoutInflater.from(context).inflate(R.layout.tablayout_eva, null)
            val textView = view.findViewById<TextView>(R.id.tab_name)
            textView.text = mTitleList[i].categoryName
            tabLayout.getTabAt(i)?.let {
                it.customView = view
                var tabView : View = it.customView?.parent as View
                tabView.setOnClickListener {
                    mView.tabClick(i)
                }
            }
        }
    }

    fun setTab(transformer : LifecycleTransformer<EvaluationTabBean>){
        repository.inventTab()
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

    fun setDate( categoryId : String  , page : Int , transformer : LifecycleTransformer<EvaluationListBean>){
        repository.inventList(categoryId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onDateSuc(it)
                        }
                    }else{
                        if (mView != null){
                            mView.onDateFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onDateFile(it.message)
                    }
                })
    }
}