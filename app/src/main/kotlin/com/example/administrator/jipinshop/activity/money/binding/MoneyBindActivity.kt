package com.example.administrator.jipinshop.activity.money.binding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.alipay.sdk.app.AuthTask
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.AuthResult
import com.example.administrator.jipinshop.databinding.ActivityMoneyBindBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe 绑定支付宝页面
 */
class MoneyBindActivity : BaseActivity(), View.OnClickListener, MoneyBindView {

    @Inject
    lateinit var mPresenter: MoneyBindPresenter

    private lateinit var mBinding: ActivityMoneyBindBinding
    private var mDialog: Dialog? = null
    private var alipayNickname = ""//支付宝昵称
    private var realname = ""//真实姓名
    private var SDK_AUTH_FLAG = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_bind)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "绑定支付宝"
        }
        alipayNickname = intent.getStringExtra("name")
        realname = intent.getStringExtra("realName")
        if (TextUtils.isEmpty(alipayNickname)){
            mBinding.bindText.text = "去授权"
        }else{
            mBinding.bindText.text =  alipayNickname
        }
        if (TextUtils.isEmpty(realname)){
            mBinding.bindName.setText("")
        }else{
            mBinding.bindName.setText(realname)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bind_layout -> {
                if (TextUtils.isEmpty(alipayNickname)){
                    HasPermissionsUtil.permission(this,object : HasPermissionsUtil(){
                        override fun hasPermissionsSuccess() {
                            super.hasPermissionsSuccess()
                            mDialog = ProgressDialogView().createLoadingDialog(this@MoneyBindActivity, "")
                            mDialog?.let { it ->
                                if (!it.isShowing)
                                    it.show()
                            }
                            mPresenter.getAlipayAuthInfo(this@MoneyBindActivity.bindToLifecycle())
                        }
                    }, Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }else {
                    ToastUtil.show("已绑定支付宝账号，请勿重复绑定")
                }
            }
            R.id.bind_account -> {
                if (TextUtils.isEmpty(alipayNickname)){
                    ToastUtil.show("请绑定支付宝账号")
                    return
                }
                if (TextUtils.isEmpty(mBinding.bindName.text.toString().trim())){
                    ToastUtil.show("请先输入真实姓名")
                    return
                }
                mDialog = ProgressDialogView().createLoadingDialog(this, "")
                mDialog?.let { it ->
                    if (!it.isShowing)
                        it.show()
                }
                mPresenter.bindingAlipay(mBinding.bindName.text.toString(),this.bindToLifecycle())
            }
            R.id.title_back -> {
                var intent = Intent()
                intent.putExtra("name" , alipayNickname)
                intent.putExtra("realName" , realname)
                setResult(200,intent)
                finish()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isHideInput(view, ev)) {
                showKeyboard(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isHideInput(v: View?, ev: MotionEvent): Boolean {
        return if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            !(ev.x > left && ev.x < right && ev.y > top
                    && ev.y < bottom)
        }else{
            false
        }
    }

    //支付宝完整版授权
    fun authV2(authInfo : String){
        val authRunnable = Runnable {
            // 构造AuthTask 对象
            val authTask = AuthTask(this)
            // 调用授权接口，获取授权结果
            val result = authTask.authV2(authInfo, true)
            val msg = Message()
            msg.what = SDK_AUTH_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        // 必须异步调用
        var authThread = Thread(authRunnable)
        authThread.start()
    }

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_AUTH_FLAG -> {
                    val authResult = AuthResult(msg.obj as Map<String, String>, true)
                    val resultStatus = authResult.resultStatus
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.resultCode, "200")) {
                        var authCode = authResult.authCode
                        authCode?.let {
                            mDialog = ProgressDialogView().createLoadingDialog(this@MoneyBindActivity, "")
                            mDialog?.let { it ->
                                if (!it.isShowing)
                                    it.show()
                            }
                            mPresenter.alipayLogin(it,this@MoneyBindActivity.bindToLifecycle())
                        }
                    } else {
                        ToastUtil.show(String.format("业务失败，结果码: %s", authResult.resultStatus))
                    }
                }
            }
        }
    }

    override fun info(info : String) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        authV2(info)
    }

    override fun onSuccess(name: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        if (!TextUtils.isEmpty(name) && name != null){
           alipayNickname = name
        }
        mBinding.bindText.text =  alipayNickname
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun binding() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        realname = mBinding.bindName.text.toString()
        ToastUtil.show("绑定成功")
        var intent = Intent()
        intent.putExtra("name" , alipayNickname)
        intent.putExtra("realName" , realname)
        setResult(200,intent)
        finish()
    }

    override fun onBackPressed() {
        var intent = Intent()
        intent.putExtra("name" , alipayNickname)
        intent.putExtra("realName" , realname)
        setResult(200,intent)
        super.onBackPressed()
    }
}