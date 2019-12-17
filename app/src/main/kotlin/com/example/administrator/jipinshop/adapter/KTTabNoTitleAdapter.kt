package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import com.example.administrator.jipinshop.R
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView

/**
 * @author 莫小婷
 * @create 2019/12/12
 * @Describe 没有title的，只有下划线的tab
 */
class KTTabNoTitleAdapter : CommonNavigatorAdapter{

    private var mFragment: MutableList<Fragment>

    constructor(fragment: MutableList<Fragment>){
        mFragment = fragment
    }

    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        return DummyPagerTitleView(context)
    }

    override fun getCount(): Int {
        return mFragment.size
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        var indicator = LinePagerIndicator(context)
        var lineHeight = context.resources.getDimension(R.dimen.y5)
        indicator.lineHeight = lineHeight
        indicator.setColors(Color.parseColor("#E25838"))
        return indicator
    }

}