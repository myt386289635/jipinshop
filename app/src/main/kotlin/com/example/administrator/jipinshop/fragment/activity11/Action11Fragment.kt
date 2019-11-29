package com.example.administrator.jipinshop.fragment.activity11

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.activity11.Action11Activity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity
import com.example.administrator.jipinshop.adapter.Action11Adapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.Action11Bean
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.databinding.FragmentActivity11Binding
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
 * @create 2019/10/29
 * @Describe 双十一模块活动
 */
class Action11Fragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, Action11View, Action11Adapter.OnClickItem, View.OnClickListener {

    @Inject
    lateinit var mPresenter: Action11Presenter

    private lateinit var mBinding: FragmentActivity11Binding
    private lateinit var mList: MutableList<Action11Bean.DataBean.GoodsDataListBean>
    private lateinit var mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>
    private lateinit var mGvListBeans: MutableList<Action11Bean.DataBean.CategoryListBean>
    private lateinit var mAdapter: Action11Adapter
    private var page = 1
    private var refersh: Boolean = true
    private var once: Boolean = true //第一次进入
    private var mDialog: Dialog? = null//加载框

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity11, container, false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_white))
        mPresenter.setView(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdListBeans = mutableListOf()
        mGvListBeans = mutableListOf()
        mAdapter = Action11Adapter(mList, context!!, true)
        mAdapter.setAd(mAdListBeans)
        mAdapter.setGv(mGvListBeans)
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
            R.id.home_sreach -> {
                ToastUtil.show("搜索")
            }
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setRefreshDate(page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.setDate(this.bindToLifecycle())
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

    override fun onSuccess(bean: Action11Bean) {
        dissRefresh()
        if (bean.data.goodsDataList != null && bean.data.goodsDataList.size != 0) {
            mBinding.netClude?.let {
                it.qsNet.visibility = View.GONE
            }
            mBinding.recyclerView.visibility = View.VISIBLE
            mGvListBeans.clear()
            mList.clear()
            mList.addAll(bean.data.goodsDataList)
            mGvListBeans.addAll(bean.data.categoryList)
            if (once){
                mAdListBeans.addAll(bean.data.adList)
                once = false
            }
            mAdapter.notifyDataSetChanged()
        } else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
            mBinding.recyclerView.visibility = View.GONE
        }
    }

    override fun onFile(error: String?) {
        dissRefresh()
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
        mBinding.recyclerView.visibility = View.GONE
        ToastUtil.show(error)
    }

    override fun onPageSuccess(bean: SucBean<Action11Bean.DataBean.GoodsDataListBean>) {
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

    override fun onPageFile(error: String?) {
        dissLoading()
        page--
        ToastUtil.show(error)
    }

    /**
     * 购买
     */
    override fun onClickBuy(pos: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") {
            TaoBaoUtil.aliLogin {
                startActivity(Intent(context, TaoBaoWebActivity::class.java)
                        .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                        .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                )
            }
        } else {
            mPresenter.goodsBuyLink(mList[pos].goods.goodsId, this.bindToLifecycle<ImageBean>())
        }
    }

    /**
     * 跳转详情
     */
    override fun onClickDetail(pos: Int) {
        startActivity(Intent(context, Action11Activity::class.java)
                .putExtra("title",mList[pos].ad.name)
                .putExtra("categoryId",mList[pos].ad.objectId)
        )
    }

    override fun onBuyLinkSuccess(bean: ImageBean) {
        TaoBaoUtil.openAliHomeWeb(context as Activity?, bean.data,bean.otherGoodsId)
    }

    override fun onFileCommentInsert(error: String?) {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
        ToastUtil.show(error)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        AlibcTradeSDK.destory()
    }

    override fun onResume() {
        super.onResume()
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }

}