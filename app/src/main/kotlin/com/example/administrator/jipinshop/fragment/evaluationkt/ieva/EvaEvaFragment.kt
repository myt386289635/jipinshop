package com.example.administrator.jipinshop.fragment.evaluationkt.ieva

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.EvaEvaAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentEvaEvaBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaEvaFragment : DBBaseFragment(), OnRefreshListener, EvaEvaView {

    @Inject
    lateinit var mPresenter : EvaEvaPresenter

    private lateinit var mBinding : FragmentEvaEvaBinding
    private lateinit var mList1: MutableList<String>//开箱评测
    private lateinit var mList2: MutableList<String>//对比评测
    private lateinit var mTitleList: MutableList<String>
    private lateinit var mAdapter : EvaEvaAdapter
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
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        context?.let {
            mBinding.swipeToLoad.setBackgroundColor(it.resources.getColor(R.color.color_white))
            mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(it.resources.getColor(R.color.transparent))
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList1 = mutableListOf()
        mList2 = mutableListOf()
        mTitleList = mutableListOf()
        mAdapter = EvaEvaAdapter(mList1,mList2,context!!)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.isLoadMoreEnabled = false
    }

    override fun onRefresh() {
        dissRefresh()
        mList1.clear()
        mList2.clear()
        for (i in 0 .. 10){
            mList1.add("")
        }
        for (i in 0 .. 1){
            mList2.add("")
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