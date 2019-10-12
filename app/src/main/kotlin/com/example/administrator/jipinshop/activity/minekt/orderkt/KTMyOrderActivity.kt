package com.example.administrator.jipinshop.activity.minekt.orderkt

import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityOrderBinding
import com.example.administrator.jipinshop.fragment.orderkt.KTMyOrderFragment
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe 我的订单（淘宝订单）
 */
class KTMyOrderActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding : ActivityOrderBinding
    private lateinit var mFragments: MutableList<Fragment>
    private lateinit var mAdapter: HomeFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_order)
        mBinding.listener = this
        initView()
    }

    private fun initView() {
        mBinding.inClude?.let {
            it.titleTv.text = "我的订单"
            it.titleLine.visibility = View.INVISIBLE
        }
        mFragments = ArrayList()
        mAdapter = HomeFragmentAdapter(supportFragmentManager)
        mFragments.add(KTMyOrderFragment.getInstance("0"))//全部订单
        mFragments.add(KTMyOrderFragment.getInstance("1"))//即将到账
        mFragments.add(KTMyOrderFragment.getInstance("2"))//已到账
        mFragments.add(KTMyOrderFragment.getInstance("3"))//失效订单
        mAdapter.setFragments(mFragments)
        mBinding.viewPager.adapter = mAdapter
        mBinding.viewPager.offscreenPageLimit = mFragments.size - 1
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        initTabLayout()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.order_explain -> {
                startActivity(Intent(this, WebActivity::class.java)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "commission-rule.html")
                        .putExtra(WebActivity.title, "极品补贴规则说明")
                )
            }
        }
    }

    fun initTabLayout() {
        val textLether = ArrayList<Int>()
        for (i in mFragments.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.tablayout_order, null)
            val textView = view.findViewById<TextView>(R.id.tab_name)
            if (i == 0) {
                textView.text = "全部订单"
            } else if (i == 1) {
                textView.text = "即将到账"
            } else if (i == 2) {
                textView.text = "已到账"
            } else {
                textView.text = "失效订单"
            }
            mBinding.tabLayout.getTabAt(i)!!.customView = view
            val a = textView.paint.measureText(textView.text.toString()).toInt()
            textLether.add(a)
        }
        mBinding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.color_E25838))
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mBinding.tabLayout.post {
            //拿到tabLayout的mTabStrip属性
            val mTabStrip = mBinding.tabLayout.getChildAt(0) as LinearLayout
            val totle = textLether[0] + textLether[1] + textLether[2] + textLether[3]
            val dp10 = (mBinding.tabLayout.width - totle) / textLether.size
            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)
                tabView.setPadding(0, 0, 0, 0)
                val width = textLether[i] + dp10
                val params = tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = dp10 / 2
                params.rightMargin = dp10 / 2
                tabView.layoutParams = params
                tabView.invalidate()
            }
        }
    }
}