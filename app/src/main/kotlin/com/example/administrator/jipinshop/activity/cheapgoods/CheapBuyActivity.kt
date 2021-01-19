package com.example.administrator.jipinshop.activity.cheapgoods

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.cheapgoods.record.AllowanceRecordActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.newpeople.cheap.CheapBuyDetailActivity
import com.example.administrator.jipinshop.adapter.KTCheapBuyAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.example.administrator.jipinshop.databinding.ActivityCheapBuyBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2
import com.umeng.socialize.bean.SHARE_MEDIA
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe 特惠购
 */
class CheapBuyActivity : BaseActivity(), View.OnClickListener, OnRefreshListener, KTCheapBuyAdapter.OnClickItem, CheapBuyView, ShareBoardDialog2.onShareListener {

    @Inject
    lateinit var mPresenter: CheapBuyPresenter
    private lateinit var mBinding : ActivityCheapBuyBinding
    private lateinit var mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>
    private lateinit var adapter: KTCheapBuyAdapter
    private var mDialog: Dialog? = null
    private var mShareBoardDialog: ShareBoardDialog2? = null
    private var startPop: Boolean = true//是否弹出关闭确认弹窗
    private var allowance: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cheap_buy)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        startPop = intent.getBooleanExtra("startPop", true)
        mBinding.inClude?.let {
            it.titleTv.text = "官方百万补贴专区"
        }
        var animation = AnimationUtils.loadAnimation(this, R.anim.free_scale)
        mBinding.detailText.startAnimation(animation)
        animation.start()//动画

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
                if (startPop) {
                    if (mList.size >= 3 && !TextUtils.isEmpty(allowance)) {
                        var money = BigDecimal(allowance).toDouble()
                        if (money != 0.0){
                            DialogUtil.cheapOutDialog(this, mList, allowance) {
                                finish()
                            }
                            startPop = false
                        }else{
                            finish()
                        }
                    }else{
                        finish()
                    }
                } else {
                    finish()
                }
            }
            R.id.detail_share -> {
                //使用记录
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    return
                }
                startActivity(Intent(this, AllowanceRecordActivity::class.java))
            }
            R.id.detail_used -> {
                //分享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    return
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(0)
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(supportFragmentManager,"ShareBoardDialog2")
                    }
                }
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.show()
        mPresenter.initShare(share_media,this.bindToLifecycle())
    }

    override fun onLink() {
        mPresenter.initShare(null,this.bindToLifecycle())
    }

    override fun initShare(share_media: SHARE_MEDIA?, bean: ShareInfoBean) {
        mPresenter.taskFinish(this.bindToLifecycle())
       if (share_media != null){
           ShareUtils(this,share_media,mDialog)
                   .shareWeb(this, bean.data.link, bean.data.title,bean.data.desc,bean.data.imgUrl,R.mipmap.share_logo)
       }else{
           val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           val clipData = ClipData.newPlainText("jipinshop", bean.data.link)
           clip.primaryClip = clipData
           ToastUtil.show("复制成功")
           SPUtils.getInstance().put(CommonDate.CLIP, bean.data.link)
       }
    }


    override fun onRefresh() {
        mPresenter.setDate(this.bindToLifecycle())
    }

    override fun onSuccess(bean: NewPeopleBean) {
        dissRefresh()
        allowance = bean.data.allowance
        mList.clear()
        mList.addAll(bean.data.allowanceGoodsList)
        adapter.setBean(bean)
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
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun onBuy(position: Int) {
        startActivity(Intent(this, CheapBuyDetailActivity::class.java)
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

    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }

    override fun onBackPressed() {
        if (startPop) {
            if (mList.size >= 3 && !TextUtils.isEmpty(allowance)) {
                var money = BigDecimal(allowance).toDouble()
                if (money != 0.0){
                    DialogUtil.cheapOutDialog(this, mList, allowance) { finish() }
                    startPop = false
                }else{
                    super.onBackPressed()
                }
            }else{
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}