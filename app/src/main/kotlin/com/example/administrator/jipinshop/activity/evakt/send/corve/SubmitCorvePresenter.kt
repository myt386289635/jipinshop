package com.example.administrator.jipinshop.activity.evakt.send.corve

import android.app.Dialog
import com.example.administrator.jipinshop.activity.evakt.send.SubmitView
import com.example.administrator.jipinshop.bean.FindDetailBean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.netwrok.Repository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
class SubmitCorvePresenter {

    private var repository: Repository
    private lateinit var mView : SubmitCorveView

    fun setView(view: SubmitCorveView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    /**
     * 上传图片
     */
    fun importCustomer(ransformer: LifecycleTransformer<ImageBean>, mDialog: Dialog?, file: File) {
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("imageFile", file.name, requestBody)
        repository.importCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe({ imageBean ->
                    if (mDialog != null && mDialog.isShowing) {
                        mDialog.dismiss()
                    }
                    if (imageBean.code == 0) {
                        mView.uploadPicSuccess(imageBean.data, file)
                    } else {
                        mView.uploadPicFailed(imageBean.msg)
                    }
                }, { throwable ->
                    if (mDialog != null && mDialog.isShowing) {
                        mDialog.dismiss()
                    }
                    mView.uploadPicFailed(throwable.message)
                })
    }

    /**
     * 保存清单
     */
    fun saveListing(content : String , title : String , img : String ,  articleId : String  , transformer: LifecycleTransformer<FindDetailBean>){
        var hashMap = HashMap<String, String>()
        hashMap["content"] = content
        hashMap["title"] = title
        hashMap["img"] = img
        hashMap["articleId"] = articleId
        repository.saveListing(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onSave()
                    } else {
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    /**
     * 发布清单
     */
    fun submitReport(content : String , title : String , img : String ,  articleId : String  , transformer: LifecycleTransformer<SuccessBean>){
        repository.publishListing(articleId, content, img, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onSave()
                    } else {
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    /**
     * 获取关联商品
     */
    fun relatedGoods(articleId : String ,transformer: LifecycleTransformer<SreachResultGoodsBean>){
        repository.relatedGoods(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onRelatedGoods(it)
                    } else {
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }

    /**
     * 取消关联商品
     */
    fun deleteGoods(position : Int ,articleId : String ,  goodsId :String , transformer: LifecycleTransformer<SuccessBean>){
        repository.deleteGoods(articleId, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (it.code == 0) {
                        mView.onDetele(position)
                    } else {
                        mView.onFile(it.msg)
                    }
                }, Consumer {
                    mView.onFile(it.message)
                })
    }
}