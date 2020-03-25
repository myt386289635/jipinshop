package com.example.administrator.jipinshop.activity.share

import android.view.MotionEvent
import android.widget.TextView
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.databinding.ActivityShareBinding
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/3/19
 * @Describe
 */
class SharePresenter {

    private var mRepository: Repository
    private lateinit var mView: ShareView

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun setView(view: ShareView){
        mView = view
    }

    //checkBox的setOnClickListener能监听到点击事件，但是不要在该方法里设置isChecked，checkBox是先改变isChecked属性后触发点击事件
    //点击checkBox之后会自动更改图标，不需人为控制isChecked去改变
    fun initCheckBox(mBinding: ActivityShareBinding){
        mBinding.shareCheckBox1.setOnClickListener {
            if (!mBinding.shareCheckBox2.isChecked && !mBinding.shareCheckBox1.isChecked) {
                //下载链接没选，正在取消淘口令
                ToastUtil.show("淘口令与下载链接必须选择其中一个")
                mBinding.shareCheckBox1.isChecked = true
                return@setOnClickListener
            }
            mView.initShareContent(mBinding.shareCheckBox1.isChecked,mBinding.shareCheckBox2.isChecked,
                    mBinding.shareCheckBox3.isChecked)
        }
        mBinding.shareCheckBox2.setOnClickListener {
            if (!mBinding.shareCheckBox1.isChecked && !mBinding.shareCheckBox2.isChecked) {
                //淘口令没选，正在取消下载链接
                ToastUtil.show("淘口令与下载链接必须选择其中一个")
                mBinding.shareCheckBox2.isChecked = true
                return@setOnClickListener
            }
            mView.initShareContent(mBinding.shareCheckBox1.isChecked,mBinding.shareCheckBox2.isChecked,
                    mBinding.shareCheckBox3.isChecked)
        }
        mBinding.shareCheckBox3.setOnClickListener {
            mView.initShareContent(mBinding.shareCheckBox1.isChecked,mBinding.shareCheckBox2.isChecked,
                    mBinding.shareCheckBox3.isChecked)
        }
    }

    //解决scrollView嵌套滑动textView时出现的滑动冲突
    fun initText(mBinding: ActivityShareBinding){
        mBinding.shareTitle.setOnTouchListener { v, event ->
            if (canVerticalScroll(mBinding.shareTitle)){
                if(event.action == MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }
                if(event.action == MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }
                if (event.action == MotionEvent.ACTION_UP ){
                    v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            return@setOnTouchListener false
        }
        mBinding.shareContent.setOnTouchListener { v, event ->
            if (canVerticalScroll(mBinding.shareContent)){
                if(event.action == MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }
                if(event.action == MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }
                if (event.action == MotionEvent.ACTION_UP ){
                    v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun canVerticalScroll(editText : TextView): Boolean{
        //滚动的距离
        var scrollY = editText.scrollY
        //控件内容的总高度
        var scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        var scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        var scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1)
    }

    /**
     * 获取创建分享内容
     */
    fun getGoodsShareInfo(otherGoodsId: String, shareImgLocation: Int , transformer: LifecycleTransformer<ShareBean>) {
        mRepository.getGoodsShareInfo(otherGoodsId,shareImgLocation)
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

    /**
     * 获取海报
     */
    fun refreshInfo(type: String , otherGoodsId: String, shareImgLocation: Int , transformer: LifecycleTransformer<ShareBean>){
        mRepository.getGoodsShareInfo(otherGoodsId,shareImgLocation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onRefresh(it.data.shareImg,type)
                    }
                }, Consumer {
                })
    }
}