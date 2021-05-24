package com.example.administrator.jipinshop.activity.cheapgoods

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.cheapgoods.record.AllowanceRecordActivity
import com.example.administrator.jipinshop.activity.home.newGift.NewGiftActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.newpeople.cheap.CheapBuyDetailActivity
import com.example.administrator.jipinshop.adapter.KTCheapBuyAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.example.administrator.jipinshop.databinding.ActivityCheapBuyBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2
import com.trello.rxlifecycle2.android.FragmentEvent
import com.umeng.socialize.bean.SHARE_MEDIA
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2021/2/22
 * @Describe 综合页-百万补贴
 */
class zCheapBuyFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, KTCheapBuyAdapter.OnClickItem, CheapBuyView, ShareBoardDialog2.onShareListener, OnLoadMoreListener {

    @Inject
    lateinit var mPresenter: CheapBuyPresenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil

    private lateinit var mBinding : ActivityCheapBuyBinding
    private lateinit var mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>
    private lateinit var adapter: KTCheapBuyAdapter
    private var mDialog: Dialog? = null
    private var mShareBoardDialog: ShareBoardDialog2? = null
    private var startPop: Boolean = true//是否弹出关闭确认弹窗
    private var allowance: String? = null
    private var page = 1
    private var refersh: Boolean = true
    //区分从哪个页进来的  1:综合页  2：新人五重礼 3:单开页
    private var type = "1" //默认综合页
    private val once = arrayOf(true)//记录第一次进入页面标示

    companion object{
        @JvmStatic //java中的静态方法
        fun getInstance(startPop : Boolean , type : String) : zCheapBuyFragment {
            var fragment = zCheapBuyFragment()
            var bundle = Bundle()
            bundle.putBoolean("startPop" , startPop)
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_cheap_buy,container,false)
        mBinding.listener = this
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root
    }

    override fun initView() {
        arguments?.let {
            startPop = it.getBoolean("startPop", true)
            type = it.getString("type")
        }
        mBinding.inClude?.let {
            it.titleTv.text = "官方百万补贴"
        }

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context, 3)
        mList = mutableListOf()
        adapter = KTCheapBuyAdapter(context!!,mList)
        adapter.setOnClickItem(this)
        mBinding.swipeTarget.adapter = this.adapter

        if (type == "2"){
            //新人五重礼
            mBinding.statusBar.visibility = View.GONE
            mBinding.inClude?.container?.visibility = View.GONE
            mPresenter.solveScoll(mBinding.swipeTarget, mBinding.swipeToLoad,
                    (activity as NewGiftActivity).bar, once)
        }else{
            mPresenter.setStatusBarHight(mBinding.statusBar, context!!)
        }

        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back  ->{
                if (startPop) {
                    if (mList.size >= 3 && !TextUtils.isEmpty(allowance)) {
                        var money = BigDecimal(allowance).toDouble()
                        if (money != 0.0){
                            DialogUtil.cheapOutDialog(context, mList, allowance) {
                                activity?.finish()
                            }
                            startPop = false
                        }else{
                            activity?.finish()
                        }
                    }else{
                        activity?.finish()
                    }
                } else {
                    activity?.finish()
                }
            }
            R.id.detail_share -> {
                //使用记录
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                startActivity(Intent(context, AllowanceRecordActivity::class.java))
            }
            R.id.detail_text -> {
                //分享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(0)
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(childFragmentManager,"ShareBoardDialog2")
                    }
                }
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        mPresenter.initShare(share_media,this.bindToLifecycle())
    }

    override fun onLink() {
        mPresenter.initShare(null,this.bindToLifecycle())
    }

    override fun initShare(share_media: SHARE_MEDIA?, bean: ShareInfoBean) {
        mPresenter.taskFinish(this.bindToLifecycle())
        if (share_media != null){
            ShareUtils(context,share_media,mDialog)
                    .shareWeb(activity, bean.data.link, bean.data.title,bean.data.desc,bean.data.imgUrl,R.mipmap.share_logo)
        }else{
            val clip = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("jipinshop", bean.data.link)
            clip.primaryClip = clipData
            ToastUtil.show("复制成功")
            SPUtils.getInstance().put(CommonDate.CLIP, bean.data.link)
        }
    }


    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.setDate(page,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.setDate(page,this.bindToLifecycle())
    }

    override fun onSuccess(bean: NewPeopleBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.allowanceGoodsList.size != 0) {
                allowance = bean.data.allowance
                mList.clear()
                mList.addAll(bean.data.allowanceGoodsList)
                adapter.setBean(bean)
                adapter.notifyDataSetChanged()
            }else {
                ToastUtil.show("暂无数据")
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.allowanceGoodsList.size != 0) {
                mList.addAll(bean.data.allowanceGoodsList)
                allowance = bean.data.allowance
                adapter.setBean(bean)
                adapter.notifyDataSetChanged()
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
        once[0] = false
    }

    override fun onFile(error: String?) {
        if (refersh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
        once[0] = false
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

    override fun onBuy(position: Int) {
        appStatisticalUtil.addEvent("shouye_activity_allowance_" + mList[position].otherGoodsId , this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        startActivity(Intent(context, CheapBuyDetailActivity::class.java)
                .putExtra("freeId", mList[position].id)
                .putExtra("otherGoodsId", mList[position].otherGoodsId)
        )
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
        AlibcTradeSDK.destory()
    }

}