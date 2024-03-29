package com.example.administrator.jipinshop.activity.home

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.share.ShareActivity
import com.example.administrator.jipinshop.adapter.TBSreachResultAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.databinding.ActivityHomeDetailBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog
import com.trello.rxlifecycle2.android.ActivityEvent
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import java.util.*
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe 首页模块 —— 专题页面
 */
class HomeDetailActivity : BaseActivity(), View.OnClickListener, TBSreachResultAdapter.OnItem, OnLoadMoreListener, OnRefreshListener, HomeDetailView, ShareBoardDialog.onShareListener {

    @Inject
    lateinit var mPresenter: HomeDetailPresenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil

    private lateinit var mBinding: ActivityHomeDetailBinding
    private var asc = ""
    private var orderByType = "0"
    private lateinit var mTextViews: MutableList<TextView>
    private lateinit var mList: MutableList<TBSreachResultBean.DataBean>
    private lateinit var mAdapter: TBSreachResultAdapter
    private var page = 1
    private var refresh: Boolean = true
    private var mDialog: Dialog? = null
    private var id = ""
    private var title = ""
    //分享面板
    private var mShareBoardDialog: ShareBoardDialog? = null
    private var shareUrl: String = ""
    //签到进入需要
    private var isSign = false //是否是签到过来的，默认是false
    private var timer: CountDownTimer? = object : CountDownTimer(30*1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            var downTimer = (millisUntilFinished / 1000).toInt()
            var html = "浏览商品赚钱中，<font color='#E25838'><b>"+ downTimer +"秒</b></font>后领取极币"
            mBinding.detailTime.text = Html.fromHtml(html)
        }

        override fun onFinish() {
            mBinding.detailTime.visibility = View.GONE
            mBinding.detailOk.visibility = View.VISIBLE
            mPresenter.taskFinish(this@HomeDetailActivity.bindUntilEvent(ActivityEvent.DESTROY))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_detail)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        id = intent.getStringExtra("id")
        title = intent.getStringExtra("title")
        isSign = intent.getBooleanExtra("isSign",false)
        mBinding.inClude?.let {
            it.titleTv.text = title
        }
        if (isSign){
            mBinding.detailTimeCotianer.visibility = View.VISIBLE
            mBinding.detailTime.visibility = View.VISIBLE
            mBinding.detailOk.visibility = View.GONE
            var html = "浏览商品赚钱中，<font color='#E25838'><b>30秒</b></font>后领取极币"
            mBinding.detailTime.text = Html.fromHtml(html)
            timer?.start()
        }else{
            mBinding.detailTimeCotianer.visibility = View.GONE
        }

        mTextViews = ArrayList()
        mTextViews.add(mBinding.titleZh)
        mTextViews.add(mBinding.titleJg)
        mTextViews.add(mBinding.titleBt)
        mTextViews.add(mBinding.titleXl)
        initTitle(0)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(this, 2)
        mList = mutableListOf()
        mAdapter = TBSreachResultAdapter(mList, this)
        mAdapter.setAppStatisticalUtil(appStatisticalUtil)
        mAdapter.setTransformer(this.bindUntilEvent(ActivityEvent.DESTROY))
        mAdapter.setId("zhuanti." + id + "_liebiao.")
        mAdapter.layoutType = 1//默认横向布局
        mAdapter.setOnItem(this)
        mBinding.swipeTarget.adapter = mAdapter

        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onRefresh() {
        refresh = true
        page = 1
        appStatisticalUtil.addEvent("tongyong_loding",this.bindToLifecycle())//专题页刷新统计
        mBinding.swipeTarget.scrollToPosition(0)
        mPresenter.getDate(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        refresh = false
        page++
        mPresenter.getDate(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onSuccess(bean: TBSreachResultBean) {
        shareUrl = bean.shareUrl
        if (refresh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
    }

    override fun onFile(error: String?) {
        if (refresh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.title_zh -> {
                orderByType = "0"
                asc = ""
                initTitle(0)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_jg -> {
                orderByType = "1"
                initTitle(1)
                asc = if (TextUtils.isEmpty(asc) || asc == "0") {
                    "1"
                } else {
                    "0"
                }
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_bt -> {
                orderByType = "2"
                asc = ""
                initTitle(2)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_xl -> {
                orderByType = "3"
                asc = ""
                initTitle(3)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.sreach_change -> {
                appStatisticalUtil.zhuanti_tab(4,id,this.bindToLifecycle())
                if (mAdapter.layoutType == 1) {
                    mAdapter.layoutType = 2//网格
                    mAdapter.notifyItemRangeChanged(0, mList.size)
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change1)
                } else {
                    mAdapter.layoutType = 1//横向
                    mAdapter.notifyItemRangeChanged(0, mList.size)
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change)
                }
            }
            R.id.detail_share ->{
                //分享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivityForResult(Intent(this, LoginActivity::class.java),301)
                    return
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog.getInstance("","")
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(supportFragmentManager,"ShareBoardDialog")
                    }
                }
            }
        }
    }

    override fun onItemShare(position: Int) {
        appStatisticalUtil.addEvent("zhuanti." + id + "_liebiao.zf",this.bindToLifecycle())
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        if (TextUtils.isEmpty(mList[position].source) || mList[position].source == "2"){
            TaoBaoUtil.openTB(this){
                startActivity(Intent(this, ShareActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                        .putExtra("source",mList[position].source)
                )
            }
        }else{
            startActivity(Intent(this, ShareActivity::class.java)
                    .putExtra("otherGoodsId", mList[position].otherGoodsId)
                    .putExtra("source",mList[position].source)
            )
        }
    }

    //初始化标题
    private fun initTitle(position: Int) {
        appStatisticalUtil.zhuanti_tab(position,id,this.bindToLifecycle())
        val drawable = resources.getDrawable(R.mipmap.sreach_price3)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        mBinding.titleJg.setCompoundDrawables(null, null, drawable, null)
        for (i in mTextViews.indices) {
            mTextViews[i].setTextColor(resources.getColor(R.color.color_9D9D9D))
            mTextViews[i].paint.isFakeBoldText = false
        }
        for (i in mTextViews.indices) {
            if (i == position) {
                if (position == 1) {
                    val upDrawable: Drawable
                    if (TextUtils.isEmpty(asc) || asc == "0") {
                        upDrawable = resources.getDrawable(R.mipmap.sreach_price)
                    } else {
                        upDrawable = resources.getDrawable(R.mipmap.sreach_price1)
                    }
                    upDrawable.setBounds(0, 0, upDrawable.minimumWidth, upDrawable.minimumHeight)
                    mBinding.titleJg.setCompoundDrawables(null, null, upDrawable, null)
                }
                mTextViews[i].setTextColor(resources.getColor(R.color.color_202020))
                mTextViews[i].paint.isFakeBoldText = true
            }
        }
    }

    fun dissRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing) {
            mBinding.swipeToLoad.isRefreshing = false
        }
        mDialog?.let {
            if(it.isShowing){
                it.dismiss()
            }
        }
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore) {
            mBinding.swipeToLoad.isLoadingMore = false
        }
    }

    override fun onResume() {
        super.onResume()
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        ShareUtils(this,share_media,mDialog)
                .shareWeb(this, shareUrl, "我在极品城看$title","款款商品有优惠，总有一款适合你，速来围观","",R.mipmap.share_logo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
        if (requestCode == 301 && resultCode == 200){
            //登陆成功后回来，需要刷新分享链接
            onRefresh()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
        timer?.cancel()
    }
}