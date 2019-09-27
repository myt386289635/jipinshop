package com.example.administrator.jipinshop.fragment.freekt.pay

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
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity
import com.example.administrator.jipinshop.adapter.PayPendingAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.MyFreeBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe 待付款
 */
class PayPendingFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, PayPendingAdapter.OnClickListener, PayPendingView {

    @Inject
    lateinit var mPresenter: PayPendingPresenter

    private lateinit var mBinding : FragmentEvaluationCommonBinding
    private var page = 1
    private var refersh: Boolean = true
    private lateinit var mList: MutableList<MyFreeBean.DataBean>
    private lateinit var mAdapter: PayPendingAdapter
    private var mDialog: Dialog? = null

    companion object{
        fun getInstance() : PayPendingFragment {
            return PayPendingFragment()
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_F5F5F5))
        mPresenter.setView(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = PayPendingAdapter(mList,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getData("0",page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getData("0",page,this.bindToLifecycle())
    }

    override fun onSuccess(bean: MyFreeBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
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

    //进入详情
    override fun onItem(position: Int) {
        startActivity(Intent(context, FreeDetailActivity::class.java)
                .putExtra("id", mList[position].id)
        )
    }

    //购买
    override fun onBuy(position: Int) {
        if (TextUtils.isEmpty(mList[position].goodsId)) {
            ToastUtil.show("暂无商品链接")
            return
        }
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
        val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") {
            TaoBaoUtil.aliLogin {
                startActivity(Intent(context, TaoBaoWebActivity::class.java)
                        .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                        .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                )
            }
        } else {
            mPresenter.goodsBuyLink(mList[position].goodsId, this.bindToLifecycle())
        }
    }

    override fun onBuyLinkSuccess(bean: ImageBean) {
        TaoBaoUtil.openAliHomeWeb((context as Activity?), bean.data)
    }

    override fun onCommenFile(error: String?) {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter.cancelAllTimers()
        AlibcTradeSDK.destory()
    }

    override fun onResume() {
        super.onResume()
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

}