package com.example.administrator.jipinshop.fragment.orderkt

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.adapter.KTMyOrderAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.OrderTBBean
import com.example.administrator.jipinshop.databinding.FragmentOrderBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe 全部订单、即将到账、已到账、失效订单
 */
class KTMyOrderFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, KTMyOrderView, View.OnClickListener, KTMyOrderAdapter.OnItem {

    @Inject
    lateinit var mPresenter: KTMyOrderPresenter

    private lateinit var mBinding : FragmentOrderBinding
    private lateinit var mList: MutableList<OrderTBBean.DataBean>
    private lateinit var mAdapter : KTMyOrderAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入
    private var source = "0"//来源标示：0全部,1京东,2淘宝，4拼多多  默认0全部
    private var status = 0 // 状态： 0全部,1即将到账，2已到账，3订单失效   默认0全部
    private var mDialog: Dialog? = null
    private lateinit var mTitles: MutableList<TextView>

    companion object{
        fun getInstance(source : String, status : Int): KTMyOrderFragment{
            val fragment = KTMyOrderFragment()
            val bundle = Bundle()
            bundle.putString("source", source)
            bundle.putInt("status", status)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once ){
            arguments?.let {
                source = it.getString("source","0")
                if (source != "0"){
                    mBinding.swipeToLoad.isRefreshing = true
                    once = false
                }
            }
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_F5F5F5))
        arguments?.let {
            source = it.getString("source","0")
            status = it.getInt("status",0)
        }
        mPresenter.setView(this)

        mTitles = mutableListOf()
        mTitles.add(mBinding.orderAll)
        mTitles.add(mBinding.orderFuture)
        mTitles.add(mBinding.orderReached)
        mTitles.add(mBinding.orderInvalid)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = KTMyOrderAdapter(context!!,mList)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        if (source == "0"){
            mBinding.swipeToLoad.post { mBinding.swipeToLoad.isRefreshing = true }
            once = false
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.order_all -> {
                //全部
                status = 0
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.order_future -> {
                //即将到账
                status = 1
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.order_reached -> {
                //已到账
                status = 2
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.order_invalid -> {
                //已失效
                status = 3
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getDate(page,source,status.toString(),this.bindToLifecycle())
    }

    override fun onRefresh() {
        initTitle()
        page = 1
        refersh = true
        mPresenter.getDate(page,source,status.toString(),this.bindToLifecycle())
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
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
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
            qsNet.setBackgroundColor(resources.getColor(R.color.color_F5F5F5))
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }

    override fun onSuccess(bean: OrderTBBean) {
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

    fun initTitle(){
        for (i in mTitles.indices){
            if (i == status){
                mTitles[i].setBackgroundResource(R.drawable.bg_e25838_5)
                mTitles[i].setTextColor(resources.getColor(R.color.color_E25838))
            }else{
                mTitles[i].setBackgroundResource(R.drawable.bg_989898)
                mTitles[i].setTextColor(resources.getColor(R.color.color_989898))
            }
        }
    }

    override fun onItemClick(position: Int) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.tbGoodsDetail(position, mList[position].otherGoodsId,
                "" + mList[position].source , this.bindToLifecycle())
    }

    override fun onNext(position : Int) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        startActivity(Intent(context, TBShoppingDetailActivity::class.java)
                .putExtra("otherGoodsId", mList[position].otherGoodsId)
                .putExtra("source",mList[position].source)
        )
    }

    override fun onCommonFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }
}