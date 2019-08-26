package com.example.administrator.jipinshop.fragment.publishkt.question.unpass

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.UnPassQuesAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.QuestionsBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/26
 * @Describe 我的发布——问答  审核中、 未通过
 */
class UnPassQuesFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, UnPassQuesView, UnPassQuesAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter: UnPassQuesPresenter

    private lateinit var mBinding: FragmentEvaluationCommonBinding
    private lateinit var mAdapter: UnPassQuesAdapter
    private lateinit var mList: MutableList<QuestionsBean.DataBean>
    private var page = 1
    private var refersh: Boolean = true
    private var once: Boolean = true //第一次进入
    private var type = ""//标志页面    0:审核中   -1：未通过
    private var mDialog: Dialog? = null

    companion object {
        fun getInstance(type: String): UnPassQuesFragment {
            val fragment = UnPassQuesFragment()
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
        mPresenter.setView(this)
        arguments?.let {
            type = it.getString("type", "")
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = UnPassQuesAdapter(mList, context!!,type)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setDate(page,type,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.setDate(page,type,this.bindToLifecycle())
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

    override fun onSuccess(bean: QuestionsBean) {
        if (refersh){
            dissRefresh()
            if (bean.data.size != 0){
                mBinding.netClude?.qsNet?.visibility = View.GONE
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
        DialogUtil.listingDetele(context,"话题一旦删除，将无法找回") {
            mDialog = ProgressDialogView().createLoadingDialog(context, "")
            mDialog?.show()
            mPresenter.questionDelete(position,mList[position].id,this.bindToLifecycle())
        }
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