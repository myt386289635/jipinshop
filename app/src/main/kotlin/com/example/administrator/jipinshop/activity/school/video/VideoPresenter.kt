package com.example.administrator.jipinshop.activity.school.video

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.FileManager
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe
 */
class VideoPresenter {

    private var mRepository: Repository
    private lateinit var mView : VideoView

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun setView(view: VideoView){
        mView = view
    }

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }

    fun getVideo(courseId : String, transformer: LifecycleTransformer<VideoBean>){
        mRepository.videoDetail(courseId)
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
     * 下载视频
     */
    fun downLoadVideo(url : String, transformer: LifecycleTransformer<ResponseBody>){
        mRepository.downLoadImg(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    mView.onVideo(it)
                }, Consumer {
                    mView.onFile(it.message)
                })
    }


    /**
     * 添加点赞
     */
    fun snapInsert( id: String, transformer: LifecycleTransformer<VoteBean>) {
        val hashMap = HashMap<String, String>()
        hashMap["type"] = "7"
        hashMap["targetId"] = id
        mRepository.snapInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ successBean ->
                    if (successBean.code == 0 || successBean.code == 602) {
                        mView.onVote(successBean)
                    } else {
                        mView.onFile(successBean.msg)
                    }
                }, { throwable ->
                    mView.onFile(throwable.message)
                })
    }

    /**
     * 删除点赞
     */
    fun snapDelete(id: String, transformer: LifecycleTransformer<VoteBean>) {
        val hashMap = HashMap<String, String>()
        hashMap["type"] = "7"
        hashMap["targetId"] = id
        mRepository.snapDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe({ successBean ->
                    if (successBean.code == 0 || successBean.code == 602) {
                        mView.onDelVote(successBean)
                    } else {
                        mView.onFile(successBean.msg)
                    }
                }, { throwable ->
                    mView.onFile(throwable.message)
                })
    }

    fun initCourses(categoryId : String, transformer: LifecycleTransformer<SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>>){
        mRepository.listByCategoryId(categoryId, 0)
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

    //添加转发数
    fun addShareCourse(courseId : String , transformer : LifecycleTransformer<SuccessBean> ){
        mRepository.addShareCourse(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {}, Consumer {})
    }
}