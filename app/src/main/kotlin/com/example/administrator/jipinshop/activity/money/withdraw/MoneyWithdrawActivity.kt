package com.example.administrator.jipinshop.activity.money.withdraw

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.money.binding.MoneyBindActivity
import com.example.administrator.jipinshop.activity.money.record.MoneyRecordActivity
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityMoneyWithdrawBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 12:06
 * Description： 红包提现页面
 */
class MoneyWithdrawActivity : BaseActivity(), View.OnClickListener, MoneyWithdrawView {

    @Inject
    lateinit var mPresenter : MoneyWithdrawPresenter

    private lateinit var mBinding: ActivityMoneyWithdrawBinding
    private var limiMoney = 50
    private var mDialog: Dialog? = null
    private var alipayNickname = ""//支付宝昵称
    private var realname = ""//真实姓名

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_withdraw)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "提现"
        }
        mBinding.withdrawMoney.text = intent.getStringExtra("money")
        alipayNickname = intent.getStringExtra("name")
        realname = intent.getStringExtra("realName")
        if(TextUtils.isEmpty(alipayNickname) || TextUtils.isEmpty(realname)){
            mBinding.withdrawBindingText.text = "绑定账号"
        }else{
            mBinding.withdrawBindingText.text = "已绑定"
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.title_back -> {
                var intent = Intent()
                intent.putExtra("money" , mBinding.withdrawMoney.text.toString())
                intent.putExtra("name",alipayNickname)
                intent.putExtra("realName" , realname)
                setResult(300,intent)
                finish()
            }
            R.id.withdraw_withdraw -> {
                var money = BigDecimal(mBinding.withdrawMoney.text.toString()).toDouble()
                if (money < limiMoney){
                    ToastUtil.show("金额不足")
                    return
                }
                if(TextUtils.isEmpty(realname) || TextUtils.isEmpty(alipayNickname)){
                    ToastUtil.show("请绑定支付宝账号")
                    return
                }
                DialogUtil.LoginDialog(this, "是否提现到支付宝账号\n$alipayNickname","确定", "取消"){
                    mDialog = ProgressDialogView().createLoadingDialog(this, "")
                    mDialog?.let {
                        if (!it.isShowing)
                            it.show()
                    }
                    mPresenter.withdraw("" + limiMoney,this.bindToLifecycle())
                }
            }
            R.id.withdraw_node -> {
                startActivity(Intent(this,MoneyRecordActivity::class.java))
            }
            R.id.withdraw_binding -> {
                startActivityForResult(Intent(this, MoneyBindActivity::class.java)
                        .putExtra("name",alipayNickname)
                        .putExtra("realName" , realname)
                ,303)
            }
            R.id.withdraw_money50 -> {
                initButton()
                mBinding.withdrawMoney50.setBackgroundResource(R.mipmap.withdraw_select)
                limiMoney = 50
            }
            R.id.withdraw_money100 -> {
                initButton()
                mBinding.withdrawMoney100.setBackgroundResource(R.mipmap.withdraw_select)
                limiMoney = 100
            }
            R.id.withdraw_money150 -> {
                initButton()
                mBinding.withdrawMoney150.setBackgroundResource(R.mipmap.withdraw_select)
                limiMoney = 150
            }
        }
    }

    fun initButton(){
        mBinding.withdrawMoney50.setBackgroundResource(R.mipmap.withdraw_normal)
        mBinding.withdrawMoney100.setBackgroundResource(R.mipmap.withdraw_normal)
        mBinding.withdrawMoney150.setBackgroundResource(R.mipmap.withdraw_normal)
    }

    override fun onSuccess() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        var oldMoney = BigDecimal(mBinding.withdrawMoney.text.toString())
        var slimiMoney = BigDecimal("" + limiMoney)
        mBinding.withdrawMoney.text = oldMoney.subtract(slimiMoney).stripTrailingZeros().toPlainString()
        startActivity(Intent(this,MoneyRecordActivity::class.java))
        ToastUtil.show("提现申请成功")
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onBackPressed() {
        var intent = Intent()
        intent.putExtra("money" , mBinding.withdrawMoney.text.toString())
        intent.putExtra("name",alipayNickname)
        intent.putExtra("realName" , realname)
        setResult(300,intent)
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 303 && resultCode == 200){
            data?.let {
                alipayNickname = it.getStringExtra("name")
                realname = it.getStringExtra("realName")
                if(TextUtils.isEmpty(alipayNickname) || TextUtils.isEmpty(realname)){
                    mBinding.withdrawBindingText.text = "绑定账号"
                }else{
                    mBinding.withdrawBindingText.text = "已绑定"
                }
            }
        }
    }
}