package com.example.administrator.jipinshop.fragment.userkt.article

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.classification.questions.detail.QuestionDetailActivity
import com.example.administrator.jipinshop.activity.minekt.UserActivity
import com.example.administrator.jipinshop.adapter.QuestionsAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.QuestionsBean
import com.example.administrator.jipinshop.databinding.FragmentUserfindBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/20
 * @Describe 个人主页问答列表
 */
class UserArticleFragment : DBBaseFragment(), QuestionsAdapter.OnClickView, OnRefreshListener, OnLoadMoreListener, UserArticleView {

    @Inject
    lateinit var mPresenter: UserArticlePresenter

    private lateinit var mBinding: FragmentUserfindBinding
    private var once = arrayOf(true)//记录第一次进入页面标示
    private lateinit var mAdapter: QuestionsAdapter
    private lateinit var mList: MutableList<QuestionsBean.DataBean>
    private var userId = ""
    private var page = 1
    private var refersh: Boolean = true

    companion object{
        fun getInstance(userId: String): UserArticleFragment {
            val fragment = UserArticleFragment()
            val bundle = Bundle()
            bundle.putString("targetUserId", userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once[0]){
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_userfind, container, false)
        return mBinding.root
    }

    override fun initView() {
        arguments?.let {
            userId = it.getString("targetUserId","")
        }
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = QuestionsAdapter(mList, context)
        mAdapter.setView(this)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad, (activity as UserActivity).getBar(), once)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getDate(page,userId,"3",this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getDate(page,userId,"3",this.bindToLifecycle())
    }

    override fun onClickArticle(position: Int) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return
        } else {
            startActivity(Intent(context, QuestionDetailActivity::class.java)
                    .putExtra("date", mList[position]))
        }
    }

    override fun onSuccess(bean: QuestionsBean) {
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