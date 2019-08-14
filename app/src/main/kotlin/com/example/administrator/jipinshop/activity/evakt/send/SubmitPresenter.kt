package com.example.administrator.jipinshop.activity.evakt.send

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.netwrok.Repository
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.view.glide.GlideApp
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
class SubmitPresenter {

    private var repository: Repository
    private lateinit var mView : SubmitView

    fun setView(view: SubmitView){
        mView = view
    }

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun addText(context: Context, linearLayout: LinearLayout, content: String, mList: MutableList<ReportContentBean>) {
        val dataBean = TryDetailBean.DataBean.GoodsContentListBean()
        dataBean.type = "1"
        dataBean.value = ""
        dataBean.height = 0.0
        dataBean.width = 0.0
        val view = LayoutInflater.from(context).inflate(R.layout.item_report_text, null)
        val editText = view.findViewById<EditText>(R.id.report_editText)
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content)
            dataBean.value = content
            editText.setSelection(editText.text.length)
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                dataBean.value = editText.text.toString()
            }
        })
        linearLayout.addView(view)
        val bean = ReportContentBean(dataBean)
        bean.editText = editText
        mList.add(bean)
    }

    fun addImge(context: Context, linearLayout: LinearLayout, img: String,
                mList: MutableList<ReportContentBean>, imgWidth: Double, imgHeight: Double,
                file: File?) {
        val dataBean = TryDetailBean.DataBean.GoodsContentListBean()
        dataBean.type = "2"
        dataBean.value = img
        dataBean.height = imgHeight
        dataBean.width = imgWidth
        val view = LayoutInflater.from(context).inflate(R.layout.item_report_img, null)
        val imageView = view.findViewById<ImageView>(R.id.report_img)
        val container = view.findViewById<RelativeLayout>(R.id.report_imgContainer)
        val imageViewClose = view.findViewById<ImageView>(R.id.report_imgClose)
        val layoutParams = imageView.layoutParams as RelativeLayout.LayoutParams
        layoutParams.width = (DistanceHelper.getAndroiodScreenwidthPixels(context).toFloat() - context.resources.getDimension(R.dimen.x28) - context.resources.getDimension(R.dimen.x28)).toInt()
        layoutParams.height = (imgHeight * layoutParams.width / imgWidth).toInt()
        imageView.layoutParams = layoutParams
        if (file == null) {
            GlideApp.loderImage(context, img, imageView, 0, 0)
        } else {
            Glide.with(context)
                    .load(file)
                    .into(imageView)
        }
        linearLayout.addView(view)
        val bean = ReportContentBean(dataBean)
        bean.imageView = imageView
        imageViewClose.setOnClickListener { v ->
            container.visibility = View.GONE
            mList.remove(bean)
        }
        mList.add(bean)
    }

    /**
     * 上传图片
     */
    fun importCustomer(ransformer: LifecycleTransformer<ImageBean>, mDialog: Dialog?, file: File, imgWidth: Int, imgHeight: Int) {
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
                        mView.uploadPicSuccess(imageBean.data, imgWidth, imgHeight, file)
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
     * 保存清单，获取清单id
     */
    fun saveListing(mDialog: Dialog ,content : String , title : String , transformer: LifecycleTransformer<FindDetailBean>){
        var hashMap = HashMap<String, String>()
        hashMap["content"] = content
        hashMap["title"] = title
        repository.saveListing(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(Consumer {
                    if (mDialog != null && mDialog.isShowing) {
                        mDialog.dismiss()
                    }
                    if (it.code == 0) {
                        mView.onSave(it)
                    } else {
                        mView.uploadPicFailed(it.msg)
                    }
                }, Consumer {
                    if (mDialog != null && mDialog.isShowing) {
                        mDialog.dismiss()
                    }
                    mView.uploadPicFailed(it.message)
                })
    }

}