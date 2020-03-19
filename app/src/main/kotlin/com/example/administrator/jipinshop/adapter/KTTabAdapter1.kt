package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v4.view.ViewPager
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.view.tab.PagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author 莫小婷
 * @create 2019/12/12
 * @Describe 正常有title和下划线的tab
 */
class KTTabAdapter1 : CommonNavigatorAdapter{

    private var mTitle: MutableList<String>
    private var mViewPager: ViewPager

    constructor(title: MutableList<String> ,viewPager: ViewPager){
        mTitle = title
        mViewPager = viewPager
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val simplePagerTitleView = PagerTitleView(context)
        simplePagerTitleView.text = mTitle[index]
        simplePagerTitleView.textSize = 18f //设置导航的文字大小
        simplePagerTitleView.normalColor = context.resources.getColor(R.color.color_white)
        simplePagerTitleView.selectedColor = context.resources.getColor(R.color.color_FFD224)
        simplePagerTitleView.setOnClickListener {
            mViewPager.currentItem = index
        }
        return simplePagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        indicator.lineHeight = context.resources.getDimension(R.dimen.y5)
        indicator.roundRadius = context.resources.getDimension(R.dimen.y3)
        indicator.setColors(context.resources.getColor(R.color.color_FFD224))
        return indicator
    }

}