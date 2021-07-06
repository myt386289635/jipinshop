package com.example.administrator.jipinshop.activity.share

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.MotionEvent
import android.widget.TextView
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.bean.TaskFinishBean
import com.example.administrator.jipinshop.databinding.ActivityShareBinding
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.FileManager
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import com.xiaomi.push.it
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import java.util.*
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
    fun getGoodsShareInfo(otherGoodsId: String, shareImgLocation: Int , source : String,transformer: LifecycleTransformer<ShareBean>) {
        mRepository.getGoodsShareInfo(otherGoodsId,shareImgLocation,source)
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
    fun refreshInfo(type: String , otherGoodsId: String, shareImgLocation: Int , source : String,transformer: LifecycleTransformer<ShareBean>){
        mRepository.getGoodsShareInfo(otherGoodsId,shareImgLocation,source)
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

    /**
     * 下载图片
     */
    fun downLoadImg(context: Context, urls :MutableList<String>, share_media: SHARE_MEDIA?, transformer: LifecycleTransformer<ResponseBody>){
        var observables = mutableListOf<Observable<File>>()
        for (i in urls.indices){
            var observable =
                    mRepository.downLoadImg(urls[i])
                            .compose(transformer)
                            .map { it ->
                                var bys = it.bytes()
                                var bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.size)
                                FileManager.saveFile(bitmap, context)
                            }
            observables.add(observable)
        }
        var mun = 0
        var imageUris = ArrayList<Uri>()
        Observable.merge(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        imageUris.add(Uri.fromFile(it))
                    } else {
                        //修复微信在7.0崩溃的问题
                        var uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context?.contentResolver, it.absolutePath, it.name, null))
                        imageUris.add(uri)
                    }
                    mun++
                    if (mun == urls.size){
                        mView.downLoadSuc(share_media,imageUris)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    /**
     * 分享商品获取极币
     */
    fun taskFinish(transformer: LifecycleTransformer<TaskFinishBean>) {
        mRepository.taskFinish("20")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ taskFinishBean ->
                    if (taskFinishBean.code == 0 && taskFinishBean.msg != "success") {
                        ToastUtil.show(taskFinishBean.msg)
                    }
                }, { throwable ->

                })
    }
}