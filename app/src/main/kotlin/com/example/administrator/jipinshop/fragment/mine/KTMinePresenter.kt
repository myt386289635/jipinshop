package com.example.administrator.jipinshop.fragment.mine

import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.DeviceUuidFactory
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/6/5
 * @Describe
 */
class KTMinePresenter {

    private var repository: Repository
    private lateinit var mView: KTMineView

    fun setView(view: KTMineView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun modelUser(transformer: LifecycleTransformer<UserInfoBean>) {
        repository.modelUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        if (mView != null) {
                            mView.successUserInfo(it)
                        }
                    } else {
                        if (mView != null) {
                            mView.FaileUserInfo(it.code, it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null) {
                        mView.FaileUserInfo(-1 , it.message)
                    }
                })
    }

    fun listSimilerGoods(context : Context, page : Int, transformer : LifecycleTransformer<SimilerGoodsBean> ){
        var map : MutableMap<String, String>? = DeviceUuidFactory.getIdfa(context)
        map?.put("page", "" + page)
        repository.listSimilerGoods(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.onSuccess(bean)
                    } else {
                        mView.onFile(bean.msg)
                    }
                }, { throwable -> mView.onFile(throwable.message) })
    }

    //轮播图数据
    fun adList(transformer: LifecycleTransformer<SucBean<EvaluationTabBean.DataBean.AdListBean>>) {
        repository.adList("14")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ adListBeanSucBean ->
                    if (adListBeanSucBean.code == 0) {
                        if (mView != null) {
                            mView.onAdList(adListBeanSucBean)
                        }
                    } else {
                        if (mView != null) {
                            mView.onFileCommen(adListBeanSucBean.msg)
                        }
                    }
                }, { throwable ->
                    if (mView != null) {
                        mView.onFileCommen(throwable.message)
                    }
                })
    }

    /**
     * 获取未读消息
     */
    fun unMessage(ransformer: LifecycleTransformer<UnMessageBean>) {
        repository.unMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe({ unMessageBean ->
                    if (unMessageBean.code == 0){
                        mView.unMessageSuc(unMessageBean)
                    }else{
                        mView.onFileCommen(unMessageBean.msg)
                    }
                }, { throwable ->
                    mView.onFileCommen(throwable.message)
                })
    }

    //邀请码
    fun addInvitationCode(invitationCode: String, dialog: Dialog, inputManager: InputMethodManager, transformer: LifecycleTransformer<SuccessBean>) {
        repository.addInvitationCode(invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ successBean ->
                    if (successBean.code == 0) {
                        mView.onCodeSuc(dialog, inputManager, successBean)
                    } else {
                        mView.onFileCommen(successBean.msg)
                    }
                }, { throwable -> mView.onFileCommen(throwable.message) })
    }

    //佣金
    fun myCommssionSummary(transformer: LifecycleTransformer<MyWalletBean>) {
        repository.myCommssionSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ myWalletBean ->
                    if (myWalletBean.code == 0) {
                        if (mView != null) {
                            mView.onCommssionSummary(myWalletBean)
                        }
                    } else {
                        if (mView != null) {
                            mView.onFileCommen(myWalletBean.msg)
                        }
                    }
                }, { throwable ->
                    if (mView != null) {
                        mView.onFileCommen(throwable.message)
                    }
                })
    }

    //会员购买分享
    fun initShare(transformer: LifecycleTransformer<ShareInfoBean>) {
        repository.getShareInfo(7)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ bean ->
                    if (bean.code == 0) {
                        mView.initShare(bean)
                    } else {
                        mView.onFileCommen(bean.msg)
                    }
                }, { throwable -> mView.onFileCommen(throwable.message) })
    }

    //分享会员获得极币
    fun taskFinish(transformer: LifecycleTransformer<TaskFinishBean>) {
        repository.taskFinish("27")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({}, {})
    }
}