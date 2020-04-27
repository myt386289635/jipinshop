package com.example.administrator.jipinshop.fragment.home

import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.TeacherBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
class KTHomePresenter {

    private var repository: Repository
    private lateinit var mView: KTHomeView

    fun setView(view: KTHomeView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setStatusBarHight( StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun  getData(transformer : LifecycleTransformer<JDBean>){
        repository.tbkCategory("2")
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

    fun getParentInfo(type : Int, transformer : LifecycleTransformer<TeacherBean>){
        repository.parentInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onTeacher(type,it)
                    }
                }, Consumer {
                    if (type == 1){
                        ToastUtil.show("导师信息获取失败，请重新尝试")
                    }
                })
    }
}