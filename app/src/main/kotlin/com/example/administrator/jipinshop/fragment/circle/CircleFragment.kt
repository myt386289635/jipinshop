package com.example.administrator.jipinshop.fragment.circle

import android.databinding.DataBindingUtil
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.adapter.KTTabAdapter1
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentCircleBinding
import com.example.administrator.jipinshop.fragment.circle.daily.DailyFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/3/16
 * @Describe 发圈页面
 */
class CircleFragment : DBBaseFragment(){

    @Inject
    lateinit var mPresenter: CirclePresenter

    private lateinit var mBinding: FragmentCircleBinding
    private lateinit var mTitle: MutableList<String>
    private lateinit var mFragments: MutableList<Fragment>
    private lateinit var mAdapter: HomeFragmentAdapter
    private var once: Boolean = true

    companion object{
        @JvmStatic
        fun getInstance(): CircleFragment{
            return CircleFragment()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once) {
            mFragments.add(DailyFragment.getInstance("1"))//每日精选
            mFragments.add(DailyFragment.getInstance("2"))//宣传素材
            mBinding.viewPager.offscreenPageLimit = mFragments.size - 1
            mAdapter.notifyDataSetChanged()
            once = false
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_circle,container,false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)

        //tab
        mTitle = mutableListOf()
        mTitle.add("每日精选")
        mTitle.add("宣传素材")
        var commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = KTTabAdapter1(mTitle,mBinding.viewPager)
        mBinding.viewIndicator.navigator = commonNavigator
        var titleContainer = commonNavigator.titleContainer
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return context!!.resources.getDimension(R.dimen.x178).toInt()
            }
        }
        ViewPagerHelper.bind(mBinding.viewIndicator, mBinding.viewPager)

        mFragments = mutableListOf()
        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mAdapter.setFragments(mFragments)
        mBinding.viewPager.adapter = mAdapter
    }
}