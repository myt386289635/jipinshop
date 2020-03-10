package com.example.administrator.jipinshop.activity.minekt.welfare

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTWelfareAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.WelfareBean
import com.example.administrator.jipinshop.databinding.ActivityArticleMoreBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/9
 * @Describe 福利兑换
 */
class WelfareActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, WelfareView {

    @Inject
    lateinit var mPresenter: WelfarePresenter

    private lateinit var mBinding : ActivityArticleMoreBinding
    private lateinit var mList: MutableList<WelfareBean.DataBean>
    private lateinit var mAdapter: KTWelfareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_more)
        mBinding.listener = this
        mBinding.detailNode.visibility = View.VISIBLE
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "福利兑换"
        }

        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_F5F5F5))
        mBinding.recyclerView.setBackgroundColor(resources.getColor(R.color.transparent))
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = KTWelfareAdapter(mList,this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.isLoadMoreEnabled = false
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back  ->{
                finish()
            }
        }
    }

    override fun onRefresh() {
       mPresenter.getDate(this.bindToLifecycle())
    }

    override fun onSuccess(bean: WelfareBean) {
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