package com.example.administrator.jipinshop.fragment.home.main

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.share.ShareActivity
import com.example.administrator.jipinshop.adapter.HomePageAdapter
import com.example.administrator.jipinshop.adapter.KTMain2Adapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.bean.eventbus.HomeRefresh
import com.example.administrator.jipinshop.databinding.FragmentKtMainBinding
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.trello.rxlifecycle2.android.FragmentEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/20
 * @Describe 新首页——精选（含京东和拼多多）
 */
class KTMain2Fragment : DBBaseFragment(), KTMain2View, OnLoadMoreListener, OnRefreshListener, KTMain2Adapter.OnItem {

    @Inject
    lateinit var mPresenter: KTMain2Presenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var mBinding : FragmentKtMainBinding
    private var page = 1
    private var refersh: Boolean = true
    private lateinit var mList: MutableList<TBSreachResultBean.DataBean>//今日推荐列表
    private lateinit var mAdapter: KTMain2Adapter
    private lateinit var mPagerAdapter: HomePageAdapter
    private var mDialog: Dialog? = null
    private var source : String = "2" //1:京东 2:淘宝 4拼多多  默认淘宝

    companion object{
        fun getInstance() : KTMain2Fragment {
            return KTMain2Fragment()
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_main, container, false)
        EventBus.getDefault().register(this)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.transparent))
        mPresenter.setView(this)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context,2)
        mList = mutableListOf()
        mAdapter = KTMain2Adapter(mList,context!!)
        mPagerAdapter = HomePageAdapter(childFragmentManager)
        mAdapter.setPagerAdapter(mPagerAdapter)
        mAdapter.setOnClick(this)
        mAdapter.setAppStatisticalUtil(appStatisticalUtil)
        mAdapter.setTransformer(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        mBinding.swipeTarget.adapter = mAdapter

        var fragment = parentFragment
        if (fragment as? KTHomeFragnent != null)
            mPresenter.solveScoll(mBinding.swipeTarget,
                    mBinding.swipeToLoad,fragment.getAppBar(),fragment)
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
        appStatisticalUtil.addEvent("shouye_loding",this.bindToLifecycle())
        mPresenter.getDate("1",this.bindToLifecycle())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.commendGoodsList(page,source,this.bindToLifecycle())
    }

    override fun onSuccess(type: String, bean: TbkIndexBean) {
        mAdapter.setDate(bean)
        mAdapter.notifyDataSetChanged()
        var fragment = parentFragment
        if (fragment as? KTHomeFragnent != null){
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                if(SPUtils.getInstance().getBoolean(CommonDate.memberNotice, true)){
                    fragment.initMemberNotice(true)
                    fragment.initMarquee("")
                    fragment.initAuth(false)
                }else{
                    fragment.initMemberNotice(false)
                    if (bean.data.message != null && !TextUtils.isEmpty(bean.data.message.content)){
                        fragment.initMarquee(bean.data.message.content)
                    }else{
                        fragment.initMarquee("")
                        var specialId2 = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
                        if (TextUtils.isEmpty(specialId2) || specialId2 == "null"){
                            fragment.initAuth(true)
                        }else{
                            fragment.initAuth(false)
                        }
                    }
                }
            }else{//未登录
                fragment.initMemberNotice(false)
                fragment.initMarquee("")
                fragment.initAuth(false)
            }
        }
        if (type == "1"){
            mPresenter.commendGoodsList(page,source,this.bindToLifecycle())
        }
    }

    override fun onDay(bean: TBSreachResultBean) {
        if (refersh){
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            }else {
                mList.clear()
                mAdapter.notifyDataSetChanged()
            }
        }else{
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
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

    //今日推荐列表的分享
    override fun onItemShare(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        if (TextUtils.isEmpty(mList[position].source) || mList[position].source == "2"){
            TaoBaoUtil.openTB(context){
                startActivity(Intent(context, ShareActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                        .putExtra("source",mList[position].source)
                )
            }
        }else{
            startActivity(Intent(context, ShareActivity::class.java)
                    .putExtra("otherGoodsId", mList[position].otherGoodsId)
                    .putExtra("source",mList[position].source)
            )
        }
    }

    //今日推荐的选择
    override fun onSelect(source : String) {
        this.source = source
        page = 1
        refersh = true
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.commendGoodsList(page,this.source,this.bindToLifecycle())
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
        EventBus.getDefault().unregister(this)
    }

    //用来实时更新首页数据
    @Subscribe
    fun refresh(bus: HomeRefresh?){
        bus?.let {
            if (it.flag == HomeRefresh.tag){
                mPresenter.getDate("2",this.bindToLifecycle())
            }
        }
    }
}