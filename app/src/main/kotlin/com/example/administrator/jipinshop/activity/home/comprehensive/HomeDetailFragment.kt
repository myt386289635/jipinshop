package com.example.administrator.jipinshop.activity.home.comprehensive

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.HomeDetailPresenter
import com.example.administrator.jipinshop.activity.home.HomeDetailView
import com.example.administrator.jipinshop.activity.home.newGift.NewGiftActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.share.ShareActivity
import com.example.administrator.jipinshop.adapter.TBSreachResultAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.databinding.ActivityHomeDetailBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog
import com.trello.rxlifecycle2.android.FragmentEvent
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import java.util.*
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2021/2/22
 * @Describe 综合页9.9和一元购
 */
class HomeDetailFragment :  DBBaseFragment(), View.OnClickListener, TBSreachResultAdapter.OnItem, OnLoadMoreListener, OnRefreshListener, HomeDetailView, ShareBoardDialog.onShareListener {

    @Inject
    lateinit var mPresenter: HomeDetailPresenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil

    private lateinit var mBinding: ActivityHomeDetailBinding
    private var asc = ""
    private var orderByType = "0"
    private lateinit var mTextViews: MutableList<TextView>
    private lateinit var mList: MutableList<TBSreachResultBean.DataBean>
    private lateinit var mAdapter: TBSreachResultAdapter
    private var page = 1
    private var refresh: Boolean = true
    private var mDialog: Dialog? = null
    private var id = ""
    private var title = ""
    //分享面板
    private var mShareBoardDialog: ShareBoardDialog? = null
    private var shareUrl: String = ""
    //区分从哪个页进来的  1:综合页  2：新人五重礼
    private var type = "1" //默认综合页
    private val once = arrayOf(true)//记录第一次进入页面标示

    companion object{
        @JvmStatic //java中的静态方法
        fun getInstance(id : String , title : String , type: String) : HomeDetailFragment {
            var fragment = HomeDetailFragment()
            var bundle = Bundle()
            bundle.putString("id" , id)
            bundle.putString("title" , title)
            bundle.putString("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_home_detail, container, false)
        mBinding.listener = this
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root
    }

    override fun initView() {
        arguments?.let {
            id = it.getString("id")
            title = it.getString("title")
            type = it.getString("type")
        }
        mBinding.inClude?.let {
            it.titleTv.text = title
        }
        mBinding.detailTimeCotianer.visibility = View.GONE

        mTextViews = ArrayList()
        mTextViews.add(mBinding.titleZh)
        mTextViews.add(mBinding.titleJg)
        mTextViews.add(mBinding.titleBt)
        mTextViews.add(mBinding.titleXl)
        initTitle(0)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(context, 2)
        mList = mutableListOf()
        mAdapter = TBSreachResultAdapter(mList, context)
        mAdapter.setAppStatisticalUtil(appStatisticalUtil)
        mAdapter.setTransformer(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        mAdapter.setId(id)
        mAdapter.layoutType = 1//默认横向布局
        mAdapter.setOnItem(this)
        mBinding.swipeTarget.adapter = mAdapter

        if (type == "2"){
            //新人五重礼
            mBinding.statusBar.visibility = View.GONE
            mBinding.titleContainer.visibility = View.GONE
            mPresenter.solveScoll(mBinding.swipeTarget, mBinding.swipeToLoad,
                    (activity as NewGiftActivity).bar, once)
        }else{
            mPresenter.setStatusBarHight(mBinding.statusBar, context!!)
        }

        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onRefresh() {
        refresh = true
        page = 1
        appStatisticalUtil.addEvent("tongyong_loding",this.bindToLifecycle())//专题页刷新统计
        mBinding.swipeTarget.scrollToPosition(0)
        mPresenter.getDate(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        refresh = false
        page++
        mPresenter.getDate(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onSuccess(bean: TBSreachResultBean) {
        shareUrl = bean.shareUrl
        if (refresh) {
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
        once[0] = false
    }

    override fun onFile(error: String?) {
        if (refresh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
        once[0] = false
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                activity?.finish()
            }
            R.id.title_zh -> {
                orderByType = "0"
                asc = ""
                initTitle(0)
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_jg -> {
                orderByType = "1"
                initTitle(1)
                asc = if (TextUtils.isEmpty(asc) || asc == "0") {
                    "1"
                } else {
                    "0"
                }
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_bt -> {
                orderByType = "2"
                asc = ""
                initTitle(2)
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_xl -> {
                orderByType = "3"
                asc = ""
                initTitle(3)
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.sreach_change -> {
                appStatisticalUtil.zhuanti_tab(4,id,this.bindToLifecycle())
                if (mAdapter.layoutType == 1) {
                    mAdapter.layoutType = 2//网格
                    mAdapter.notifyItemRangeChanged(0, mList.size)
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change1)
                } else {
                    mAdapter.layoutType = 1//横向
                    mAdapter.notifyItemRangeChanged(0, mList.size)
                    mBinding.sreachChangeImg.setImageResource(R.mipmap.sreach_change)
                }
            }
            R.id.detail_share ->{
                //分享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivityForResult(Intent(context, LoginActivity::class.java),301)
                    return
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog.getInstance("","")
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(childFragmentManager,"ShareBoardDialog")
                    }
                }
            }
        }
    }

    override fun onItemShare(position: Int) {
        appStatisticalUtil.addEvent("zhuanti." + id + "_liebiao.zf",this.bindToLifecycle())
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

    //初始化标题
    private fun initTitle(position: Int) {
        appStatisticalUtil.zhuanti_tab(position,id,this.bindToLifecycle())
        val drawable = resources.getDrawable(R.mipmap.sreach_price3)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        mBinding.titleJg.setCompoundDrawables(null, null, drawable, null)
        for (i in mTextViews.indices) {
            mTextViews[i].setTextColor(resources.getColor(R.color.color_9D9D9D))
            mTextViews[i].paint.isFakeBoldText = false
        }
        for (i in mTextViews.indices) {
            if (i == position) {
                if (position == 1) {
                    val upDrawable: Drawable
                    if (TextUtils.isEmpty(asc) || asc == "0") {
                        upDrawable = resources.getDrawable(R.mipmap.sreach_price)
                    } else {
                        upDrawable = resources.getDrawable(R.mipmap.sreach_price1)
                    }
                    upDrawable.setBounds(0, 0, upDrawable.minimumWidth, upDrawable.minimumHeight)
                    mBinding.titleJg.setCompoundDrawables(null, null, upDrawable, null)
                }
                mTextViews[i].setTextColor(resources.getColor(R.color.color_202020))
                mTextViews[i].paint.isFakeBoldText = true
            }
        }
    }

    fun dissRefresh() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
        mDialog?.let {
            if(it.isShowing){
                it.dismiss()
            }
        }
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled) {
                mBinding.swipeToLoad.isLoadMoreEnabled = true
                mBinding.swipeToLoad.isLoadingMore = false
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                mBinding.swipeToLoad.isLoadingMore = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        ShareUtils(context,share_media,mDialog)
                .shareWeb(activity, shareUrl, "我在极品城看$title","款款商品有优惠，总有一款适合你，速来围观","", R.mipmap.share_logo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data)
        if (requestCode == 301 && resultCode == 200){
            //登陆成功后回来，需要刷新分享链接
            onRefresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UMShareAPI.get(context).release()
    }
}