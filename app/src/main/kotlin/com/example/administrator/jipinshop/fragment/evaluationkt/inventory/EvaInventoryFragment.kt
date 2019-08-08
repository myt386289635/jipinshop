package com.example.administrator.jipinshop.fragment.evaluationkt.inventory

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.EvaInventoryAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentEvaEvaBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaInventoryFragment : DBBaseFragment(), EvaInventoryView, OnLoadMoreListener, OnRefreshListener, View.OnClickListener {

    @Inject
    lateinit var mPresenter: EvaInventoryPresenter

    private lateinit var mBinding : FragmentEvaEvaBinding
    private lateinit var mList: MutableList<String>
    private lateinit var mTitleList: MutableList<String>
    private lateinit var mAdapter : EvaInventoryAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once){
            //先请求类目成功后再刷新列表
            mTitleList.clear()
            for (i in 0 .. 10){
                mTitleList.add("电动牙刷$i")
            }
            mPresenter.initTab(context,mBinding.tabLayout,mTitleList)
            mBinding.swipeToLoad.isRefreshing = true
            once = false
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_eva_eva,container,false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBinding.evaSend.visibility = View.VISIBLE
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        context?.let {
            mBinding.swipeToLoad.setBackgroundColor(it.resources.getColor(R.color.color_white))
            mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(it.resources.getColor(R.color.transparent))
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mTitleList = mutableListOf()
        mAdapter = EvaInventoryAdapter(mList,context!!)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.eva_send ->{
                ToastUtil.show("点击跳转到发布清单")
            }
        }
    }


    override fun onRefresh() {
        page = 1
        refersh = true
        dissRefresh()
        mList.clear()
        for (i in 0 .. 10){
            mList.add("")
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onLoadMore() {
        page++
        refersh = false
        dissLoading()
        for (i in 0 .. 10){
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

    override fun tabClick(position: Int) {
        ToastUtil.show("位置$position")
    }
}