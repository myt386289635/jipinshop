package com.example.administrator.jipinshop.activity.minekt.publishkt.detail

import com.example.administrator.jipinshop.bean.FindDetailBean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe
 */
class AuditDetailPresenter {

    private var mRepository: Repository
    private lateinit var mView: AuditDetailView

    fun setView(view: AuditDetailView){
        mView = view
    }

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    /**
     * 获取数据
     */
    fun getDetail(id: String, type: String, transformer: LifecycleTransformer<FindDetailBean>) {
        mRepository.findDetail(id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.onSuccess(bean)
                    } else {
                        mView.onFile(bean.msg)
                    }
                }, { throwable ->
                    mView.onFile(throwable.message)
                })
    }

    /**
     * 获取专属淘宝购买链接地址
     */
    fun goodsBuyLink(goodsId: String, transformer: LifecycleTransformer<ImageBean>) {
        mRepository.goodsBuyLink(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ imageBean ->
                    if (imageBean.code == 0) {
                        mView.onBuyLinkSuccess(imageBean)
                    } else {
                        mView.onFileCommentInsert(imageBean.msg)
                    }
                }, { throwable ->
                    mView.onFileCommentInsert(throwable.message)
                })
    }
}