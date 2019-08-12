package com.example.administrator.jipinshop.fragment.evaluationkt.ieva

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.EvaEvaAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.databinding.FragmentEvaEvaBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaEvaFragment : DBBaseFragment(), OnRefreshListener, EvaEvaView, EvaEvaAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter : EvaEvaPresenter

    private lateinit var mBinding : FragmentEvaEvaBinding
    private lateinit var mList1: MutableList<EvaEvaBean.DataBean>//开箱评测
    private lateinit var mList2: MutableList<EvaEvaBean.List2Bean>//对比评测
    private lateinit var mTitleList: MutableList<EvaluationTabBean.DataBean>
    private lateinit var mAdapter : EvaEvaAdapter
    private var once : Boolean = true //第一次进入
    private var categoryId : String = ""

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once){
            //先请求类目成功后再刷新列表
            mPresenter.setTab(this.bindToLifecycle())
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
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.isLoadMoreEnabled = false
    }

    override fun onRefresh() {
        if (mBinding.netClude?.qsNet?.visibility == View.VISIBLE){
            mPresenter.setTab(this.bindToLifecycle())
        }else{
            mPresenter.getDate(categoryId,this.bindToLifecycle())
        }
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
        categoryId = mTitleList[position].categoryId
        onRefresh()
    }

    override fun onSuccess(bean: EvaluationTabBean) {
        once = false//第一次请求后改变
        mBinding.netClude?.run {
            qsNet.visibility = View.GONE
        }
        mTitleList.clear()
        mTitleList.addAll(bean.data)
        mPresenter.initTab(context,mBinding.tabLayout,mTitleList)
        categoryId = mTitleList[0].categoryId
        if (mBinding.swipeToLoad.isRefreshing) {
            onRefresh()
        }else{
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onFile(error: String?) {
        dissRefresh()
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
        ToastUtil.show(error)
        once = false//第一次请求后改变
    }

    override fun onDateSuc(bean: EvaEvaBean) {
        dissRefresh()
        mList1.clear()
        mList2.clear()
        mList1.addAll(bean.data)
        mList2.addAll(bean.list2)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDateFile(error: String?) {
        dissRefresh()
        ToastUtil.show(error)
    }

    /**
     * 跳转到文章详情
     */
    override fun onClickItem(articleId :String , type :String , contentType : Int) {
        mAdapter.notifyDataSetChanged()
        ShopJumpUtil.jumpArticle(context, articleId,
                type, contentType)
    }

    /**
     * 个人详情页
     */
    override fun onClickUserinfo(userId: String) {
        ToastUtil.show(userId)
    }

    /**
     * 跳转到开箱评测
     */
    override fun onClickUnbox() {
        ToastUtil.show("跳转到开箱评测")
    }

    /**
     * 跳转到对比评测
     */
    override fun onClickComparison() {
        ToastUtil.show("跳转到对比评测")
    }

}