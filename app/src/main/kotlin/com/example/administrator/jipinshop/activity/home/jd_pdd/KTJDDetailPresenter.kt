package com.example.administrator.jipinshop.activity.home.jd_pdd

import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/26
 * @Describe
 */
class KTJDDetailPresenter {

    private lateinit var mView: KTJDDetailView
    private var repository: Repository

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setView(view: KTJDDetailView){
        mView = view
    }

    fun  getData( source : String, transformer : LifecycleTransformer<JDBean>){
        repository.tbkCategory(source)
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

    fun  getOtherGoodsListByCategory( category1Id :String,  page : Int,  source : String ,transformer : LifecycleTransformer<TBSreachResultBean> ){
        repository.getOtherGoodsListByCategory(category1Id, page, source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onList(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    fun commendGoodsList(page : Int , source : String ,transformer: LifecycleTransformer<TBSreachResultBean>){
        var map = HashMap<String, String>()
        map["page"] = "" + page
        map["source"] = source
        repository.commendGoodsList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onList(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}