package com.example.administrator.jipinshop.activity.home.comprehensive

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.cheapgoods.zCheapBuyFragment
import com.example.administrator.jipinshop.activity.home.hot.HomeHotFragment
import com.example.administrator.jipinshop.activity.home.seckill.SeckillFragment
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityComprehensiveBinding

/**
 * @author 莫小婷
 * @create 2021/2/22
 * @Describe 综合页
 */
class ComprehensiveActivity : BaseActivity(){

    private lateinit var mBinding : ActivityComprehensiveBinding
    private lateinit var mFragments: MutableList<Fragment>
    private lateinit var mAdapter: HomeFragmentAdapter
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_comprehensive)
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init()
        initView()
    }

    private fun initView() {
        page = intent.getIntExtra("page",0)
        mFragments = ArrayList()
        mFragments.add(HomeDetailFragment.getInstance("7","9.9包邮"))//9.9包邮
        mFragments.add(HomeDetailFragment.getInstance("8","1元购"))//一元购
        mFragments.add(SeckillFragment())//限时秒杀
        mFragments.add(zCheapBuyFragment.getInstance(true))//百万补贴
        mFragments.add(HomeHotFragment())//热销榜单
        mAdapter = HomeFragmentAdapter(supportFragmentManager)
        mAdapter.setFragments(mFragments)
        mBinding.viewPager.adapter = mAdapter
        mBinding.viewPager.offscreenPageLimit = mFragments.size - 1
        mBinding.viewPager.setNoScroll(true)
        mBinding.viewPager.currentItem = page
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        initTabLayout()
    }

    private fun initTabLayout() {
        val view2 = LayoutInflater.from(this).inflate(R.layout.tab_item1, null)
        mBinding.tabLayout.getTabAt(0)!!.customView = view2
        val view3 = LayoutInflater.from(this).inflate(R.layout.tab_item2, null)
        mBinding.tabLayout.getTabAt(1)!!.customView = view3
        val view1 = LayoutInflater.from(this).inflate(R.layout.tab_item3, null)
        mBinding.tabLayout.getTabAt(2)!!.customView = view1
        val view4 = LayoutInflater.from(this).inflate(R.layout.tab_item4, null)
        mBinding.tabLayout.getTabAt(3)!!.customView = view4
        val view5 = LayoutInflater.from(this).inflate(R.layout.tab_item5, null)
        mBinding.tabLayout.getTabAt(4)!!.customView = view5
        //水波纹颜色
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(this.resources.getColor(R.color.transparent))
    }

}