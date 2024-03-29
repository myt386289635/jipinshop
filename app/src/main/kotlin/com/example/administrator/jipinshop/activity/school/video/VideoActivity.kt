package com.example.administrator.jipinshop.activity.school.video

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.bean.VideoBean
import com.example.administrator.jipinshop.bean.VoteBean
import com.example.administrator.jipinshop.databinding.ActivityVideoBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2
import com.example.administrator.jipinshop.view.dialog.VideoPop
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.umeng.socialize.bean.SHARE_MEDIA
import java.math.BigDecimal
import javax.inject.Inject


/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe 商学院视频详情页
 */
class VideoActivity : BaseActivity(), View.OnClickListener, VideoView, ShareBoardDialog2.onShareListener, VideoPop.OnClick {

    @Inject
    lateinit var mPresenter: VideoPresenter
    private lateinit var binding: ActivityVideoBinding
    private var mDialog: Dialog? = null
    private var courseId : String = ""
    private var orientationUtils : OrientationUtils? = null
    private var mShareBoardDialog: ShareBoardDialog2? = null
    private var mUrl = "" //记录当前视频的地址，用来下载视频和判断该视频在列表中的位置
    //标志：是否点赞过此视频  false:没有
    private var isSnap = false
    private var liked: String = "0"//点赞数
    private var mShare: String = "0"
    //pop菜单
    private lateinit var mPop: VideoPop
    private var SendSet = 0 //播放的位置
    private lateinit var mSendTitle : MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean> //视频列表
    //分享的东西
    private var shareInfo: VideoBean.ShareInfoBean? = null

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
        mPop = VideoPop(this,this)
        mSendTitle = mutableListOf()

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
                mPop.show(binding.videoBottomContainer,mSendTitle,SendSet)
            }
            R.id.video_likeContainer -> {
                //点赞
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点")
                    return
                } else {
                    if (isSnap) {
                        //点赞过了
                        mPresenter.snapDelete(courseId, this.bindToLifecycle<VoteBean>())
                    } else {
                        //没有点赞
                        mPresenter.snapInsert(courseId, this.bindToLifecycle<VoteBean>())
                    }
                }
            }
            R.id.video_shareContainer -> {
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(1)
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        mPresenter.addShareCourse(courseId,this.bindToLifecycle())
                        it.show(supportFragmentManager,"ShareBoardDialog2")
                        mShare = "" + (BigDecimal(mShare).toInt() + 1)
                        binding.videoShare.text = initGoods(mShare)
                        mSendTitle[SendSet].share = mShare
                    }
                }
            }
        }
    }

    override fun onSuccess(bean: VideoBean) {
        mPresenter.initCourses(bean.data.categoryId,this.bindToLifecycle())
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        //配置视频播放
        mUrl = bean.data.video
        orientationUtils = OrientationUtils(this, binding.itemPlayer)
        orientationUtils?.isEnable = false //初始化不打开外部的旋转
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
        mShare = bean.data.share
        liked = bean.data.liked
        binding.videoLike.text = initGoods(liked)
        binding.videoShare.text = initGoods(mShare)
        if (bean.data.isLike == "1"){
            isSnap = true
            binding.videoLikeImage.setImageResource(R.mipmap.video_liked)
        }else{
            isSnap = false
            binding.videoLikeImage.setImageResource(R.mipmap.video_like)
        }
        shareInfo = bean.shareInfo
    }

    //初始化课程列表
    override fun onList(bean: SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>) {
        mSendTitle.clear()
        mSendTitle.addAll(bean.data)
        for (i in mSendTitle.indices){
            if (mUrl == mSendTitle[i].video){
                SendSet = i
                break
            }
        }
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    //点赞
    override fun onVote(successBean: VoteBean) {
        if(successBean.code == 0){
            isSnap = true
            liked = "" + (BigDecimal(liked).toInt() + 1)
            binding.videoLike.text =  initGoods(liked)
            mSendTitle[SendSet].liked = liked
            mSendTitle[SendSet].isLike = "1"
            binding.videoLikeImage.setImageResource(R.mipmap.video_liked)
            ToastUtil.show("点赞成功")
        }else{
            //602
            startActivity(Intent(this, LoginActivity::class.java))
            SPUtils.getInstance(CommonDate.USER).clear()
        }
    }

    //取消点赞
    override fun onDelVote(successBean: VoteBean) {
        if(successBean.code == 0){
            isSnap = false
            liked = "" + (BigDecimal(liked).toInt() - 1)
            binding.videoLike.text = initGoods(liked)
            mSendTitle[SendSet].liked = liked
            mSendTitle[SendSet].isLike = "0"
            binding.videoLikeImage.setImageResource(R.mipmap.video_like)
            ToastUtil.show("取消点赞")
        }else{
            //602
            startActivity(Intent(this, LoginActivity::class.java))
            SPUtils.getInstance(CommonDate.USER).clear()
        }
    }

    //切换视频
    override fun onPopItemOnClick(pos: Int) {
        if (SendSet != pos){
            SendSet = pos
            binding.itemPlayer.onVideoReset()
            binding.itemPlayer.loadCoverImage(mSendTitle[pos].video, R.color.transparent)
            binding.itemPlayer.setUp(mSendTitle[pos].video, true,mSendTitle[pos].title)
            binding.itemPlayer.startPlayLogic()
            //初始化其他数据
            courseId = mSendTitle[pos].id
            mUrl = mSendTitle[pos].video
            mShare = mSendTitle[pos].share
            liked = mSendTitle[pos].liked
            binding.videoLike.text = initGoods(liked)
            binding.videoShare.text = initGoods(mShare)
            if (mSendTitle[pos].isLike == "1"){
                isSnap = true
                binding.videoLikeImage.setImageResource(R.mipmap.video_liked)
            }else{
                isSnap = false
                binding.videoLikeImage.setImageResource(R.mipmap.video_like)
            }
        }
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
        mPresenter.downLoadVideo(this,mUrl,this.bindToLifecycle())
    }

    override fun onVideo() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show("保存完成")
    }

    //分享
    override fun share(share_media: SHARE_MEDIA?) {
        shareInfo?.let {
            mDialog = ProgressDialogView().createLoadingDialog(this, "")
            ShareUtils(this,share_media,mDialog)
                    .shareWeb(this, it.link, it.title, it.desc, it.imgUrl,R.mipmap.share_logo)
        }
    }

    //设置点赞数、分享数
    fun initGoods(vote: String) : String{
        var result = ""
        var voteNum = BigDecimal(vote).toDouble()
        if (voteNum >= 10000){
            var voteA = BigDecimal(vote)
            var voteB = BigDecimal("10000")
            result = voteA.divide(voteB,1,BigDecimal.ROUND_HALF_DOWN).toPlainString() + "万"
        }else {
            result = BigDecimal(vote).setScale(1,BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString()
        }
        return result
    }
}