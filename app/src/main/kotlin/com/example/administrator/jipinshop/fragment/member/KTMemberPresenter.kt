package com.example.administrator.jipinshop.fragment.member

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter
import com.example.administrator.jipinshop.bean.MemberBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
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

    fun memberUpdate(transformer: LifecycleTransformer<SuccessBean>){
        repository.memberUpdate()
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
}