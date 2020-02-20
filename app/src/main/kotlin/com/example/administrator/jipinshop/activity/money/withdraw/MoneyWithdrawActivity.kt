package com.example.administrator.jipinshop.activity.money.withdraw

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.money.record.MoneyRecordActivity
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityMoneyWithdrawBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 12:06
 * Description： 红包提现页面
 */
class MoneyWithdrawActivity : BaseActivity(), View.OnClickListener {

    @Inject
    lateinit var mPresenter : MoneyWithdrawPresenter

    private lateinit var mBinding: ActivityMoneyWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_withdraw)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "提现"
        }

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.title_back -> {
                finish()
            }
            R.id.withdraw_withdraw -> {
                ToastUtil.show("立即提现")
            }
            R.id.withdraw_node -> {
                startActivity(Intent(this,MoneyRecordActivity::class.java))
            }
            R.id.withdraw_binding -> {
                ToastUtil.show("绑定支付宝")
            }
            R.id.withdraw_money50 -> {
                initButton()
                mBinding.withdrawMoney50.setBackgroundResource(R.mipmap.withdraw_select)
            }
            R.id.withdraw_money100 -> {
                initButton()
                mBinding.withdrawMoney100.setBackgroundResource(R.mipmap.withdraw_select)
            }
            R.id.withdraw_money150 -> {
                initButton()
                mBinding.withdrawMoney150.setBackgroundResource(R.mipmap.withdraw_select)
            }
        }
    }

    fun initButton(){
        mBinding.withdrawMoney50.setBackgroundResource(R.mipmap.withdraw_normal)
        mBinding.withdrawMoney100.setBackgroundResource(R.mipmap.withdraw_normal)
        mBinding.withdrawMoney150.setBackgroundResource(R.mipmap.withdraw_normal)
    }
}