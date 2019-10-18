package com.example.administrator.jipinshop.fragment.evaluationkt.zcompare

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.EvaCompareAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/10/17
 * @Describe 横评
 */
class EvaCompareFragment :DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, EvaCompareAdapter.OnClickItem, EvaCompareView {

    @Inject
    lateinit var mPresenter: EvaComparePresenter

    private lateinit var mBinding: FragmentEvaluationCommonBinding
    private lateinit var mList: MutableList<EvaEvaBean.List2Bean>
    private lateinit var mAdapter: EvaCompareAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once ){
            mBinding.swipeToLoad.isRefreshing = true
            once = false
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_white))
        mPresenter.setView(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = EvaCompareAdapter(mList,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setDate(page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.setDate(page,this.bindToLifecycle())
    }

    override fun onSuccess(bean: SucBean<EvaEvaBean.List2Bean>) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                if (bean.count != 0){
                    ToastUtil.showTop("为您更新"+bean.count+"篇文章",context)
                }else{
                    ToastUtil.showTop("刷新成功",context)
                }
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

    override fun onClickItem(articleId: String, type: String, contentType: Int) {
        mAdapter.notifyDataSetChanged()
        ShopJumpUtil.jumpArticle(context, articleId,
                type, contentType)
    }

    override fun onClickUserinfo(userId: String) {
        startActivity(Intent(context, UserActivity::class.java)
                .putExtra("userid", userId)
        )
    }
}