package com.example.administrator.jipinshop.activity.minekt.publishkt.detail

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.adapter.ReportDetailAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.FindDetailBean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.TryDetailBean
import com.example.administrator.jipinshop.databinding.ActivityAuditDetailBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.RelatedGoodsDialog
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/22
 * @Describe 审核中  详情
 */
class AuditDetailActivity : BaseActivity(), View.OnClickListener, RelatedGoodsDialog.OnItem, AuditDetailView {

    @Inject
    lateinit var mPresenter: AuditDetailPresenter

    private lateinit var mBinding : ActivityAuditDetailBinding
    private lateinit var mRelatedGoods : ArrayList<FindDetailBean.DataBean.RelatedGoodsListBean>
    private lateinit var mDialog: Dialog//加载框
    private var mRelatedGoodsDialog: RelatedGoodsDialog? = null
    private lateinit var mList : MutableList<TryDetailBean.DataBean.GoodsContentListBean>
    private lateinit var mRVAdapter: ReportDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_audit_detail)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "清单详情"
        }
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog.show()

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mRVAdapter = ReportDetailAdapter(this, mList)
        mBinding.recyclerView.adapter = mRVAdapter

        mRelatedGoods = arrayListOf()

        mPresenter.getDetail(intent.getStringExtra("id"), "7", this.bindToLifecycle<FindDetailBean>())
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back -> {
                finish()
            }
            R.id.detail_buy -> {
                if (mRelatedGoods.size != 0 && mRelatedGoods.size > 1) {
                    //购买
                    if (mRelatedGoodsDialog == null) {
                        mRelatedGoodsDialog = RelatedGoodsDialog.getInstance(mRelatedGoods)
                        mRelatedGoodsDialog?.setOnItem(this)
                    }
                    mRelatedGoodsDialog?.let {
                        if (!it.isAdded) {
                            it.show(supportFragmentManager, "RelatedGoodsDialog")
                        }
                    }

                } else if ( mRelatedGoods.size != 0 && mRelatedGoods.size == 1) {
                    //直接购买
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        return
                    }
                    mDialog = ProgressDialogView().createLoadingDialog(this, "")
                    mDialog.show()
                    val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
                    if (TextUtils.isEmpty(specialId) || specialId == "null") {
                        startActivity(Intent(this, WebActivity::class.java)
                                .putExtra(WebActivity.url, RetrofitModule.UP_BASE_URL + "qualityshop-api/api/taobao/login?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                                .putExtra(WebActivity.title, "淘宝授权")
                        )
                    } else {
                        mPresenter.goodsBuyLink(mRelatedGoods[0].goodsId, this.bindToLifecycle<ImageBean>())
                    }
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog.show()
        val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") {
            startActivity(Intent(this, WebActivity::class.java)
                    .putExtra(WebActivity.url, RetrofitModule.UP_BASE_URL + "qualityshop-api/api/taobao/login?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                    .putExtra(WebActivity.title, "淘宝授权")
            )
        } else {
            mPresenter.goodsBuyLink(mRelatedGoods[position].goodsId, this.bindToLifecycle<ImageBean>())
        }
    }

    override fun onDestroy() {
        AlibcTradeSDK.destory()
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    override fun onSuccess(bean: FindDetailBean) {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
        if (bean.data.relatedGoodsList != null) {
            mRelatedGoods.addAll(bean.data.relatedGoodsList)
        }
        mBinding.detailTitle.text = bean.data.title
        mList.clear()
        mList.addAll(Gson().fromJson<List<TryDetailBean.DataBean.GoodsContentListBean>>(bean.data.content, object : TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>() {}.type))
        mRVAdapter.notifyDataSetChanged()
        GlideApp.loderCircleImage(this, bean.data.user.avatar, mBinding.detailUserImage, R.mipmap.rlogo, 0)
        mBinding.detailUserName.text = bean.data.user.nickname
        mBinding.detailTime.text = bean.data.createTime
    }

    override fun onFile(error: String?) {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
        ToastUtil.show(error)
        initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上")
    }

    override fun onBuyLinkSuccess(bean: ImageBean) {
        TaoBaoUtil.openAliHomeWeb(this, bean.data)
    }

    override fun onFileCommentInsert(error: String?) {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
        ToastUtil.show(error)
    }

    fun initError(id: Int, title: String, content: String) {
        mBinding.netClude?.run {
            qsNet.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.setText(title)
            errorContent.setText(content)
        }
    }

}