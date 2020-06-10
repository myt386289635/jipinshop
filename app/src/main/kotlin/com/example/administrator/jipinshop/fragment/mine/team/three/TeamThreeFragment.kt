package com.example.administrator.jipinshop.fragment.mine.team.three

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity
import com.example.administrator.jipinshop.adapter.TeamTwoAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentTeam1Binding
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/6/9
 * @Describe
 */
class TeamThreeFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, View.OnClickListener, TeamTwoAdapter.OnItem {

    @Inject
    lateinit var mPresenter: TeamThreePresenter

    private lateinit var mBinding: FragmentTeam1Binding
    private var once = arrayOf(true)
    private var page = 1
    private var refersh: Boolean = true
    private lateinit var mList: MutableList<String>
    private lateinit var mAdapter: TeamTwoAdapter
    private var mDialog: Dialog? = null

    companion object{
        @JvmStatic
        fun getInstance(): TeamThreeFragment {
            return TeamThreeFragment()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once[0]) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = true
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = true
            }
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team1,container,false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAdapter = TeamTwoAdapter(mList,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad, (activity as TeamActivity).bar, once)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.team_invation -> {
                startActivity(Intent(context, InvitationNewActivity::class.java))
            }
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false

        stopLoading()
        for (i in 0 until 10){
            mList.add("")
        }
        mAdapter.notifyDataSetChanged()
        once[0] = false
    }

    override fun onRefresh() {
        page = 1
        refersh = true

        stopResher()
        mList.clear()
        for (i in 0 until 10){
            mList.add("")
        }
        mAdapter.notifyDataSetChanged()
        once[0] = false
    }

    //下级详情dialog
    override fun onItem(position: Int) {
        DialogUtil.userDetailDialog(context)
    }

    //错误页面
    fun initError() {
        mBinding.recyclerView.visibility = View.GONE
        mBinding.nestedScrollview.visibility = View.VISIBLE
    }

    //停止刷新
    fun stopResher() {
        if (mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    //停止加载
    fun stopLoading() {
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

    fun refresh(){
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        onRefresh()
    }

}