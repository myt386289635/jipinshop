package com.example.administrator.jipinshop.fragment.mine.group

import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/11/27
 * @Describe
 */
class KTMyGroupPresenter {

    private var repository: Repository
    private lateinit var mView: KTMyGroupView

    fun setView(view: KTMyGroupView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun initShare(groupId : String , transformer: LifecycleTransformer<ShareInfoBean>) {
        repository.getShareInfo(5,groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.initShare(bean)
                    } else {
                        mView.onFile(bean.msg)
                    }
                }, { throwable -> mView.onFile(throwable.message) })
    }
}