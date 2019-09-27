package com.example.administrator.jipinshop.activity.minekt.freekt

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.fragment.freekt.end.PayPendedFragment
import com.example.administrator.jipinshop.fragment.freekt.pay.PayPendingFragment
import java.util.*

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe 我的免单
 */
class MineFreeActivity : BaseActivity(){

    @BindView(R.id.title_back)
    lateinit var mTitleBack: ImageView
    @BindView(R.id.title_tv)
    lateinit var mTitleTv: TextView
    @BindView(R.id.tab_layout)
    lateinit var mTabLayout: TabLayout
    @BindView(R.id.view_pager)
    lateinit var mViewPager: ViewPager
    @BindView(R.id.title_line)
    lateinit var mTitleLine: View

    private lateinit var mFragments: MutableList<Fragment>
    private lateinit var mAdapter: HomeFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foval)
        mButterKnife = ButterKnife.bind(this)
        initView()
    }

    private fun initView() {
        mTitleBack.setOnClickListener { v -> finish() }
        mTitleTv.text = "我的免单"
        mTitleLine.visibility = View.INVISIBLE
        mFragments = ArrayList()
        mAdapter = HomeFragmentAdapter(supportFragmentManager)
        mFragments.add(PayPendingFragment.getInstance())
        mFragments.add(PayPendedFragment.getInstance("1"))//待返现
        mFragments.add(PayPendedFragment.getInstance("3"))//已完成
        mFragments.add(PayPendedFragment.getInstance("-1"))//已失效
        mAdapter.setFragments(mFragments)
        mViewPager.adapter = mAdapter
        mViewPager.offscreenPageLimit = mFragments.size - 1
        mTabLayout.setupWithViewPager(mViewPager)
        initTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        mButterKnife.unbind()
    }

    fun initTabLayout() {
        val textLether = ArrayList<Int>()
        for (i in mFragments.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.tablayout_user, null)
            val textView = view.findViewById<TextView>(R.id.tab_name)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            if (i == 0) {
                textView.text = "待付款"
            } else if (i == 1) {
                textView.text = "待返现"
            } else if (i == 2) {
                textView.text = "已完成"
            } else {
                textView.text = "已失效"
            }
            mTabLayout.getTabAt(i)!!.customView = view
            val a = textView.paint.measureText(textView.text.toString()).toInt()
            textLether.add(a)
        }
        mTabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.color_E25838))
        mTabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mTabLayout.post {
            //拿到tabLayout的mTabStrip属性
            val mTabStrip = mTabLayout.getChildAt(0) as LinearLayout
            val totle = textLether[0] + textLether[1] + textLether[2] + textLether[3]
            val dp10 = (mTabLayout.width - totle) / textLether.size
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