package com.example.administrator.jipinshop.fragment.publishkt.inventory

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentMyInventoryBinding
import com.example.administrator.jipinshop.fragment.publishkt.inventory.published.PublishedFragment
import com.example.administrator.jipinshop.fragment.publishkt.inventory.unpass.UnPassFragment

/**
 * @author 莫小婷
 * @create 2019/8/22
 * @Describe
 */
class MyInventoryFragment : DBBaseFragment(){

    private lateinit var mBinding: FragmentMyInventoryBinding
    private var mList: MutableList<Fragment> = mutableListOf()
    private var mTitle : ArrayList<String> = arrayListOf("已发布","审核中","草稿箱","未通过")
    private lateinit var mAdapter : HomeFragmentAdapter

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_inventory,container,false)
        return  mBinding.root
    }

    override fun initView() {
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mBinding.tabLayout.setTabTextColors(resources.getColor(R.color.color_9D9D9D),resources.getColor(R.color.color_202020))

        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mList.add(PublishedFragment.getInstance("2"))//已发布
        mList.add(PublishedFragment.getInstance("1"))//审核中
        mList.add(UnPassFragment.getInstance("0"))//草稿箱
        mList.add(UnPassFragment.getInstance("-1"))//未通过
        mAdapter.setFragments(mList)
        mAdapter.setTitle(mTitle)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mBinding.viewPager.setNoScroll(true)
    }

}