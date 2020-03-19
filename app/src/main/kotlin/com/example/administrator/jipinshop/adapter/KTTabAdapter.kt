package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v4.view.ViewPager
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe 正常有title和下划线的tab
 */
class KTTabAdapter : CommonNavigatorAdapter{

    private var mTitle: MutableList<String>
    private var mViewPager: ViewPager

    constructor(title: MutableList<String> ,viewPager: ViewPager){
        mTitle = title
        mViewPager = viewPager
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var simplePagerTitleView = ColorFlipPagerTitleView(context, context.resources.getDimension(R.dimen.x20).toInt(), context.resources.getDimension(R.dimen.x20).toInt())
        simplePagerTitleView.text = mTitle[index]
        simplePagerTitleView.normalColor = context.resources.getColor(R.color.color_white)
        simplePagerTitleView.selectedColor = context.resources.getColor(R.color.color_white)
        simplePagerTitleView.setOnClickListener {
            mViewPager.currentItem = index
        }
        return simplePagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        var indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        indicator.lineHeight = UIUtil.dip2px(context, 2.5).toFloat()
        indicator.roundRadius = UIUtil.dip2px(context, 1.25).toFloat()
        indicator.setColors(context.resources.getColor(R.color.color_white))
        return indicator
    }

}