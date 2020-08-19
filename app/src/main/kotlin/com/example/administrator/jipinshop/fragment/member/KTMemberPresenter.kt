package com.example.administrator.jipinshop.fragment.member

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/2
 * @Describe
 */
class KTMemberPresenter {

    private var repository: Repository
    private lateinit var mView: KTMemberView

    fun setView(view: KTMemberView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun initBanner(mBannerList: List<String>, context: Context, point: MutableList<ImageView>, mDetailPoint: LinearLayout, mBannerAdapter: NoPageBannerAdapter) {
        mDetailPoint.removeAllViews()
        for (i in mBannerList.indices) {
            var imageView = ImageView(context)
            point.add(imageView)
            if (i == 0) {
                imageView.setImageResource(R.mipmap.member_yes)
            } else {
                imageView.setImageResource(R.mipmap.member_no)
            }
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.leftMargin = context.resources.getDimensionPixelSize(R.dimen.x4)
            layoutParams.rightMargin = context.resources.getDimensionPixelSize(R.dimen.x4)
            mDetailPoint.addView(imageView, layoutParams)
        }
        mBannerAdapter.notifyDataSetChanged()
    }

    //会员信息
    fun memberIndex(transformer: LifecycleTransformer<MemberBean>) {
        repository.memberIndex()
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

    //会员升级
    fun memberUpdate(type : String,transformer: LifecycleTransformer<SuccessBean>){
        repository.memberUpdate(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onApply()
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    //每日任务
    fun DailytaskList(transformer: LifecycleTransformer<DailyTaskBean>) {
        repository.DailytaskList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ dailyTaskBean ->
                    if (dailyTaskBean.code == 0) {
                        mView.getDayList(dailyTaskBean)
                    } else {
                        mView.onFile(dailyTaskBean.msg)
                    }
                }, { throwable ->
                    mView.onFile(throwable.message)
                })
    }

    //填写邀请码
    fun addInvitationCode(invitationCode: String, dialog: Dialog, inputManager: InputMethodManager, transformer: LifecycleTransformer<SuccessBean>) {
        repository.addInvitationCode(invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ successBean ->
                    if (successBean.getCode() == 0) {
                        mView.onCodeSuc(dialog, inputManager, successBean)
                    } else {
                        mView.onFile(successBean.getMsg())
                    }
                }, { throwable -> mView.onFile(throwable.message) })
    }

    //签到
    fun sign(transformer: LifecycleTransformer<SignInsertBean>) {
        repository.signInsert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ signInsertBean ->
                    if (signInsertBean.getCode() == 0) {
                        mView.signSuc(signInsertBean)
                    } else {
                        mView.onFile(signInsertBean.getMsg())
                    }
                }, { throwable ->
                    mView.onFile(throwable.message)
                })
    }

    fun getParentInfo(transformer : LifecycleTransformer<TeacherBean>){
        repository.parentInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onTeacher(it)
                    }else{
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}