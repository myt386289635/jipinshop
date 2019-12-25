package com.example.administrator.jipinshop.fragment.home.commen

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTHomeCommenAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.databinding.FragmentKtMainBinding
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe 首页 其他通用页面
 */
class KTHomeCommenFragment : DBBaseFragment(), OnLoadMoreListener, OnRefreshListener, KTHomeCommenView, KTHomeCommenAdapter.OnItem {

    @Inject
    lateinit var mPresenter: KTHomeCommenPresenter

    private lateinit var mBinding : FragmentKtMainBinding
    private lateinit var mList: MutableList<TBSreachResultBean.DataBean>
    private lateinit var mAdListBeans: MutableList<TbCommonBean.AdListBean> //轮播图列表
    private lateinit var mGvListBeans: MutableList<TbCommonBean.CategoryListBean>
    private lateinit var mAdapter: KTHomeCommenAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入
    private var id = ""
    private var asc = arrayOf("")
    private var orderByType = arrayOf("0")
    private var mDialog: Dialog? = null

    companion object{
        fun getInstance(id: String) : KTHomeCommenFragment {
            var fragment = KTHomeCommenFragment()
            var bundle = Bundle()
            bundle.putString("id",id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once){
            refresh()
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_main, container, false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        arguments?.let {
            id = it.getString("id","")
        }
        mPresenter.setView(this)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context,2)
        mList = mutableListOf()
        mAdListBeans = mutableListOf()
        mGvListBeans = mutableListOf()
        mAdapter = KTHomeCommenAdapter(mList,mAdListBeans,context!!)
        mAdapter.setGv(mGvListBeans)
        mAdapter.setLayoutType(1)
        mAdapter.setAsc(asc)
        mAdapter.setOrderByType(orderByType)
        mAdapter.setOnClick(this)
        mBinding.swipeTarget.adapter = mAdapter

        var fragment = parentFragment
        if (fragment as? KTHomeFragnent != null)
            mPresenter.solveScoll(mBinding.swipeTarget,mBinding.swipeToLoad,fragment.getAppBar())
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getDate(page,asc[0],orderByType[0],id,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.getDate(page,asc[0],orderByType[0],id,this.bindToLifecycle())
    }

    override fun onSuccess(bean: TbCommonBean) {
        if (refersh) {
            dissRefresh()
            mList.clear()
            mAdListBeans.clear()
            mGvListBeans.clear()
            mGvListBeans.addAll(bean.categoryList)
            if (bean.adList.size > 1){
                for (i in 0..(bean.adList.size + 1)) {//轮播图数据
                    when (i) {
                        0 -> {//加入最后一个
                            mAdListBeans.add(bean.adList[bean.adList.size - 1])
                        }
                        bean.adList.size + 1 -> {//加入第一个
                            mAdListBeans.add(bean.adList[0])
                        }
                        else -> {//正常数据
                            mAdListBeans.add(bean.adList[i - 1])
                        }
                    }
                }
            }else{
                mAdListBeans.addAll(bean.adList)
            }
            mList.addAll(bean.data)
            mAdapter.notifyDataSetChanged()
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
        if (once)
            once = false
    }

    override fun onFile(error: String?) {
        if (refersh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onItemShare(position: Int) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        val path = "pages/list/main-v2-info/main?id=" + mList[position].otherGoodsId
        val shareImage = mList[position].img
        val shareName = mList[position].otherName
        val shareContent = "【分享来自极品城APP】看评测选好物，省心更省钱"
        val shareUrl = "https://www.jipincheng.cn"
        ShareUtils(context, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWXMin1(context as Activity, shareUrl, shareImage, shareName, shareContent, path)
    }

    override fun initTitle() {
        page = 1
        refersh = true
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.getDate(page,asc[0],orderByType[0],id,this.bindToLifecycle())
    }

    override fun changeList() {
        if (mAdapter.getLayoutType() == 1) {
            mAdapter.setLayoutType(2)//网格
            mAdapter.notifyItemRangeChanged(2, mList.size)
        } else {
            mAdapter.setLayoutType(1)//横向
            mAdapter.notifyItemRangeChanged(2, mList.size)
        }
    }

    fun refresh(){
        if (!mBinding.swipeToLoad.isRefreshEnabled) {
            mBinding.swipeToLoad.isRefreshEnabled = true
            mBinding.swipeToLoad.isRefreshing = true
            mBinding.swipeToLoad.isRefreshEnabled = false
        } else {
            mBinding.swipeToLoad.isRefreshing = true
        }
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
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UMShareAPI.get(context).release()
    }
}