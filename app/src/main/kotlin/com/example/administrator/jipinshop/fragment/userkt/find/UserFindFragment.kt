package com.example.administrator.jipinshop.fragment.userkt.find

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.SreachArticleAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.databinding.FragmentUserfindBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/19
 * @Describe 个人主页里的  评测与清单
 */
class UserFindFragment : DBBaseFragment(), SreachArticleAdapter.OnItem, OnRefreshListener, OnLoadMoreListener, UserFindView {

    @Inject
    lateinit var mPresenter: UserFindPresenter

    private lateinit var mBinding: FragmentUserfindBinding
    private var userId = ""
    private var type = ""
    private lateinit var mList: MutableList<SreachResultArticlesBean.DataBean>
    private lateinit var mAdapter: SreachArticleAdapter
    private var once = arrayOf(true)//记录第一次进入页面标示
    private var page = 1
    private var refersh: Boolean = true

    companion object{
        fun getInstance(userId: String, type: String): UserFindFragment {
            val fragment = UserFindFragment()
            val bundle = Bundle()
            bundle.putString("targetUserId", userId)
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once[0]){
            if (!TextUtils.isEmpty(type) && type == "2"){
                mBinding.swipeToLoad.isRefreshing = true
            }
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_userfind, container, false)
        return mBinding.root
    }

    override fun initView() {
        arguments?.let {
            type = it.getString("type","")
            userId = it.getString("targetUserId","")
        }
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = SreachArticleAdapter(mList, context)
        mAdapter.setOnItem(this)
        mAdapter.setType(type)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad, (activity as UserActivity).getBar(), once)
        if (type == "4"){
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onItem(position: Int) {
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

    override fun onClickUserInfo(userId: String?) {

    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getDate(page,userId,type,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getDate(page,userId,type,this.bindToLifecycle())
    }

    override fun onSuccess(bean: SreachResultArticlesBean) {
        if (refersh){
            dissRefresh()
            if (bean.data.size != 0){
                mBinding.nestedScrollview.visibility = View.GONE
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            }else{
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据！~")
                mBinding.recyclerView.visibility = View.GONE
            }
        }else{
            dissLoading()
            if (bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
        if (refersh) {
            if (once[0]) {
                once[0] = false
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
        mBinding.inClude?.run {
            mBinding.nestedScrollview.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }

}