package com.example.administrator.jipinshop.fragment.home.userlike

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTUserLikeAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.databinding.FragmentKtMainBinding
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.share.MobLinkUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog
import com.trello.rxlifecycle2.android.FragmentEvent
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe
 */
class KTUserLikeFragment : DBBaseFragment(), OnLoadMoreListener, OnRefreshListener, KTUserLikeView, KTUserLikeAdapter.OnItem, ShareBoardDialog.onShareListener {

    @Inject
    lateinit var mPresenter: KTUserLikePresenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil

    private lateinit var mBinding : FragmentKtMainBinding
    private lateinit var mList: MutableList<SimilerGoodsBean.DataBean>
    private lateinit var mAdapter: KTUserLikeAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入
    private var mDialog: Dialog? = null
    private var mShareBoardDialog: ShareBoardDialog? = null
    private var sharePosition = -1//分享的位置  默认-1是商品本身
    private var path = ""
    private var shareImage = ""
    private var shareName = ""
    private var shareContent = ""
    private var shareUrl = ""

    companion object{
        fun getInstance() : KTUserLikeFragment {
            return KTUserLikeFragment()
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
        mPresenter.setView(this)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context,2)
        mList = mutableListOf()
        mAdapter = KTUserLikeAdapter(mList,context!!)
        mAdapter.setOnItem(this)
        mAdapter.setAppStatisticalUtil(appStatisticalUtil)
        mAdapter.setTransformer(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
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
        appStatisticalUtil.addEvent("cnxh_loding",this.bindToLifecycle())//猜你喜欢刷新统计
        mPresenter.listSimilerGoods(context!!,page,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.listSimilerGoods(context!!,page,this.bindToLifecycle())
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

    override fun onFile(error: String?) {
        if (refersh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onSuccess(bean: SimilerGoodsBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            }
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

    override fun onItemShare(position: Int) {
        appStatisticalUtil.addEvent("shouye.cnxh_liebiao.zf",this.bindToLifecycle())//猜你喜欢列表分享统计
        sharePosition = position
        if (mShareBoardDialog == null) {
            mShareBoardDialog = ShareBoardDialog.getInstance("", "")
            mShareBoardDialog?.setOnShareListener(this)
        }
        mShareBoardDialog?.let {
            if (!it.isAdded){
                it.show(childFragmentManager,"ShareBoardDialog")
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        path = "pages/list/main-v2-info/main?id=" + mList[sharePosition].otherGoodsId
        shareImage = mList[sharePosition].img
        shareName = mList[sharePosition].otherName
        shareContent = "【分享来自极品城APP】看评测选好物，省心更省钱"
        shareUrl = RetrofitModule.H5_URL + "share/tbGoodsDetail.html?id=" + mList[sharePosition].otherGoodsId
        if (share_media == SHARE_MEDIA.WEIXIN){
            mDialog?.let {
                if (!it.isShowing)
                    it.show()
            }
            mPresenter.getTbkGoodsPoster(mList[sharePosition].otherGoodsId,this.bindToLifecycle())
        } else {
            MobLinkUtil.mobShare(mList[sharePosition].otherGoodsId, "/tbkGoodsDetail") { mobID ->
                if (!TextUtils.isEmpty(mobID)) {
                    shareUrl += "&mobid=$mobID"
                }
                ShareUtils(context, share_media, mDialog)
                        .shareWeb(context as Activity?, shareUrl, shareName, shareContent, shareImage, R.mipmap.share_logo)
            }
        }
    }

    override fun onShareSuc(bean: ImageBean) {
        ShareUtils(context, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWXMin1(context as Activity, shareUrl, bean.data, shareName, shareContent, path)
    }

    override fun onShareFile() {
        ShareUtils(context, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWXMin1(context as Activity, shareUrl, shareImage, shareName, shareContent, path)
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