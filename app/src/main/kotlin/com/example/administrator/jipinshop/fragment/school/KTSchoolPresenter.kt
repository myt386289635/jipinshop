package com.example.administrator.jipinshop.fragment.school

import android.content.Context
import android.widget.LinearLayout
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
class KTSchoolPresenter {

    private var mRepository: Repository
    private lateinit var mView: KTSchoolView

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun setView(view : KTSchoolView){
        mView = view
    }

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun courseIndex(transformer: LifecycleTransformer<SchoolHomeBean>){
        mRepository.courseIndex()
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
}