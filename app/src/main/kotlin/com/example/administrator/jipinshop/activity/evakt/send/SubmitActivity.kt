package com.example.administrator.jipinshop.activity.evakt.send

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.evakt.send.corve.SubmitCorveActivity
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.FindDetailBean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.ReportContentBean
import com.example.administrator.jipinshop.bean.TryDetailBean
import com.example.administrator.jipinshop.databinding.ActivityCreateReportBinding
import com.example.administrator.jipinshop.util.ImageCompressUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe 发布清单 （UI与发布试用报告一致）
 */
class SubmitActivity : BaseActivity(), View.OnClickListener, SelectPicDialog.ChoosePhotoCallback, SubmitView {

    @Inject
    lateinit var mPresenter : SubmitPresenter

    private lateinit var mBinding: ActivityCreateReportBinding
    private lateinit var mList: MutableList<ReportContentBean>
    private var type = "1" //  用于判断是新建还是其他    （默认）新建是1    其他是2
    private var mDialog: SelectPicDialog? = null
    private var Conver = ""//封面
    private var articleId = ""//清单id
    private var reason = ""//审核失败原因

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_report)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mList = mutableListOf()
        type = intent.getStringExtra("type")
        mBinding.inClude?.let { it.titleTv.text = "清单编辑" }
        //解决ScrollView中包含EditText在启动的时候滚动
        mBinding.scrollView.let {
            it.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
            it.isFocusable = true
            it.isFocusableInTouchMode = true
            it.setOnTouchListener { v, _ ->
                v?.requestFocusFromTouch()
                false
            }
        }
        mBinding.reportTitle.let {
            it.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(36))
            it.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) {
                        mBinding.reportTitleLimi.text = "0/36"
                    } else {
                        val html = "<font color='#202020'>" + s.length + "</font>/36"
                        mBinding.reportTitleLimi.text = Html.fromHtml(html)
                    }
                }
            })
        }

        if (type == "1"){
            //创建
            mPresenter.addText(this, mBinding.reportContentContainer, "", mList)
            showKeyboardDelayed(mBinding.reportTitle)
        }else{
            //编辑
            articleId = intent.getStringExtra("articleId")
            reason = intent.getStringExtra("remark")
            if (TextUtils.isEmpty(reason)){
                mBinding.reportReason.visibility = View.GONE
            }else{
                mBinding.reportReason.visibility = View.VISIBLE
                mBinding.reportReason.text = reason
            }
            mPresenter.getDetail(articleId,this.bindToLifecycle())
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.report_next  ->{ //下一步
                if (TextUtils.isEmpty(mBinding.reportTitle.text.toString())) {
                    ToastUtil.show("请输入标题")
                    return
                }
                val dataBeans = ArrayList<TryDetailBean.DataBean.GoodsContentListBean>()
                for (i in mList.indices) {
                    dataBeans.add(mList[i].dataBean)
                }
                var textNum = 0
                var imgNum = 0
                for (dataBean in dataBeans) {
                    if (dataBean.type == "1") {
                        textNum += dataBean.value.length
                    } else {
                        imgNum++
                    }
                }
                if (textNum <= 100) {
                    ToastUtil.show("文章内容应大于100字")
                    return
                }
                if (imgNum < 1) {
                    ToastUtil.show("内容图片数量应大于一张 ")
                    return
                }
                val json = Gson().toJson(dataBeans, object : TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>(){}.type)
//                Log.e("----",json)
                if (type == "1") {
                    //创建
                    val mDialog = ProgressDialogView().createLoadingDialog(this, "请求中...")
                    mDialog.show()
                    mPresenter.saveListing(mDialog,json,mBinding.reportTitle.text.toString(),this.bindToLifecycle())
                }else {
                    //其他
                    startActivityForResult(Intent(this, SubmitCorveActivity::class.java)
                            .putExtra("content", json)
                            .putExtra("title", mBinding.reportTitle.text.toString())
                            .putExtra("articleId",articleId)
                            .putExtra("conver", Conver)
                            , 331)
                }
            }
            R.id.title_back -> {
                finish()
            }
            R.id.report_text ->{//添加文字
                if (mList.size == 0){
                    mPresenter.addText(this, mBinding.reportContentContainer, "", mList)
                    showKeyboardDelayed(mBinding.reportTitle)
                }else{
                    if (mList[mList.size - 1].dataBean.type == "2") {
                        mPresenter.addText(this, mBinding.reportContentContainer, "", mList)
                        mBinding.scrollView.post { mBinding.scrollView.fullScroll(View.FOCUS_DOWN) }
                    } else {
                        showKeyboardDelayed(mList[mList.size - 1].editText)
                        mList[mList.size - 1].editText.setSelection(mList[mList.size - 1].editText.text.length)
                    }
                }
            }
            R.id.report_pic -> {//添加图片
                mBinding.reportTitle.clearFocus()
                if (mDialog == null){
                    mDialog = SelectPicDialog()
                    mDialog?.setChoosePhotoCallback(this)
                }
                mDialog?.let {
                    if (!it.isAdded) {
                        it.show(supportFragmentManager, SelectPicDialog.TAG)
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isHideInput(view, ev)) {
                showKeyboard(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isHideInput(v: View?, ev: MotionEvent): Boolean {
        return if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            !(ev.x > left && ev.x < right && ev.y > top
                    && ev.y < bottom)
        }else{
            false
        }
    }


    override fun getAbsolutePicPath(picFile: String?) {
        val mDialog = ProgressDialogView().createLoadingDialog(this, "请求中...")
        mDialog.show()
        //这里进行了图片旋转后得到新图片
        var file = ImageCompressUtil.getimage1(this, ImageCompressUtil.getPicture(this, picFile))
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file, options)
        val imgWidth = options.outWidth
        val imgHeight = options.outHeight
        mPresenter.importCustomer(this.bindToLifecycle<ImageBean>(), mDialog, File(file), imgWidth, imgHeight)
    }

    override fun uploadPicSuccess(picPath: String, imgWidth: Int, imgHeight: Int, file: File) {
        if (mList.size == 1 && mList[0].dataBean.type == "1"
                && TextUtils.isEmpty(mList[0].dataBean.value)) {
            //判断一进来就选择图片时的情况，删掉第一个editText
            mList[0].editText.visibility = View.GONE
            mList.removeAt(0)
        }
        mPresenter.addImge(this, mBinding.reportContentContainer, picPath, mList, imgWidth.toDouble(), imgHeight.toDouble(), file)
        mBinding.scrollView.post { mBinding.scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    override fun uploadPicFailed(error: String?) {
        ToastUtil.show(error)
    }

    override fun onSave(bean : FindDetailBean) {
        startActivityForResult(Intent(this, SubmitCorveActivity::class.java)
                .putExtra("content", bean.data.content)
                .putExtra("title", mBinding.reportTitle.text.toString())
                .putExtra("articleId", bean.data.articleId)
                .putExtra("conver", Conver)
                , 331)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            331 -> {
                finish()
            }
        }
    }

    override fun onSuccess(bean: FindDetailBean) {
        var dataBeans = mutableListOf<TryDetailBean.DataBean.GoodsContentListBean>()
        dataBeans.addAll(Gson().fromJson<Collection<TryDetailBean.DataBean.GoodsContentListBean>>(bean.data.content, object : TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>() {}.type))
        for (dataBean in dataBeans) {
            if (dataBean.type == "1") {
                mPresenter.addText(this, mBinding.reportContentContainer, dataBean.value, mList)
            } else {
                mPresenter.addImge(this, mBinding.reportContentContainer, dataBean.value, mList, dataBean.width, dataBean.height, null)
            }
        }
        mBinding.reportTitle.setText(bean.data.title)
        mBinding.reportTitle.setSelection(mBinding.reportTitle.text.length)
        mBinding.reportTitle.clearFocus()
        Conver = bean.data.img
        val html = "<font color='#202020'>" + mBinding.reportTitle.text.length + "</font>/36"
        mBinding.reportTitleLimi.text = Html.fromHtml(html)
    }

    override fun onFile(error: String?) {
        ToastUtil.show(error)
        mPresenter.addText(this, mBinding.reportContentContainer, "", mList)
        showKeyboardDelayed(mBinding.reportTitle)
    }
}