package com.example.administrator.jipinshop.activity.minekt.recovery

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityOrderRecoveryBinding

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe 订单找回
 */
class OrderRecoveryActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityOrderRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_recovery)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mBinding.inClude?.titleTv?.text = "订单找回"
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.search_search -> {
                //搜索
                startActivity(Intent(this, OrderRecoveryResultActivity::class.java)
                        .putExtra("content",mBinding.searchEdit.text.toString())
                )
            }
        }
    }

}