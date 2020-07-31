package com.example.administrator.jipinshop.activity.share

import android.Manifest
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.ShareAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.databinding.ActivityShareBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil
import com.example.administrator.jipinshop.util.share.PlatformUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.pick.CustomLoadingUIProvider2
import com.example.administrator.jipinshop.view.pick.DecorationLayout
import com.example.administrator.jipinshop.view.pick.GlideSimpleLoader
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/3/19
 * @Describe 创建分享
 */
class ShareActivity : BaseActivity(), View.OnClickListener, ShareAdapter.OnClickItem, ShareView {

    @Inject
    lateinit var mPresenter: SharePresenter

    private lateinit var mBinding: ActivityShareBinding
    private lateinit var mList: MutableList<String> //分享的所有图片
    private lateinit var mSet: MutableList<Int> //记录所有图片是否被选中，1选中，0未选中
    private lateinit var mAdapter: ShareAdapter
    private var goodsId = ""//商品id
    private var shareImgLocation = 1 //默认是第一张
    private lateinit var mShareImages : MutableList<String> //分享的图片地址
    private  var temp = "" //记录海报地址
    private var mDialog: Dialog? = null
    private var mProgressDialog : Dialog? = null
    private var downloadUrl: String = ""
    private var baseComment: String = ""
    private var iwHelper: ImageWatcherHelper? = null
    private var layDecoration: DecorationLayout? = null
    private var source : String = "2" //默认来源是淘宝 1京东,2淘宝，4拼多多

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_share)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        if (!TextUtils.isEmpty(intent.getStringExtra("source"))){
            source = intent.getStringExtra("source")
        }
        goodsId = intent.getStringExtra("otherGoodsId")//商品id
        mBinding.shareTitle.movementMethod = ScrollingMovementMethod.getInstance()
        mBinding.shareContent.movementMethod = ScrollingMovementMethod.getInstance()
        mBinding.inClude?.let {
            it.titleTv.text = "分享商品"
        }
        mPresenter.initText(mBinding)

        mShareImages = mutableListOf()
        mList = mutableListOf()
        mSet = mutableListOf()
        mBinding.shareImages.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        mAdapter = ShareAdapter(mList,this)
        mAdapter.setSet(mSet)
        mAdapter.setPosition(0)
        mAdapter.setOnClick(this)
        mBinding.shareImages.adapter = mAdapter

        mProgressDialog = ProgressDialogView().createLoadingDialog(this, "")
        mProgressDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mPresenter.getGoodsShareInfo(goodsId,0,source,this.bindToLifecycle())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.share_copy -> {
                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareContent.text.toString())
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareContent.text.toString())
                ToastUtil.show("已复制评论内容到粘贴板")
            }
            R.id.share_checkBox -> {
                if (mBinding.shareCheckBox.isChecked){
                    mBinding.shareContent.text = baseComment + "\n" + downloadUrl
                }else{
                    mBinding.shareContent.text = baseComment
                }
            }
            R.id.share_all -> {
                //照片全选
                for (i in mSet.indices){
                    mSet[i] = 1
                }
                shareImgLocation = 1
                mAdapter.setPosition(0)
                mAdapter.notifyDataSetChanged()
            }
            R.id.share_copyTitle -> {
                //复制文案
                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                ToastUtil.show("已复制分享文案到粘贴板")
            }
            R.id.share_wechat -> {
                HasPermissionsUtil.permission(this, object : HasPermissionsUtil() {
                    override fun hasPermissionsSuccess() {
                        super.hasPermissionsSuccess()
                        mDialog = ProgressDialogView().createLoadingDialog(this@ShareActivity, "")
                        mDialog?.let {
                            if (!it.isShowing)
                                it.show()
                        }
                        mPresenter.refreshInfo("wechat",goodsId,shareImgLocation,source,this@ShareActivity.bindToLifecycle())
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.share_pyq -> {
                HasPermissionsUtil.permission(this, object : HasPermissionsUtil() {
                    override fun hasPermissionsSuccess() {
                        super.hasPermissionsSuccess()
                        mDialog = ProgressDialogView().createLoadingDialog(this@ShareActivity, "")
                        mDialog?.let {
                            if (!it.isShowing)
                                it.show()
                        }
                        mPresenter.refreshInfo("pyq",goodsId,shareImgLocation,source,this@ShareActivity.bindToLifecycle())
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.share_qq -> {
                HasPermissionsUtil.permission(this, object : HasPermissionsUtil() {
                    override fun hasPermissionsSuccess() {
                        super.hasPermissionsSuccess()
                        mDialog = ProgressDialogView().createLoadingDialog(this@ShareActivity, "")
                        mDialog?.let {
                            if (!it.isShowing)
                                it.show()
                        }
                        mPresenter.refreshInfo("qq",goodsId,shareImgLocation,source,this@ShareActivity.bindToLifecycle())
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.share_wb -> {
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.let {
                    if (!it.isShowing)
                        it.show()
                }
                mPresenter.refreshInfo("wb",goodsId,shareImgLocation,source,this.bindToLifecycle())
            }
            R.id.share_pic -> {
                HasPermissionsUtil.permission(this, object : HasPermissionsUtil() {
                    override fun hasPermissionsSuccess() {
                        super.hasPermissionsSuccess()
                        mDialog = ProgressDialogView().createLoadingDialog(this@ShareActivity, "")
                        mDialog?.let {
                            if (!it.isShowing)
                                it.show()
                        }
                        mPresenter.refreshInfo("pic",goodsId,shareImgLocation,source,this@ShareActivity.bindToLifecycle())
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onClickCheck(isChecked: Boolean, pos: Int) {
        if (isChecked){
            //点亮了
            mSet[pos] = 1
        }else{
            //熄灭了
            if (imagesClick() == 1){
                ToastUtil.show("至少选择一张图片")
                mAdapter.notifyDataSetChanged()
                return
            }
            mSet[pos] = 0
        }
        for (i in mSet.indices){
            if (mSet[i] == 1){
                if(shareImgLocation != (i+1)){
                    shareImgLocation = i+1
                }
                mAdapter.setPosition(i)
                break
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    fun imagesClick(): Int{
        var mun = 0
        for (i in mSet.indices){
            if (mSet[i] == 1){
                mun++
            }
        }
        return mun
    }

    override fun onSuccess(bean: ShareBean) {
        mProgressDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        baseComment = bean.data.baseComment
        downloadUrl = bean.data.downloadUrl
        temp = bean.data.shareImg
        mBinding.shareTitle.text = bean.data.content
        mBinding.shareContent.text = bean.data.baseComment + "\n" + bean.data.downloadUrl
        mList.clear()
        mList.addAll(bean.data.imgs)
        for (i in mList.indices){
            if (i == 0){
                mSet.add(1)
            }else{
                mSet.add(0)
            }
        }
        shareImgLocation = 1 //默认是第一张
        pickImages()
        mAdapter.setPosition(0)
        mAdapter.notifyDataSetChanged()
    }

    override fun onFile(error: String?) {
        dissRefresh()
        ToastUtil.show(error)
    }

    override fun onRefresh(shareImage: String ,type: String) {
        temp = shareImage
        shareImages()
        mPresenter.taskFinish(this.bindToLifecycle())
        when(type){
            "wechat" -> {
                mPresenter.downLoadImg(this,mShareImages,SHARE_MEDIA.WEIXIN,this.bindToLifecycle())
            }
            "pyq"  -> {
                if (mShareImages.size == 1){
                    //直接分享图片
                    var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                    clip.primaryClip = clipData
                    SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                    ToastUtil.show("已复制分享文案到粘贴板")
                    ShareUtils(this@ShareActivity, SHARE_MEDIA.WEIXIN_CIRCLE, mDialog)
                            .shareImage(this@ShareActivity,temp)
                }else{
                    mPresenter.downLoadImg(this,mShareImages,SHARE_MEDIA.WEIXIN_CIRCLE,this.bindToLifecycle())
                }
            }
            "qq" -> {
                mPresenter.downLoadImg(this,mShareImages,SHARE_MEDIA.QQ,this.bindToLifecycle())
            }
            "wb" -> {
                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                ToastUtil.show("已复制分享文案到粘贴板")
                ShareUtils(this, SHARE_MEDIA.SINA ,mDialog)
                        .shareImages(this, mBinding.shareTitle.text.toString(),mShareImages)
            }
            "pic" -> {
                mPresenter.downLoadImg(this,mShareImages,null,this.bindToLifecycle())
            }
        }
    }

    fun shareImages(){
        mShareImages.clear()
        mShareImages.add(temp)
        for (i in mSet.indices){
            if (mSet[i] == 1){
                //被选中
                if ((shareImgLocation - 1) != i){
                    mShareImages.add(mList[i])
                }
            }
        }
    }

    override fun downLoadSuc(share_media: SHARE_MEDIA?, imageUris: java.util.ArrayList<Uri>) {
        dissRefresh()
        var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
        clip.primaryClip = clipData
        SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
        if(share_media != null){
            when(share_media){
                SHARE_MEDIA.WEIXIN -> {
                    ToastUtil.show("已复制分享文案到粘贴板")
                    var intent: Intent? = PlatformUtil.shareWX_images(this@ShareActivity, imageUris)
                    intent?.let {
                        startActivity(intent)
                    }
                }
                SHARE_MEDIA.QQ -> {
                    ToastUtil.show("已复制分享文案到粘贴板")
                    var intent: Intent? = PlatformUtil.shareQQ_images(this@ShareActivity, imageUris)
                    intent?.let {
                        startActivity(intent)
                    }
                }
                SHARE_MEDIA.WEIXIN_CIRCLE -> {
                    ToastUtil.show("已复制分享文案到粘贴板")
                    var intent : Intent? = PlatformUtil.sharePYQ_images(this@ShareActivity)
                    intent?.let {
                        startActivity(intent)
                    }
                }
            }
        }else{
            ToastUtil.show("图片已保存到相册")
        }
    }

    fun dissRefresh() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }

    override fun onResume() {
        super.onResume()
        dissRefresh()
    }

    override fun onBackPressed() {
        iwHelper?.let {
            if (!it.handleBackPressed()) {//浏览图片如果打开，按返回键关掉浏览框架
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

    //浏览图片初始化控件
    fun pickImages() {
        var pics = mutableListOf<Uri>()
        for (i in mList.indices){
            pics.add(Uri.parse(mList[i]))
        }
        layDecoration = DecorationLayout(this)
        layDecoration?.setDataList(pics)
        iwHelper = ImageWatcherHelper.with(this, GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOtherView(layDecoration)
                .addOnPageChangeListener(layDecoration)
                .setLoadingUIProvider(CustomLoadingUIProvider2()) // 自定义LoadingUI
        layDecoration?.attachImageWatcher(iwHelper)
    }

    override fun onItem(view: View, position: Int) {
        var pics = mutableListOf<Uri>()
        for (i in mList.indices){
            pics.add(Uri.parse(mList[i]))
        }
        var mVisiblePictureList = SparseArray<ImageView>()
        mVisiblePictureList.put(position, view as ImageView)
        iwHelper?.show(view, mVisiblePictureList, pics)
    }
}