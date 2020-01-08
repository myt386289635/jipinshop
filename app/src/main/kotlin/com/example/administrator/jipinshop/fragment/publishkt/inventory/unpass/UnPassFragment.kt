package com.example.administrator.jipinshop.fragment.publishkt.inventory.unpass

import android.app.Dialog
import android.content.Intent
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
import com.example.administrator.jipinshop.activity.evakt.send.SubmitActivity
import com.example.administrator.jipinshop.adapter.UnPassAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.recyclerView.SwipeItemLayout
import javax.inject.Inject


/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe 我的发布————草稿箱 0 、未通过 -1
 */
class UnPassFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, UnPassView, UnPassAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter: UnPassPresenter
    private lateinit var mBinding: FragmentEvaluationCommonBinding
    private var type = ""//标志页面    0:草稿箱   -1：未通过
    private var once: Boolean = true //第一次进入
    private var page = 1
    private var refersh: Boolean = true
    private lateinit var mList: MutableList<SreachResultArticlesBean.DataBean>
    private lateinit var mAdapter: UnPassAdapter
    private var mDialog: Dialog? = null

    companion object {
        fun getInstance(type: String): UnPassFragment {
            val fragment = UnPassFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once) {
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
        arguments?.let {
            type = it.getString("type", "")
        }
        mPresenter.setView(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = UnPassAdapter(mList,type,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getDate(page,type,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getDate(page,type,this.bindToLifecycle())
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

    override fun onSuccess(bean: SreachResultArticlesBean) {
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

    override fun onClickDelete(position: Int) {
        DialogUtil.listingDetele(context,"文章一旦删除，将无法找回","确认","取消",false) {
            mDialog = ProgressDialogView().createLoadingDialog(context, "")
            mDialog?.show()
            mPresenter.ListingDelete(position,mList[position].articleId,this.bindToLifecycle())
        }
    }

    override fun onClickEdit(position: Int) {
        var remark = ""
        if (!TextUtils.isEmpty(mList[position].remark)){
            remark = mList[position].remark
        }
        startActivity(Intent(context, SubmitActivity::class.java)
                .putExtra("type","2")
                .putExtra("articleId",mList[position].articleId)
                .putExtra("remark",remark)
        )
    }

    override fun onClickSend(position: Int) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.submitReport(position,mList[position].content,mList[position].title,mList[position].img,mList[position].articleId,this.bindToLifecycle())
    }

    override fun onSave(position: Int) {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
        if(position != mList.size){ // 如果移除的是最后一个，忽略
            mAdapter.notifyItemRangeChanged(position, mList.size - position)
        }
        ToastUtil.show("发布成功")
    }

    override fun onFileCommon(error: String?) {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onDelete(position: Int) {
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
        if(position != mList.size){ // 如果移除的是最后一个，忽略
            mAdapter.notifyItemRangeChanged(position, mList.size - position)
        }
        ToastUtil.show("删除成功")
    }

}