package com.example.administrator.jipinshop.activity.minekt

import android.content.Context
import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.graphics.ColorUtils
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.UserInfoBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/19
 * @Describe
 */
class UserPresenter {

    private var repository: Repository
    private lateinit var mView: UserView

    fun setView(view: UserView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }


    fun setStatusBarHight(relativeLayout: RelativeLayout, StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight

            relativeLayout.minimumHeight = statusBarHeight + context.resources.getDimension(R.dimen.y88).toInt()
        }
    }

    fun setTitleBlack(appBarLayout: AppBarLayout, view: ImageView , text : TextView , StatusBar: LinearLayout) {
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout1, verticalOffset ->
            if (verticalOffset == 0) {
                //展开
                view.setColorFilter(Color.WHITE)
                text.alpha = 0f
                StatusBar.alpha = 0f
            } else if (Math.abs(verticalOffset) >= appBarLayout1.totalScrollRange) {
                //折叠
                view.setColorFilter(Color.BLACK)
                text.alpha = 1f
                StatusBar.alpha = 1f
            } else {
                //过程
                val a = Math.abs(verticalOffset).toFloat()
                val b = a / 1000
                val max = Math.min(1f, b * 2)
                val blendCorlor = ColorUtils.blendARGB(Color.WHITE, Color.BLACK, max)
                view.setColorFilter(blendCorlor)
                text.alpha = max
                StatusBar.alpha = max
            }
        })
    }

    fun initTabLayout(mTabLayout: TabLayout,context: Context,mFragments: MutableList<Fragment>){
        val textLether = ArrayList<Int>()
        for (i in mFragments.indices) {
            val view = LayoutInflater.from(context).inflate(R.layout.tablayout_user, null)
            val textView = view.findViewById<TextView>(R.id.tab_name)
            when (i) {
                0 -> textView.text = "评测"
                1 -> textView.text = "清单"
                2 -> textView.text = "问答"
            }
            mTabLayout.getTabAt(i)!!.customView = view
            val a = textView.paint.measureText(textView.text.toString()).toInt()
            textLether.add(a)
        }
        mTabLayout.post {
            //拿到tabLayout的mTabStrip属性
            val mTabStrip = mTabLayout.getChildAt(0) as LinearLayout
            val totle = textLether[0] + textLether[1] + textLether[2]
            val dp = context.resources.getDimension(R.dimen.x16)
            val dp10 = (mTabLayout.width - totle) / 3
            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)
                tabView.setPadding(0, 0, 0, 0)
                val width = textLether[i] + dp10
                val params = tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = (dp10 / 2 - dp).toInt()
                params.rightMargin = (dp10 / 2 - dp).toInt()
                tabView.layoutParams = params
                tabView.invalidate()
            }
        }
    }

    fun getDate(userId : String, transformer : LifecycleTransformer<UserInfoBean> ){
        repository.getUserByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onSuccess(it)
                        }
                    }else{
                        if (mView != null){
                            mView.onFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onFile(it.message)
                    }
                })
    }

    /**
     * 添加关注
     */
    fun concernInsert(attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernInsert(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onAttent()
                        }
                    }else{
                        if (mView != null){
                            mView.onFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onFile(it.message)
                    }
                })
    }

    /**
     * 取消关注
     */
    fun concernDelete(attentionUserId : String , transformer : LifecycleTransformer<SuccessBean> ){
        repository.concernDelete(attentionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        if (mView != null){
                            mView.onCancleAttent()
                        }
                    }else{
                        if (mView != null){
                            mView.onFile(it.msg)
                        }
                    }
                }, Consumer {
                    if (mView != null){
                        mView.onFile(it.message)
                    }
                })
    }

}