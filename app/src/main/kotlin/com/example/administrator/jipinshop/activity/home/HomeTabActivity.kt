package com.example.administrator.jipinshop.activity.home

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.TBSreachResultAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.databinding.ActivityHomeDetailBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import java.util.ArrayList
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/17
 * @Describe
 */
class HomeTabActivity : BaseActivity(), View.OnClickListener, HomeDetailView, TBSreachResultAdapter.OnItem, OnLoadMoreListener, OnRefreshListener {

    @Inject
    lateinit var mPresenter: HomeDetailPresenter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_detail)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        id = intent.getStringExtra("id")
        title = intent.getStringExtra("title")
        mBinding.inClude?.let {
            it.titleTv.text = title
        }

        mTextViews = ArrayList()
        mTextViews.add(mBinding.titleZh)
        mTextViews.add(mBinding.titleJg)
        mTextViews.add(mBinding.titleBt)
        mTextViews.add(mBinding.titleXl)
        initTitle(0)

        mBinding.swipeTarget.layoutManager = GridLayoutManager(this, 2)
        mList = mutableListOf()
        mAdapter = TBSreachResultAdapter(mList, this)
        mAdapter.layoutType = 1//默认横向布局
        mAdapter.setOnItem(this)
        mBinding.swipeTarget.adapter = mAdapter

        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onSuccess(bean: TBSreachResultBean) {
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
    }

    override fun onFile(error: String?) {
        if (refresh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.title_zh -> {
                orderByType = "0"
                asc = ""
                initTitle(0)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
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
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_bt -> {
                orderByType = "2"
                asc = ""
                initTitle(2)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.title_xl -> {
                orderByType = "3"
                asc = ""
                initTitle(3)
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.show()
                onRefresh()
            }
            R.id.sreach_change -> {
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
        }
    }

    override fun onRefresh() {
        refresh = true
        page = 1
        mBinding.swipeTarget.scrollToPosition(0)
        mPresenter.getGoodsListByCategory2(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onLoadMore() {
        refresh = false
        page++
        mPresenter.getGoodsListByCategory2(page,asc,orderByType,id,this.bindToLifecycle())
    }

    override fun onItemShare(position: Int) {
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        val path = "pages/list/main-v2-info/main?id=" + mList[position].otherGoodsId
        val shareImage = mList[position].img
        val shareName = mList[position].otherName
        val shareContent = "【分享来自极品城APP】看评测选好物，省心更省钱"
        val shareUrl = "https://www.jipincheng.cn"
        ShareUtils(this, SHARE_MEDIA.WEIXIN, mDialog)
                .shareWXMin1(this, shareUrl, shareImage, shareName, shareContent, path)
    }

    //初始化标题
    private fun initTitle(position: Int) {
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
            mBinding.swipeToLoad.isRefreshing = false
        }
        mDialog?.let {
            if(it.isShowing){
                it.dismiss()
            }
        }
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore) {
            mBinding.swipeToLoad.isLoadingMore = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }
}