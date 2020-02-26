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
import com.alipay.sdk.app.OpenAuthTask
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.AuthResult
import com.example.administrator.jipinshop.databinding.ActivityMoneyBindBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import java.util.HashMap
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
            mBinding.bindText.text =  "已绑定"
        }
        if (TextUtils.isEmpty(realname)){
            mBinding.bindName.setText("")
        }else{
            mBinding.bindName.setText(realname)
            mBinding.bindName.isFocusableInTouchMode = false
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bind_layout -> {
                if (mBinding.bindText.text.toString() != "已绑定"){
                    HasPermissionsUtil.permission(this,object : HasPermissionsUtil(){
                        override fun hasPermissionsSuccess() {
                            super.hasPermissionsSuccess()
                            openAuthScheme()
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

    //支付宝极速版授权
    fun openAuthScheme() {

        // 传递给支付宝应用的业务参数
        var bizParams = HashMap<String, String>()
        bizParams["url"] = "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id=2021001135656812&scope=auth_user&state=init"

        // 支付宝回跳到你的应用时使用的 Intent Scheme。请设置为不和其它应用冲突的值。
        // 如果不设置，将无法使用 H5 中间页的方法(OpenAuthTask.execute() 的最后一个参数)回跳至
        // 你的应用。
        //
        // 参见 AndroidManifest 中 <AlipayResultActivity> 的 android:scheme，此两处
        // 必须设置为相同的值。
        var scheme = "jipinshop"

        // 唤起授权业务
        var task = OpenAuthTask(this)
        task.execute(
                scheme, // Intent Scheme
                OpenAuthTask.BizType.AccountAuth, // 业务类型
                bizParams, // 业务参数
                openAuthCallback, // 业务结果回调。注意：此回调必须被你的应用保持强引用
                true) // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转
    }

    private var openAuthCallback: OpenAuthTask.Callback = OpenAuthTask.Callback { i, s, bundle ->
        run {
            if (i == OpenAuthTask.OK) {
                var authCode = bundle.getString("auth_code")
                authCode?.let {
                    mDialog = ProgressDialogView().createLoadingDialog(this@MoneyBindActivity, "")
                    mDialog?.let { it ->
                        if (!it.isShowing)
                            it.show()
                    }
                    mPresenter.alipayLogin(it, this@MoneyBindActivity.bindToLifecycle())
                }
            } else {
                //授权失败的时候再次调用完整版的进行授权，为了防止支付宝出错极简版无法授权
                mDialog = ProgressDialogView().createLoadingDialog(this@MoneyBindActivity, "")
                mDialog?.let { it ->
                    if (!it.isShowing)
                        it.show()
                }
                mPresenter.getAlipayAuthInfo(this@MoneyBindActivity.bindToLifecycle())
            }
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
    //完整版代码结束

    override fun onSuccess(name: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        if (!TextUtils.isEmpty(name) && name != null){
           alipayNickname = name
        }
        mBinding.bindText.text =  "已绑定"
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