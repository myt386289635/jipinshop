package com.example.administrator.jipinshop.activity.home

import android.text.TextUtils
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TaskFinishBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
class HomeDetailPresenter {

    private var repository: Repository
    private lateinit var mView: HomeDetailView

    fun setView(view: HomeDetailView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun getDate(page : Int , asc : String , orderByType : String , subjectId: String, transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        map["subjectId"] = subjectId
        repository.getGoodsListBySubjectId(map)
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


    fun getGoodsListByCategory2(page : Int , asc : String , orderByType : String , category2Id: String, transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        if (!TextUtils.isEmpty(asc)){
            map["asc"] = asc
        }
        map["page"] = "" + page
        map["orderByType"] = orderByType
        map["category2Id"] = category2Id
        repository.getGoodsListByCategory2(map)
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

    //浏览商品
    fun taskFinish(transformer: LifecycleTransformer<TaskFinishBean>) {
        repository.taskFinish("17")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ taskFinishBean ->
                    if (taskFinishBean.code == 0 && taskFinishBean.msg != "success") {
                        ToastUtil.show(taskFinishBean.msg)
                    }
                }, { throwable -> })
    }

}