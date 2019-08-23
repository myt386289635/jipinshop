package com.example.administrator.jipinshop.activity.minekt.publishkt

import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.databinding.ActivityPublishBinding
import com.example.administrator.jipinshop.fragment.publishkt.inventory.MyInventoryFragment
import com.example.administrator.jipinshop.fragment.publishkt.question.MyQuestionFragment

/**
 * @author 莫小婷
 * @create 2019/8/21
 * @Describe 我的发布
 */
class MyPublishActivity :BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityPublishBinding
    private var mList: MutableList<Fragment> = mutableListOf()
    private var mTitle : ArrayList<String> = arrayListOf("清单","问答")
    private lateinit var mAdapter : HomeFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_publish)
        mBinding.listener = this
        initView()
    }

    private fun initView() {
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mBinding.tabLayout.setTabTextColors(resources.getColor(R.color.color_9D9D9D),resources.getColor(R.color.color_202020))

        mAdapter = HomeFragmentAdapter(supportFragmentManager)
        mList.add(MyInventoryFragment())
        mList.add(MyQuestionFragment())
        mAdapter.setFragments(mList)
        mAdapter.setTitle(mTitle)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mBinding.viewPager.setNoScroll(true)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back ->{
                finish()
            }
        }
    }
}