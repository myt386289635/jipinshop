package com.example.administrator.jipinshop.fragment.publishkt.question

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
import com.example.administrator.jipinshop.fragment.evaluationkt.hot.EvaHotFragment
import com.example.administrator.jipinshop.fragment.publishkt.question.pass.PassQuesFragment
import com.example.administrator.jipinshop.fragment.publishkt.question.unpass.UnPassQuesFragment

/**
 * @author 莫小婷
 * @create 2019/8/22
 * @Describe
 */
class MyQuestionFragment :  DBBaseFragment(){

    private lateinit var mBinding: FragmentMyInventoryBinding
    private var mList: MutableList<Fragment> = mutableListOf()
    private var mTitle : ArrayList<String> = arrayListOf("已发布","审核中","未通过")
    private lateinit var mAdapter : HomeFragmentAdapter
    private var boolean : Boolean = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && boolean){
            mList.add(PassQuesFragment())
            mList.add(UnPassQuesFragment.getInstance("0"))
            mList.add(UnPassQuesFragment.getInstance("-1"))
            mAdapter.notifyDataSetChanged()
            mBinding.viewPager.offscreenPageLimit = mList.size - 1
            boolean = false
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_inventory,container,false)
        return  mBinding.root
    }

    override fun initView() {
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mBinding.tabLayout.setTabTextColors(resources.getColor(R.color.color_9D9D9D),resources.getColor(R.color.color_202020))

        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mAdapter.setFragments(mList)
        mAdapter.setTitle(mTitle)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.viewPager.setNoScroll(true)
    }

}