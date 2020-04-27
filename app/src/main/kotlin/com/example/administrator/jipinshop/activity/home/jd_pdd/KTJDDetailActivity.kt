package com.example.administrator.jipinshop.activity.home.jd_pdd

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.share.ShareActivity
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity
import com.example.administrator.jipinshop.adapter.KTJDDetailAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.databinding.ActivityJdDetailBinding
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/22
 * @Describe 京东、拼多多详情页面
 */
class KTJDDetailActivity : BaseActivity(), View.OnClickListener, OnLoadMoreListener, OnRefreshListener, KTJDDetailAdapter.OnItem, KTJDDetailView {

    @Inject
    lateinit var mPersenter: KTJDDetailPresenter

    private lateinit var mBinding: ActivityJdDetailBinding
    private lateinit var mList: MutableList<TBSreachResultBean.DataBean> //列表
    private lateinit var mAdListBeans: MutableList<TbCommonBean.AdListBean> //轮播图列表
    private lateinit var mTitles : MutableList<EvaluationTabBean.DataBean> //tab数据
    private lateinit var mAdapter: KTJDDetailAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var source = "-1" //1京东 2淘宝 4拼多多
    private var mDialog: Dialog? = null
    private var set = 0 //位置默认是0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_jd_detail)
        mBinding.listener = this
        initView()
    }

    private fun initView() {
        source = intent.getStringExtra("source")
        mBinding.inClude?.let {
            it.titleLine.visibility = View.GONE
            it.titleTv.text = intent.getStringExtra("name")
        }
        mBaseActivityComponent.inject(this)
        mPersenter.setView(this)

        mList = mutableListOf()
        mAdListBeans = mutableListOf()
        mTitles = mutableListOf()
        mAdapter = KTJDDetailAdapter(this,mList)
        mAdapter.setOnClick(this)
        mAdapter.setAd(mAdListBeans)
        mAdapter.setTitle(mTitles)
        mAdapter.setLocation(set)
        mBinding.swipeTarget.layoutManager = LinearLayoutManager(this)
        mBinding.swipeTarget.adapter = mAdapter

        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.post { mBinding.swipeToLoad.isRefreshing = true }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.detail_sreachLeft , R.id.detail_sreachRight ->{
                startActivity(Intent(this, TBSreachActivity::class.java)
                        .putExtra("type",source)
                )
            }
        }
    }

    //刷新
    override fun onRefresh() {
        page = 1
        refersh = true
        mPersenter.getData(source,this.bindToLifecycle())
    }

    //加载
    override fun onLoadMore() {
        page++
        refersh = false
        if (mTitles[set].type == 1){
            mPersenter.commendGoodsList(page,source,this.bindToLifecycle())
        }else{
            mPersenter.getOtherGoodsListByCategory(mTitles[set].categoryId,page,source,this.bindToLifecycle())
        }
    }

    override fun onSuccess(bean: JDBean) {
        mTitles.clear()
        mTitles.addAll(bean.data)
        mAdListBeans.clear()
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
        mAdapter.notifyDataSetChanged()//刷新title
        if (mTitles[set].type == 1){
            //获取今日推荐
            mPersenter.commendGoodsList(page,source,this.bindToLifecycle())
        }else {
            //常规列表商品
            mPersenter.getOtherGoodsListByCategory(mTitles[set].categoryId,page,source,this.bindToLifecycle())
        }
    }

    override fun onList(bean: TBSreachResultBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.setLocation(set)
                mAdapter.notifyDataSetChanged()
            }else {//没有数据时
                mList.clear()
                mAdapter.setLocation(set)
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
        if (refersh) {
            dissRefresh()
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }


    fun dissRefresh() {
        mBinding.swipeToLoad.isRefreshing = false
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    fun dissLoading() {
        mBinding.swipeToLoad.isLoadingMore = false
    }

    override fun onItemShare(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        if (TextUtils.isEmpty(mList[position].source) || mList[position].source == "2" ){
            TaoBaoUtil.openTB(this){
                startActivity(Intent(this, ShareActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                )
            }
        }else{
            startActivity(Intent(this, ShareActivity::class.java)
                    .putExtra("otherGoodsId", mList[position].otherGoodsId)
            )
        }
    }

    override fun selcetTitle(position: Int) {
        set = position
        page = 1
        refersh = true
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.show()
        if (mTitles[set].type == 1){
            //获取今日推荐
            mPersenter.commendGoodsList(page,source,this.bindToLifecycle())
        }else {
            //常规列表商品
            mPersenter.getOtherGoodsListByCategory(mTitles[set].categoryId,page,source,this.bindToLifecycle())
        }
    }
}