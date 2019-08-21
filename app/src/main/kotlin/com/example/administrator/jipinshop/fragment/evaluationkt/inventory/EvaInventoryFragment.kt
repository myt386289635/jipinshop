package com.example.administrator.jipinshop.fragment.evaluationkt.inventory

import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.evakt.send.SubmitActivity
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.EvaInventoryAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaluationListBean
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.databinding.FragmentEvaEvaBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaInventoryFragment : DBBaseFragment(), EvaInventoryView, OnLoadMoreListener, OnRefreshListener, View.OnClickListener, EvaInventoryAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter: EvaInventoryPresenter

    private lateinit var mBinding : FragmentEvaEvaBinding
    private lateinit var mList: MutableList<EvaluationListBean.DataBean>
    private lateinit var mTitleList: MutableList<EvaluationTabBean.DataBean>
    private lateinit var mAdapter : EvaInventoryAdapter
    private var page = 1
    private var refersh: Boolean = true
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
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.eva_send ->{
                startActivity(Intent(context,SubmitActivity::class.java)
                        .putExtra("type","1")
                )
            }
        }
    }


    override fun onRefresh() {
        if (mBinding.netClude?.qsNet?.visibility == View.VISIBLE){
            mPresenter.setTab(this.bindToLifecycle())
        }else{
            page = 1
            refersh = true
            mPresenter.setDate(categoryId,page,this.bindToLifecycle())
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
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

    override fun onDateSuc(bean: EvaluationListBean) {
        if (refersh) {
            dissRefresh()
            mList.clear()
            mList.addAll(bean.data)
            mAdapter.notifyDataSetChanged()
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

    override fun onDateFile(error: String?) {
        if (refersh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onClickItem(position: Int) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return
        } else {
            val bigDecimal = BigDecimal(mList[position].pv)
            mList[position].pv = (bigDecimal.toInt() + 1).toString()
            mAdapter.notifyDataSetChanged()
            ShopJumpUtil.jumpArticle(context, mList[position].articleId,
                     mList[position].type, mList[position].contentType)
        }
    }

    override fun onClickUserinfo(userId: String) {
        startActivity(Intent(context, UserActivity::class.java)
                .putExtra("userid", userId)
        )
    }
}