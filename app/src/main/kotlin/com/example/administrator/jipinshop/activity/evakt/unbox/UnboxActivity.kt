package com.example.administrator.jipinshop.activity.evakt.unbox

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.UnboxAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.databinding.ActivityArticleMoreBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/12
 * @Describe 开箱评测
 */
class UnboxActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, OnLoadMoreListener, UnboxView, UnboxAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter : UnboxPresenter

    private lateinit var  mBinding : ActivityArticleMoreBinding
    private lateinit var  adapter : UnboxAdapter
    private lateinit var mList : MutableList<EvaEvaBean.DataBean>
    private var page = 1
    private var refersh: Boolean = true
    private var categoryId : String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_article_more)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = intent.getStringExtra("title")
        }
        categoryId = intent.getStringExtra("categoryId")

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        adapter = UnboxAdapter(this,mList)
        adapter.setClick(this)
        mBinding.recyclerView.adapter = this.adapter

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back  ->{
                finish()
            }
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setDate(categoryId,page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
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

    override fun onSuccess(bean: SucBean<EvaEvaBean.DataBean>) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                adapter.notifyDataSetChanged()
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
                mBinding.recyclerView.visibility = View.GONE
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                adapter.notifyDataSetChanged()
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

    override fun onClickItem(articleId: String, type: String, contentType: Int) {
        adapter.notifyDataSetChanged()
        ShopJumpUtil.jumpArticle(this, articleId,
                type, contentType)
    }

    override fun onClickUserinfo(userId: String) {
        startActivity(Intent(this, UserActivity::class.java)
                .putExtra("userid", userId)
        )
    }

}