package com.example.administrator.jipinshop.activity.school.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SreachHistoryBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.google.android.flexbox.FlexboxLayout
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
class SchoolSearchPresenter {

    private var mRepository: Repository
    private lateinit var mView: SchoolSearchView

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun setView(view:SchoolSearchView){
        mView = view
    }

    fun setDate(transformer: LifecycleTransformer<SreachHistoryBean>){
        mRepository.searchCourseLog()
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

    fun initHot(context: Context, flexboxLayout: FlexboxLayout, hotText: List<SreachHistoryBean.DataBean.HotWordListBean>) {
        flexboxLayout.removeAllViews()
        for (i in hotText.indices) {
            val itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy2, null)
            val textView = itemTypeView.findViewById<TextView>(R.id.histroy_item)
            textView.text = hotText[i].word
            textView.setOnClickListener { v ->
                if (mView != null) {
                    mView.jump(textView.text.toString())
                }
            }
            flexboxLayout.addView(itemTypeView)
        }
    }

    fun initHistroy(context: Context, flexboxLayout: FlexboxLayout, hotText: List<SreachHistoryBean.DataBean.LogListBean>) {
        flexboxLayout.removeAllViews()
        for (i in hotText.indices) {
            val itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy2, null)
            val textView = itemTypeView.findViewById<TextView>(R.id.histroy_item)
            textView.text = hotText[i].word
            textView.setOnClickListener { v ->
                if (mView != null) {
                    mView.jump(textView.text.toString())
                }
            }
            flexboxLayout.addView(itemTypeView)
        }
        flexboxLayout.post {
            val layoutParams = flexboxLayout.layoutParams
            if (flexboxLayout.flexLines.size >= 2) {
                layoutParams.height = context.resources.getDimension(R.dimen.y180).toInt()
            } else {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            flexboxLayout.layoutParams = layoutParams
        }

    }

    fun searchCourseDeleteAll(transformer: LifecycleTransformer<SuccessBean>){
        mRepository.searchCourseDeleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onDelect()
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}