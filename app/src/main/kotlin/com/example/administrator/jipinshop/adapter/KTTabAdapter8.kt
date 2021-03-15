package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.administrator.jipinshop.R
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

/**
 * @author 莫小婷
 * @create 2021/3/10
 * @Describe
 */
class KTTabAdapter8 : CommonNavigatorAdapter {

    private var mTitle: MutableList<String>
    private var mOnItem: OnItem

    constructor(title: MutableList<String> , onItem: OnItem){
        mTitle = title
        mOnItem = onItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var commonPagerTitleView = CommonPagerTitleView(context)
        //自定义布局
        var customLayout = LayoutInflater.from(context).inflate(R.layout.navigator_custom_title5, null)
        var item_text = customLayout.findViewById<TextView>(R.id.item_text)
        item_text.text = mTitle[index]
        commonPagerTitleView.setContentView(customLayout)
        commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {

            override fun onDeselected(index: Int, totalCount: Int) {
                item_text.paint.isFakeBoldText = false
                item_text.textSize = 14f
            }

            override fun onSelected(index: Int, totalCount: Int) {
                item_text.paint.isFakeBoldText = true
                item_text.textSize = 16f
            }

            override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}

            override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}

        }
        commonPagerTitleView.setOnClickListener {
            mOnItem.onClick(index)
        }
        return commonPagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        var indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        indicator.lineHeight = UIUtil.dip2px(context, 2.5).toFloat()
        indicator.roundRadius = UIUtil.dip2px(context, 1.25).toFloat()
        indicator.setColors(context.resources.getColor(R.color.color_202020))
        return indicator
    }

    interface OnItem{
        fun onClick(position:Int)
    }
}