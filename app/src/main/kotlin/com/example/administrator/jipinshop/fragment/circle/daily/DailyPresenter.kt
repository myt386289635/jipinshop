package com.example.administrator.jipinshop.fragment.circle.daily

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.example.administrator.jipinshop.bean.CircleListBean
import com.example.administrator.jipinshop.bean.CircleTitleBean
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.FileManager
import com.example.administrator.jipinshop.util.ToastUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe
 */
class DailyPresenter {

    private var mRepository: Repository
    private lateinit var mView : DailyView

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    fun setView(view : DailyView){
        mView = view
    }

    fun circleTitle(type :String , transformer: LifecycleTransformer<CircleTitleBean>){
        mRepository.circleTitle(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onTabSuc(it)
                    }else{
                        mView.onTabFile(it.msg)
                    }
                }, Consumer {
                    mView.onTabFile(it.message)
                })
    }

    fun circleList(categoryId1: String , categoryId2: String, type: String , page : Int,  transformer: LifecycleTransformer<CircleListBean> ){
        var map = HashMap<String, String>()
        map["type"] = type
        map["page"] = "" + page
        if (!TextUtils.isEmpty(categoryId2)){
            map["categoryId2"] = categoryId2
        }
        if(!TextUtils.isEmpty(categoryId1)){
            map["categoryId1"] = categoryId1
        }
        mRepository.circleList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0){
                        mView.onListSuc(it)
                    }else{
                        mView.onListFile(it.msg)
                    }
                }, Consumer {
                    mView.onListFile(it.message)
                })
    }

    fun solveScoll(recyclerView: RecyclerView, swipeToLoadLayout: SwipeToLoadLayout){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            private var firstChild : View? = null

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //解决单个item超过整个屏幕的时候
                if (recyclerView.childCount > 0) {
                    firstChild = recyclerView.getChildAt(0)
                }
                var firstChildPosition =  if ( firstChild == null){
                    0
                } else{
                    recyclerView.getChildLayoutPosition(firstChild!!)
                }
                if ( firstChild == null){
                    val topRowVerticalPosition = if (recyclerView == null || recyclerView.childCount === 0) 0 else recyclerView.getChildAt(0).top
                    swipeToLoadLayout.isRefreshEnabled = (topRowVerticalPosition >= 0)
                }else{
                    swipeToLoadLayout.isRefreshEnabled = (firstChildPosition == 0 && firstChild!!.top >= 0)
                }

                swipeToLoadLayout.isLoadMoreEnabled = isSlideToBottom(recyclerView)
            }
        })
    }

    fun isSlideToBottom(recyclerView: RecyclerView?) : Boolean{
        if (recyclerView == null){
            return false
        }
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true
        return false
    }

    fun addShare( momentId :String , transformer: LifecycleTransformer<SuccessBean>){
        mRepository.addShare(momentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {}, Consumer {})
    }

    /**
     * 获取创建分享内容
     */
    fun getGoodsShareInfo(share_media: SHARE_MEDIA?, otherGoodsId: String, shareImgLocation: Int, transformer: LifecycleTransformer<ShareBean>) {
        mRepository.getGoodsShareInfo(otherGoodsId,shareImgLocation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0 && !TextUtils.isEmpty(it.data.shareImg)){
                        mView.onShareSuccess(it.data.shareImg,share_media)
                    }else{
                        mView.onShareFile(share_media)
                    }
                }, Consumer {
                    mView.onShareFile(share_media)
                })
    }

    /**
     * 下载图片
     */
    fun downLoadImg(context: Context, urls :MutableList<String> ,share_media: SHARE_MEDIA?, transformer: LifecycleTransformer<ResponseBody>){
        var observables = mutableListOf<Observable<ResponseBody>>()
        for (i in urls.indices){
            observables.add(mRepository.downLoadImg(urls[i]))
        }
        var mun = 0
        var imageUris = ArrayList<Uri>()
        Observable.merge(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    var bys = it.bytes()
                    var bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.size)
                    var file = FileManager.saveFile(bitmap, context)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        imageUris.add(Uri.fromFile(file))
                    } else {
                        //修复微信在7.0崩溃的问题
                        var uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context?.contentResolver, file.absolutePath, file.name, null))
                        imageUris.add(uri)
                    }
                    mun++
                    if (mun == urls.size){
                        mView.downLoadSuc(share_media,imageUris)
                    }
                }, Consumer {
                    mView.onTabFile(it.message)
                })
    }

}