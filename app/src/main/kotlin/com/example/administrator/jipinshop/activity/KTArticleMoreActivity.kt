package com.example.administrator.jipinshop.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.classification.article.ArticleMoreView
import com.example.administrator.jipinshop.adapter.ArticleMoreAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.SucBean
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean
import com.example.administrator.jipinshop.databinding.ActivityArticleMoreBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/7/11
 * @Describe 小分类榜单——评测推荐列表更多 (kotlin写法)
 */
class KTArticleMoreActivity : BaseActivity(), View.OnClickListener, ArticleMoreAdapter.OnClickView, OnRefreshListener, OnLoadMoreListener, ArticleMoreView {

    @Inject
    lateinit var mPresenter: KTArticleMorePresenter
    private lateinit var  mBinding : ActivityArticleMoreBinding
    private var mList: MutableList<TopCategoryDetailBean.DataBean.RelatedArticleListBean> = mutableListOf()
    private lateinit var adapter : ArticleMoreAdapter
    private var page = 1
    private var refersh: Boolean = true

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

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleMoreAdapter(this,mList)
        adapter.setView(this)
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

    /**
     * 跳转到文章
     */
    override fun onClickArticle(position: Int) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return
        } else {
            val bigDecimal = BigDecimal(mList[position].pv)
            mList[position].pv = bigDecimal.toInt() + 1
            adapter.notifyDataSetChanged()
            ShopJumpUtil.jumpArticle(this, mList[position].articleId,
                    mList[position].type, mList[position].contentType)
        }
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.classiyArticleList(intent.getStringExtra("id"), page, this.bindToLifecycle<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>>())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.classiyArticleList(intent.getStringExtra("id"), page, this.bindToLifecycle<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>>())
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

    override fun onSuccess(bean: SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>) {
        if (refersh){
            dissRefresh()
            if (bean.data.size != 0){
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                adapter.notifyDataSetChanged()
            }else{
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据！~")
                mBinding.recyclerView.visibility = View.GONE
            }
        }else{
            dissLoading()
            if (bean.data.size != 0) {
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
}