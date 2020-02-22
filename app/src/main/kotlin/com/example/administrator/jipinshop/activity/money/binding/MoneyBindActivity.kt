package com.example.administrator.jipinshop.activity.money.binding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityMoneyBindBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe 绑定支付宝页面
 */
class MoneyBindActivity : BaseActivity(), View.OnClickListener {

    @Inject
    lateinit var mPresenter: MoneyBindPresenter

    private lateinit var mBinding: ActivityMoneyBindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_money_bind)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "绑定支付宝"
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bind_layout -> {
                ToastUtil.show("绑定支付宝")
            }
            R.id.bind_account -> {
                ToastUtil.show("绑定账号")
            }
            R.id.title_back -> {
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
}