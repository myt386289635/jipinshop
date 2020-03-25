package com.example.administrator.jipinshop.activity.share

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.adapter.ShareAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.ShareBean
import com.example.administrator.jipinshop.databinding.ActivityShareBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.FileManager
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
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
    private var downloadUrl: String = ""
    private var tkl: String = ""
    private var invitationCode: String = ""
    private var iwHelper: ImageWatcherHelper? = null
    private var layDecoration: DecorationLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_share)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        goodsId = intent.getStringExtra("otherGoodsId")//商品id
        mBinding.shareTitle.movementMethod = ScrollingMovementMethod.getInstance()
        mBinding.shareContent.movementMethod = ScrollingMovementMethod.getInstance()
        mBinding.inClude?.let {
            it.titleTv.text = "创建分享"
        }
        mPresenter.initCheckBox(mBinding)
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

        mShareImages = mutableListOf()
        mList = mutableListOf()
        mSet = mutableListOf()
        mBinding.shareImages.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        mAdapter = ShareAdapter(mList,this)
        mAdapter.setSet(mSet)
        mAdapter.setPosition(0)
        mAdapter.setOnClick(this)
        mBinding.shareImages.adapter = mAdapter

        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mPresenter.getGoodsShareInfo(goodsId,shareImgLocation,this.bindToLifecycle())
    }

    fun canVerticalScroll(editText : TextView): Boolean{
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

    override fun initShareContent(checkBox1: Boolean, checkBox2: Boolean, checkBox3: Boolean) {
        var string = ""
        if (checkBox1) string += tkl + "\n"
        if (checkBox2) string += downloadUrl + "\n"
        if (checkBox3) string += invitationCode
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
                DialogUtil.LoginDialog(this,"评论内容复制成功","去微信粘贴","等等"){
                    var intent : Intent? = PlatformUtil.sharePYQ_images(this)
                    intent?.let {
                        startActivity(intent)
                    }
                }
            }
            R.id.share_wechat -> {
                shareImages()
                sharePlafrom(SHARE_MEDIA.WEIXIN)
            }
            R.id.share_pyq -> {
                shareImages()
                if (mShareImages.size == 1){
                    //直接分享图片
                    var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                    clip.primaryClip = clipData
                    SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                    ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                    ShareUtils(this, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .shareImage(this,temp)
                }else{
                    download()
                }
            }
            R.id.share_qq -> {
                shareImages()
                sharePlafrom(SHARE_MEDIA.QQ)
            }
            R.id.share_wb -> {
                shareImages()
                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                ShareUtils(this, SHARE_MEDIA.SINA)
                        .shareImages(this, mBinding.shareTitle.text.toString(),mShareImages)
            }
            R.id.share_pic -> {
                shareImages()
                download()
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
                    mPresenter.refreshInfo(goodsId,shareImgLocation,this.bindToLifecycle())
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
        dissRefresh()
        downloadUrl = bean.data.downloadUrl
        tkl = bean.data.tkl
        invitationCode = bean.data.invitationCode
        temp = bean.data.shareImg
        mBinding.shareMoney.text = "分享后复制【评论区文案】，预估收益"+ bean.data.fee +"元！"
        mBinding.shareTitle.text = bean.data.content
        mBinding.shareContent.text = bean.data.tkl + "\n" + bean.data.downloadUrl + "\n" + bean.data.invitationCode
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

    override fun onRefresh(shareImage: String) {
        temp = shareImage
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

    fun download(){
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        var mun = 0
        for (i in mShareImages.indices){
            Glide.with(this)
                    .asBitmap()
                    .load(mShareImages[i])
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            FileManager.saveFile(resource, this@ShareActivity)
                            mun++
                            if (mShareImages.size == mun) {
                                dissRefresh()
                                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                                clip.primaryClip = clipData
                                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                                DialogUtil.sharePYQDialog(this@ShareActivity){
                                    var intent : Intent? = PlatformUtil.sharePYQ_images(this@ShareActivity)
                                    intent?.let {
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    })
        }
    }

    fun sharePlafrom(share_media: SHARE_MEDIA){
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        var num = 0
        var imageUris = ArrayList<Uri>()
        for (i in mShareImages.indices) {
            Glide.with(this)
                    .asBitmap()
                    .load(mShareImages[i])
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            var file = FileManager.saveFile(resource, this@ShareActivity)
                            num++
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                imageUris.add(Uri.fromFile(file))
                            } else {
                                //修复微信在7.0崩溃的问题
                                var uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(contentResolver, file.absolutePath, file.name, null))
                                imageUris.add(uri)
                            }
                            if (mShareImages.size == num) {
                                dissRefresh()
                                var clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                var clipData = ClipData.newPlainText("jipinshop", mBinding.shareTitle.text.toString())
                                clip.primaryClip = clipData
                                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.shareTitle.text.toString())
                                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                                if (share_media == SHARE_MEDIA.WEIXIN) {
                                    var intent: Intent? = PlatformUtil.shareWX_images(this@ShareActivity, imageUris)
                                    intent?.let {
                                        startActivityForResult(intent, 301)
                                    }
                                } else if (share_media == SHARE_MEDIA.QQ) {
                                    var intent: Intent? = PlatformUtil.shareQQ_images(this@ShareActivity, imageUris)
                                    intent?.let {
                                        startActivityForResult(intent, 302)
                                    }
                                }
                            }
                        }
                    })
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