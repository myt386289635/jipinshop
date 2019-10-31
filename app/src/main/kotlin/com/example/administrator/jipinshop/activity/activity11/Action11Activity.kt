package com.example.administrator.jipinshop.activity.activity11

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity
import com.example.administrator.jipinshop.adapter.Activity11Adapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.Activity11Bean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.databinding.ActivityArticleMoreBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog
import com.umeng.socialize.bean.SHARE_MEDIA
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/10/30
 * @Describe 双十一活动专题页面
 */
class Action11Activity : BaseActivity(), View.OnClickListener, OnRefreshListener, OnLoadMoreListener, Activity11Adapter.OnClickItem, Activity11View, ShareBoardDialog.onShareListener {

    @Inject
    lateinit var mPresenter: Activity11Presenter

    private lateinit var  mBinding : ActivityArticleMoreBinding
    private lateinit var mList: MutableList<Activity11Bean.DataBean>
    private lateinit var mAdapter: Activity11Adapter
    private var page = 1
    private var refersh: Boolean = true
    private var categoryId : String  = ""
    private var mDialog: Dialog? = null//加载框
    private var mShareBoardDialog: ShareBoardDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_more)
        mBinding.listener = this
        mBinding.detailShare.visibility = View.VISIBLE
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = intent.getStringExtra("title") + "双11专区"
        }
        categoryId = intent.getStringExtra("categoryId")

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = Activity11Adapter(mList,this)
        mAdapter.setOnClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.detail_share -> {
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog.getInstance("", "")
                    mShareBoardDialog!!.setOnShareListener(this)
                }
                if (!mShareBoardDialog!!.isAdded) {
                    mShareBoardDialog!!.show(supportFragmentManager, "ShareBoardDialog")
                }
            }
            R.id.title_back -> {
                finish()
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        var shareTitle = "双十一别傻了，"+ intent.getStringExtra("title") +"像我这样下单才最低价！"
        var shareContent = "官方正品抄底价，加券！加现金！额外补贴现金转支付宝~"
        var shareUrl = RetrofitModule.H5_URL + "share/category11.html?id=" + categoryId
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        ShareUtils(this, share_media,mDialog)
                .shareWeb(this, shareUrl, shareTitle, shareContent, "", R.mipmap.share_logo11)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setDate(categoryId,page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.setDate(categoryId,page,this.bindToLifecycle())
    }

    fun dissRefresh() {
        if (mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad.isLoadingMore) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled) {
                mBinding.swipeToLoad.isLoadMoreEnabled = true
                mBinding.swipeToLoad.isLoadingMore = false
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                mBinding.swipeToLoad.isLoadingMore = false
            }
        }
    }

    fun initError(id: Int, title: String, content: String) {
        mBinding.netClude?.run {
            qsNet.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }

    override fun onClickBuy(pos: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.show()
        val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") {
            TaoBaoUtil.aliLogin {
                startActivity(Intent(this, TaoBaoWebActivity::class.java)
                        .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                        .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                )
            }
        } else {
            mPresenter.goodsBuyLink(mList[pos].goodsId, this.bindToLifecycle<ImageBean>())
        }
    }

    override fun onBuyLinkSuccess(bean: ImageBean) {
        TaoBaoUtil.openAliHomeWeb(this, bean.data,bean.otherGoodsId)
    }

    override fun onFileCommentInsert(error: String?) {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
        ToastUtil.show(error)
    }

    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }

    override fun onRestart() {
        super.onRestart()
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }

    override fun onSuccess(bean: Activity11Bean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.setAd(bean.ad)
                mAdapter.notifyDataSetChanged()
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
                mBinding.recyclerView.visibility = View.GONE
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
    }

    override fun onFile(error: String?) {
        if (refersh) {
            dissRefresh()
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
            mBinding.recyclerView.visibility = View.GONE
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }
}