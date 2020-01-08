package com.example.administrator.jipinshop.fragment.home.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.HomePageAdapter
import com.example.administrator.jipinshop.adapter.KTMainAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
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
 * @create 2019/12/11
 * @Describe 首页——精选页面
 */
class KTMainFragment : DBBaseFragment(), OnLoadMoreListener, OnRefreshListener, KTMainAdapter.OnItem, KTMainView {

    @Inject
    lateinit var mPresenter: KTMainPresenter

    private lateinit var mBinding : FragmentKtMainBinding
    private lateinit var mAdapter: KTMainAdapter
    private lateinit var mPagerAdapter: HomePageAdapter
    private var asc = arrayOf("")
    private var orderByType = arrayOf("0")
    private var mDialog: Dialog? = null

    private lateinit var mList: MutableList<TBSreachResultBean.DataBean>
    private lateinit var mColor: MutableList<String>//颜色
    private lateinit var mAdListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean>//轮播图
    private lateinit var mGridList : MutableList<TbkIndexBean.DataBean.BoxListBean> //4宫格图（品质大牌、白菜好物、新品专区、每日签到）
    private lateinit var mUserList: MutableList<TbkIndexBean.DataBean.MessageListBean> //轮播的用户
    private lateinit var mFreeGoodsList : MutableList<TbkIndexBean.DataBean.AllowanceGoodsListBean>//新人免单商品
    private lateinit var mActivityList: MutableList<TbkIndexBean.DataBean.ActivityListBean>//高反专区、大额优惠卷
    private lateinit var mHotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>//热销榜单
    private var page = 1
    private var refersh: Boolean = true

    companion object{
        fun getInstance() : KTMainFragment {
            return KTMainFragment()
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_main, container, false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.transparent))
        mPresenter.setView(this)

        mList = mutableListOf()
        mColor = mutableListOf()
        mAdListBeans = mutableListOf()
        mGridList = mutableListOf()
        mUserList = mutableListOf()
        mActivityList = mutableListOf()
        mHotShopList = mutableListOf()
        mFreeGoodsList = mutableListOf()

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context,2)
        mPagerAdapter = HomePageAdapter(childFragmentManager)
        mAdapter = KTMainAdapter(mList,mColor,mAdListBeans,context!!)
        mAdapter.setPagerAdapter(mPagerAdapter)
        mAdapter.setGridList(mGridList)
        mAdapter.setUserList(mUserList)
        mAdapter.setAsc(asc)
        mAdapter.setOrderByType(orderByType)
        mAdapter.setFreeGoodsList(mFreeGoodsList)
        mAdapter.setActivityList(mActivityList)
        mAdapter.setHotShopList(mHotShopList)
        mAdapter.setLayoutType(1)//默认横向布局
        mAdapter.setOnClick(this)
        mBinding.swipeTarget.adapter = mAdapter

        var fragment = parentFragment
        if (fragment as? KTHomeFragnent != null)
            mPresenter.solveScoll(mBinding.mainBackground,mBinding.swipeTarget,mBinding.swipeToLoad,fragment.getAppBar())
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshEnabled = true
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.getDate("1",this.bindToLifecycle())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.commendGoodsList(page,asc[0],orderByType[0],this.bindToLifecycle())
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

    //设置父类中的背景颜色
    override fun onColor(pos: Int) {
        initColor(mColor[pos])
        mBinding.mainBackground.setColorFilter(Color.parseColor("#" +mColor[pos]))
    }

    private fun initColor(color : String){
        var fragment = parentFragment
        if (fragment as? KTHomeFragnent != null) {
            fragment.initColor("#$color")
        }
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

    //切换综合、价格、销量、补贴
    override fun initTitle() {
        page = 1
        refersh = true
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.commendGoodsList(page,asc[0],orderByType[0],this.bindToLifecycle())
    }

    override fun changeList() {
        if (mAdapter.getLayoutType() == 1) {
            mAdapter.setLayoutType(2)//网格
            mAdapter.notifyItemRangeChanged(5, mList.size)
        } else {
            mAdapter.setLayoutType(1)//横向
            mAdapter.notifyItemRangeChanged(5, mList.size)
        }
    }

    override fun onSuccess(type: String,bean: TbkIndexBean) {
        initClear()
        for (i in bean.data.ad1List.indices){
            mColor.add(bean.data.ad1List[i].color)
        }
        for (i in 0 .. (bean.data.ad1List.size + 1)){//轮播图数据
            when (i) {
                0 -> {//加入最后一个
                    mAdListBeans.add(bean.data.ad1List[bean.data.ad1List.size - 1])
                }
                bean.data.ad1List.size + 1 -> {//加入第一个
                    mAdListBeans.add(bean.data.ad1List[0])
                }
                else -> {//正常数据
                    mAdListBeans.add(bean.data.ad1List[i - 1])
                }
            }
        }
        mGridList.addAll(bean.data.boxList)
        mUserList.addAll(bean.data.messageList)
        mFreeGoodsList.addAll(bean.data.allowanceGoodsList)
        mAdapter.setImageDay(bean.data.hotActivity)
        mActivityList.addAll(bean.data.activityList)
        mHotShopList.addAll(bean.data.hotGoodsList)
        mAdapter.setAd2Bean(bean.data.ad2)
        mAdapter.setNewUser(bean.data.newUser)
        mAdapter.notifyDataSetChanged()
        if (type == "1")
            mPresenter.commendGoodsList(page,asc[0],orderByType[0],this.bindToLifecycle())
    }

    override fun onDay(bean: TBSreachResultBean) {
        if (refersh){
            dissRefresh()
            mList.clear()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyItemRangeChanged(5,mList.size)
            }
        }else{
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyItemRangeChanged(mAdapter.itemCount,mList.size)
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
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

    private fun initClear(){
        mColor.clear()
        mGridList.clear()
        mAdListBeans.clear()
        mUserList.clear()
        mFreeGoodsList.clear()
        mHotShopList.clear()
        mActivityList.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getDate("2",this.bindToLifecycle())//用来实时更新首页数据
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