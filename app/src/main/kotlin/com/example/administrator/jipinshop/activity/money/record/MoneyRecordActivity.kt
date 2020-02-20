package com.example.administrator.jipinshop.activity.money.record

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTMoneyRecordAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityMoneyRecordBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/20 10:02
 * Description： 红包提现记录页面
 */
class MoneyRecordActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @Inject
    lateinit var mPresenter: MoneyRecordPresenter

    private lateinit var mBinding: ActivityMoneyRecordBinding
    private lateinit var mList: MutableList<String>
    private lateinit var mAdapter: KTMoneyRecordAdapter
    private var page = 1
    private var refersh: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_record)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        initView()
    }

    private fun initView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = KTMoneyRecordAdapter(mList,this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
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

    override fun onLoadMore() {
        page++
        refersh = false

        dissLoading()
        for (i in 0 until 10 ){
            mList.add("")
        }
        mAdapter.notifyDataSetChanged()
        mBinding.swipeToLoad.isLoadMoreEnabled = false
    }

    override fun onRefresh() {
        page = 1
        refersh = true

        dissRefresh()
        mBinding.netClude?.let {
            it.qsNet.visibility = View.GONE
        }
        mBinding.recyclerView.visibility = View.VISIBLE
        mList.clear()
        for (i in 0 until 10 ){
            mList.add("")
        }
        mAdapter.notifyDataSetChanged()
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
}