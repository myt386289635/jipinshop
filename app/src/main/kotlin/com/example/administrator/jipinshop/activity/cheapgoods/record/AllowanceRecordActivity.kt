package com.example.administrator.jipinshop.activity.cheapgoods.record

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity
import com.example.administrator.jipinshop.adapter.AllowanceRecordAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.AllowanceRecordBean
import com.example.administrator.jipinshop.databinding.ActivityArticleMoreBinding
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/7
 * @Describe 津贴使用记录
 */
class AllowanceRecordActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, OnLoadMoreListener, AllowanceRecordView, AllowanceRecordAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter: AllowanceRecordPresenter

    private lateinit var  mBinding : ActivityArticleMoreBinding
    private lateinit var mList: MutableList<AllowanceRecordBean.DataBean>
    private lateinit var mAdapter: AllowanceRecordAdapter
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
            it.titleTv.text = "津贴使用记录"
        }
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_F5F5F5))

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = AllowanceRecordAdapter(mList,this)
        mAdapter.setOnClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.post { mBinding.swipeToLoad.isRefreshing = true }
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

    override fun onSuccess(bean: AllowanceRecordBean) {
        if (refersh) {
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back  ->{
                finish()
            }
        }
    }

    override fun onItem(pos: Int) {
        var specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") {
            TaoBaoUtil.aliLogin { topAuthCode ->
                startActivity(Intent(this, TaoBaoWebActivity::class.java)
                        .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                        .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                )
            }
        } else {
            TaoBaoUtil.openAliHomeWeb(this, mList[pos].allowanceUrl, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
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