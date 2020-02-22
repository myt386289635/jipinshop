package com.example.administrator.jipinshop.activity.money.record

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTMoneyRecordAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.MoneyRecordBean
import com.example.administrator.jipinshop.databinding.ActivityMoneyRecordBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/20 10:02
 * Description： 红包提现记录页面
 */
class MoneyRecordActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, MoneyRecordView {

    @Inject
    lateinit var mPresenter: MoneyRecordPresenter

    private lateinit var mBinding: ActivityMoneyRecordBinding
    private lateinit var mList: MutableList<MoneyRecordBean.DataBean>
    private lateinit var mAdapter: KTMoneyRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_record)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "提现记录"
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = KTMoneyRecordAdapter(mList,this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post { mBinding.swipeToLoad.isRefreshing = true }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.title_back -> {
                finish()
            }
            R.id.record_help -> {
                ToastUtil.show("帮助")
            }
        }
    }

    override fun onRefresh() {
        mPresenter.setDate(this.bindToLifecycle())
    }

    override fun onSuccess(bean: MoneyRecordBean) {
        dissRefresh()
        if (bean.data != null && bean.data.size != 0) {
            mBinding.netClude?.let {
                it.qsNet.visibility = View.GONE
            }
            mBinding.recyclerView.visibility = View.VISIBLE
            mList.clear()
            mList.addAll(bean.data)
            mAdapter.notifyDataSetChanged()
        }else{
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

    fun initError(id: Int, title: String, content: String) {
        mBinding.netClude?.run {
            qsNet.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }
}