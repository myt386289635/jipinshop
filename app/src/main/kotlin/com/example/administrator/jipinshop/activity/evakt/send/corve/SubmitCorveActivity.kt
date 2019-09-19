package com.example.administrator.jipinshop.activity.evakt.send.corve

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.evakt.send.goods.AddGoodsActivity
import com.example.administrator.jipinshop.adapter.SubmitCorveAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.databinding.ActivityCoverReportBinding
import com.example.administrator.jipinshop.util.ImageCompressUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog
import java.io.File
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
class SubmitCorveActivity : BaseActivity(), View.OnClickListener, SelectPicDialog.ChoosePhotoCallback, SubmitCorveView, SubmitCorveAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter : SubmitCorvePresenter

    private lateinit var mBinding: ActivityCoverReportBinding
    private var content : String = ""
    private var title : String = ""
    private var articleId : String = ""
    private var conver : String = ""
    private var mPicDialog: SelectPicDialog? = null
    private var mDialog: Dialog? = null
    private lateinit var mList: MutableList<SreachResultGoodsBean.DataBean>
    private lateinit var mAdapter: SubmitCorveAdapter
    private var goodsNum = 0 //添加的商品数量

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cover_report)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        content = intent.getStringExtra("content")
        title = intent.getStringExtra("title")
        articleId = intent.getStringExtra("articleId")
        conver = intent.getStringExtra("conver")
        mBinding.addGoods.visibility = View.VISIBLE
        mBinding.recyclerView.visibility = View.VISIBLE

        mBinding.inClude?.let {
            it.titleTv.text = "清单编辑"
        }
        if (!TextUtils.isEmpty(conver)) {
            mBinding.coverUpload.visibility = View.GONE
            mBinding.coverImg.visibility = View.VISIBLE
            mBinding.coverChange.visibility = View.VISIBLE
            Glide.with(this)
                    .load(conver)
                    .into(mBinding.coverImg)
        } else {
            mBinding.coverUpload.visibility = View.VISIBLE
            mBinding.coverImg.visibility = View.GONE
            mBinding.coverChange.visibility = View.GONE
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = SubmitCorveAdapter(mList,this)
        mAdapter.setOnClick(this)
        mBinding.recyclerView.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        mPresenter.relatedGoods(articleId,this.bindToLifecycle())//获取相关商品
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back -> {
                setResult(330)
                finish()
            }
            R.id.cover_save -> {
                //保存草稿箱
                if (TextUtils.isEmpty(conver)) {
                    ToastUtil.show("封面图片不能为空")
                    return
                }
//                if (goodsNum == 0){
//                    ToastUtil.show("至少添加一件商品")
//                    return
//                }
                if (goodsNum > 5){
                    ToastUtil.show("最多5件商品")
                    return
                }
                mDialog = ProgressDialogView().createLoadingDialog(this, "请求中...")
                mDialog?.show()
                mPresenter.saveListing(content,title,conver,articleId,this.bindToLifecycle())
            }
            R.id.cover_submit -> {
                //提交
                if (TextUtils.isEmpty(conver)) {
                    ToastUtil.show("封面图片不能为空")
                    return
                }
//                if (goodsNum == 0){
//                    ToastUtil.show("至少添加一件商品")
//                    return
//                }
                if (goodsNum > 5){
                    ToastUtil.show("最多5件商品")
                    return
                }
                mDialog = ProgressDialogView().createLoadingDialog(this, "请求中...")
                mDialog?.show()
                mPresenter.submitReport(content,title,conver,articleId,this.bindToLifecycle())
            }
            R.id.cover_upload,R.id.cover_change -> {
                //上传图片
                if (mPicDialog == null) {
                    mPicDialog = SelectPicDialog()
                    mPicDialog?.setChoosePhotoCallback(this)
                }
                mPicDialog?.let {
                    if (!it.isAdded) {
                        it.show(supportFragmentManager, SelectPicDialog.TAG)
                    }
                }
            }
            R.id.add_goods -> {
                //跳转添加商品页面
                startActivity(Intent(this,AddGoodsActivity::class.java)
                        .putExtra("articleId",articleId)
                )
            }
        }
    }

    override fun getAbsolutePicPath(picFile: String?) {
        val mDialog = ProgressDialogView().createLoadingDialog(this, "请求中...")
        mDialog.show()
        var file = ImageCompressUtil.getimage(this, ImageCompressUtil.getPicture(this, picFile))
        mPresenter.importCustomer(this.bindToLifecycle<ImageBean>(), mDialog, File(file))
    }

    override fun uploadPicSuccess(picPath: String, file: File) {
        conver = picPath
        mBinding.coverUpload.visibility = View.GONE
        mBinding.coverImg.visibility = View.VISIBLE
        mBinding.coverChange.visibility = View.VISIBLE
        Glide.with(this)
                .load(file)
                .into(mBinding.coverImg)
    }

    override fun uploadPicFailed(error: String?) {
        ToastUtil.show(error)
    }

    override fun onSave() {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        setResult(331)
        finish()
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onRelatedGoods(bean: SreachResultGoodsBean) {
        if (bean.data != null && bean.data.size != 0) {
            mList.clear()
            bean.data.forEach {
                it.related = 1
            }
            mList.addAll(bean.data)
            mAdapter.notifyDataSetChanged()
            goodsNum = mList.size
            mBinding.addText.text = "已选"+goodsNum+"件商品（上限5件）"
        }
    }

    override fun onClickAdd(position: Int) {
        //不会走到这里  就不写逻辑代码了
    }

    override fun onClickCancle(position: Int) {
        mPresenter.deleteGoods(position,articleId,mList[position].goodsId,this.bindToLifecycle())
    }

    override fun onDetele(position: Int) {
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
        if(position != mList.size){ // 如果移除的是最后一个，忽略
            mAdapter.notifyItemRangeChanged(position, mList.size - position)
        }
        goodsNum = mList.size
        if (goodsNum == 0){
            mBinding.addText.text = ""
        }else{
            mBinding.addText.text = "已选"+goodsNum+"件商品（上限5件）"
        }
    }
}