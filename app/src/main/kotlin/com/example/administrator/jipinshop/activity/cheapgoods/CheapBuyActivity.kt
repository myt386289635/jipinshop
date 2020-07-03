package com.example.administrator.jipinshop.activity.cheapgoods

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.cheapgoods.record.AllowanceRecordActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.adapter.KTCheapBuyAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.databinding.ActivityCheapBuyBinding
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe 特惠购
 */
class CheapBuyActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, KTCheapBuyAdapter.OnClickItem, CheapBuyView {

    @Inject
    lateinit var mPresenter: CheapBuyPresenter
    private lateinit var mBinding : ActivityCheapBuyBinding
    private lateinit var mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>
    private lateinit var adapter: KTCheapBuyAdapter
    private var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cheap_buy)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "百万补贴特惠购"
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        adapter = KTCheapBuyAdapter(this,mList)
        adapter.setOnClickItem(this)
        mBinding.recyclerView.adapter = this.adapter
        mBinding.recyclerView.isNestedScrollingEnabled = false

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.isLoadMoreEnabled = false
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back  ->{
                finish()
            }
            R.id.detail_share -> {
                //使用记录
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    return
                }
                startActivity(Intent(this, AllowanceRecordActivity::class.java))
            }
        }
    }

    override fun onRefresh() {
        mPresenter.setDate(this.bindToLifecycle())
    }

    override fun onSuccess(bean: NewPeopleBean) {
        dissRefresh()
        //特惠购新手指导
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRSTCHEAP, true)) {
            SPUtils.getInstance().put(CommonDate.FIRSTCHEAP, false)
            DialogUtil.cheapGuideDialog(this)
        }
        mList.clear()
        mList.addAll(bean.data.allowanceGoodsList)
        adapter.setTotalUsedAllowance(bean.data.totalUsedAllowance)
        adapter.setAllowance(bean.data.allowance)
        adapter.notifyDataSetChanged()
    }

    override fun onFile(error: String?) {
        dissRefresh()
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
    }

    override fun onBuy(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        DialogUtil.LoginDialog(this, "您将前往淘宝购买并消耗"+ mList[position].useAllowancePrice +"元津贴\n下单立减" + mList[position].useAllowancePrice + "元，无需等待返现",
                "确认", "取消", R.color.color_202020, R.color.color_202020,
                false) {
            TaoBaoUtil.openTB(this){
                mDialog = ProgressDialogView().createOtherDialog(this,"淘宝",R.mipmap.dialog_tb)
                mDialog?.let {
                    if (!it.isShowing){
                        it.show()
                    }
                }
                mPresenter.apply(mList[position].id, this.bindToLifecycle<ImageBean>())
            }
        }
    }

    override fun onBuySuccess(bean: ImageBean) {
        TaoBaoUtil.openAliHomeWeb(this, bean.data, bean.otherGoodsId)
    }

    override fun onBuyFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
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
        AlibcTradeSDK.destory()
    }
}