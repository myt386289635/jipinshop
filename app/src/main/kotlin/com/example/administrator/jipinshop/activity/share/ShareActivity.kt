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
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.adapter.ShareAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.databinding.ActivityShareBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil
import com.example.administrator.jipinshop.util.share.PlatformUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
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
    private var tkl: String = ""
    private var invitationCode: String = ""
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
            it.titleTv.text = "创建分享"
        }
        mPresenter.initCheckBox(mBinding)
        mPresenter.initText(mBinding)
        if (source.equals("2")){
            mBinding.shareTbButtonContainer.visibility = View.VISIBLE
            mBinding.shareOtherButtonContainer.visibility = View.GONE
            mBinding.shareCopy.text = "复制评论淘口令"
        }else{
            mBinding.shareTbButtonContainer.visibility = View.GONE
            mBinding.shareOtherButtonContainer.visibility = View.VISIBLE
            mBinding.shareCopy.text = "复制评论【链接】"
        }

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
        mPresenter.getGoodsShareInfo(goodsId,shareImgLocation,source,this.bindToLifecycle())
    }

    //淘宝选择
    override fun initShareContent(checkBox1: Boolean, checkBox2: Boolean, checkBox3: Boolean) {
        var string = baseComment + "\n"
        if (checkBox2) string += downloadUrl + "\n"
        if (checkBox3) string += invitationCode  + "\n"
        if (checkBox1) string += tkl
        mBinding.shareContent.text = string
    }

    //京东和拼多多选择
    override fun initShareContent_other(checkBox: Boolean) {
        var string = baseComment + "\n" + downloadUrl + "\n"
        if (checkBox) string += invitationCode
        mBinding.shareContent.text = string
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.share_help -> {
                startActivity(Intent(this,WebActivity::class.java)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "new-free/shareHelp")
                        .putExtra(WebActivity.title, "3步学会分享赚钱")
                )
            }
            R.id.share_copy -> {
                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareContent.text.toString())
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareContent.text.toString())
                DialogUtil.LoginDialog(this,"评论内容复制成功","去微信粘贴","暂不粘贴"){
                    var intent : Intent? = PlatformUtil.sharePYQ_images(this)
                    intent?.let {
                        startActivity(intent)
                    }
                }
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
        tkl = bean.data.tkl
        invitationCode = bean.data.invitationCode
        temp = bean.data.shareImg
        mBinding.shareMoney.text = "分享后复制【评论区文案】，预估收益"+ bean.data.fee +"元！"
        mBinding.shareTitle.text = bean.data.content
        if (source.equals("2")){
            mBinding.shareContent.text = bean.data.baseComment  + "\n" + bean.data.downloadUrl + "\n" + bean.data.invitationCode +"\n" + bean.data.tkl
        }else{
            mBinding.shareContent.text = bean.data.baseComment + "\n" + bean.data.downloadUrl + "\n" + bean.data.invitationCode
        }
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
                    ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
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
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                ShareUtils(this, SHARE_MEDIA.SINA ,mDialog)
                        .shareImages(this, mBinding.shareTitle.text.toString(),mShareImages)
            }
            "pic" -> {
                mPresenter.downLoadImg(this,mShareImages,SHARE_MEDIA.WEIXIN_CIRCLE,this.bindToLifecycle())
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
        when(share_media){
            SHARE_MEDIA.WEIXIN -> {
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                var intent: Intent? = PlatformUtil.shareWX_images(this@ShareActivity, imageUris)
                intent?.let {
                    startActivityForResult(intent, 301)
                }
            }
            SHARE_MEDIA.QQ -> {
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                var intent: Intent? = PlatformUtil.shareQQ_images(this@ShareActivity, imageUris)
                intent?.let {
                    startActivityForResult(intent, 302)
                }
            }
            SHARE_MEDIA.WEIXIN_CIRCLE -> {
                DialogUtil.sharePYQDialog(this@ShareActivity){
                    var intent : Intent? = PlatformUtil.sharePYQ_images(this@ShareActivity)
                    intent?.let {
                        startActivity(intent)
                    }
                }
            }
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