package com.example.administrator.jipinshop.activity.minekt.recovery

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.OrderRecoveryAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.TbOrderBean
import com.example.administrator.jipinshop.databinding.ActivityOrderRecoveryResultBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe 确认找回结果页面
 */
class OrderRecoveryResultActivity : BaseActivity(), View.OnClickListener, OrderRecoveryResultView {

    @Inject
    lateinit var mPresenter: OrderRecoveryResultPesenter
    private var keyword = ""
    private lateinit var mBinding: ActivityOrderRecoveryResultBinding
    private var mDialog: Dialog? = null
    private lateinit var mList: MutableList<TbOrderBean.DataBean>
    private lateinit var mAdapter: OrderRecoveryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_recovery_result)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        keyword = intent.getStringExtra("content")
        mBinding.inClude?.titleTv?.text = "订单找回"

        mBinding.orderRv.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        mAdapter = OrderRecoveryAdapter(mList,this)
        mBinding.orderRv.adapter = mAdapter

        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mPresenter.searchTbOrder(keyword,this.bindToLifecycle())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back, R.id.order_finish -> {
                finish()
            }
            R.id.order_bottom -> {
                //确认找回
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.let {
                    if (!it.isShowing)
                        it.show()
                }
                mPresenter.findBackTbOrder(keyword,this.bindToLifecycle())
            }
        }
    }

    override fun onSuccess(bean : TbOrderBean) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        if (bean.data != null && bean.data.size > 0){
            mBinding.orderNoDate.visibility = View.GONE
            mBinding.orderDate.visibility = View.VISIBLE
            mList.clear()
            mList.addAll(bean.data)
            mAdapter.notifyDataSetChanged()
        }else{
            mBinding.orderNoDate.visibility = View.VISIBLE
            mBinding.orderDate.visibility = View.GONE
        }
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onFind() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show("订单找回成功，请前往\n【我的订单】查看")
        finish()
    }
}