package com.example.administrator.jipinshop.activity.school.video

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.SucBeanT
import com.example.administrator.jipinshop.databinding.ActivityVideoBinding
import com.example.administrator.jipinshop.util.FileManager
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.umeng.socialize.bean.SHARE_MEDIA
import okhttp3.ResponseBody
import javax.inject.Inject


/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe 商学院视频详情页
 */
class VideoActivity : BaseActivity(), View.OnClickListener, VideoView, ShareBoardDialog2.onShareListener {

    @Inject
    lateinit var mPresenter: VideoPresenter
    private lateinit var binding: ActivityVideoBinding
    private var mDialog: Dialog? = null
    private var courseId : String = ""
    private var orientationUtils : OrientationUtils? = null
    private var mShareBoardDialog: ShareBoardDialog2? = null
    private var mUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video)
        binding.listener = this
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(false, 0f)
                .init()
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        courseId = intent.getStringExtra("courseId")
        mPresenter.setStatusBarHight(binding.statusBar,this)

        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mPresenter.getVideo(courseId,this.bindToLifecycle())

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.video_listContainer -> {

            }
            R.id.video_likeContainer -> {

            }
            R.id.video_shareContainer -> {
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(1)
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(supportFragmentManager,"ShareBoardDialog2")
                    }
                }
            }
        }
    }

    override fun onSuccess(bean: SucBeanT<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        //配置视频播放
        mUrl = bean.data.video
        orientationUtils = OrientationUtils(this, binding.itemPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false
        binding.itemPlayer.loadCoverImage(bean.data.video, R.color.transparent)
        binding.itemPlayer.setUp(bean.data.video, true, bean.data.title)
        binding.itemPlayer.titleTextView.visibility = View.VISIBLE//增加title
        binding.itemPlayer.backButton.visibility = View.VISIBLE//设置返回键
        binding.itemPlayer.fullscreenButton.visibility = View.GONE//设置全屏按键功能
        binding.itemPlayer.setIsTouchWiget(true) //是否可以滑动界面改变进度，声音等 默认true
        binding.itemPlayer.isRotateViewAuto = false
        binding.itemPlayer.startPlayLogic()
        binding.itemPlayer.backButton.setOnClickListener {
            finish()
        }
        //初始化其他数据
        binding.videoLike.text = bean.data.liked
        binding.videoShare.text = bean.data.share
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onPause() {
        super.onPause()
        binding.itemPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        binding.itemPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        orientationUtils?.releaseListener()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //释放所有
        binding.itemPlayer.setVideoAllCallBack(null)
    }

    //保存视频
    override fun onLink() {
        mDialog = ProgressDialogView().createLoadingDialog(this, "下载视频中")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mPresenter.downLoadVideo(mUrl,this.bindToLifecycle())
    }

    override fun onVideo(it: ResponseBody) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        FileManager.saveVideo(it.byteStream(),this)
    }

    //分享
    override fun share(share_media: SHARE_MEDIA?) {

    }

}