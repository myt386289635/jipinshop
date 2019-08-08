package com.example.administrator.jipinshop.fragment.evaluationkt.ieva

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */
class EvaEvaPresenter {

    private var repository : Repository
    private lateinit var mView: EvaEvaView

    fun setView(view: EvaEvaView){
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
            }
        })
    }

    fun initTab(context: Context? ,tabLayout: TabLayout , mTitleList: MutableList<String>){
        for (i in mTitleList.indices) {
            tabLayout.addTab(tabLayout.newTab())
        }
        for (i in mTitleList.indices){
            val view = LayoutInflater.from(context).inflate(R.layout.tablayout_eva, null)
            val textView = view.findViewById<TextView>(R.id.tab_name)
            textView.text = mTitleList[i]
            tabLayout.getTabAt(i)?.let {
                it.customView = view
                var tabView : View = it.customView?.parent as View
                tabView.setOnClickListener {
                    mView.tabClick(i)
                }
            }
        }
    }
}