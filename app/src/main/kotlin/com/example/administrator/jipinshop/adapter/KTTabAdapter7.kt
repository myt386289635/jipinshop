package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SeckillTabBean
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

/**
 * @author 莫小婷
 * @create 2021/2/23
 * @Describe
 */
class KTTabAdapter7 : CommonNavigatorAdapter {

    private var mTitle: MutableList<SeckillTabBean.List2Bean>
    private var mOnClickItem: OnClickItem

    constructor(mTitle: MutableList<SeckillTabBean.List2Bean>, mOnClickItem: OnClickItem) {
        this.mTitle = mTitle
        this.mOnClickItem = mOnClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var commonPagerTitleView = CommonPagerTitleView(context)
        //自定义布局
        var customLayout = LayoutInflater.from(context).inflate(R.layout.navigator_custom_title4, null)
        var item_text = customLayout.findViewById<TextView>(R.id.item_text)
        item_text.text = mTitle[index].title
        commonPagerTitleView.setContentView(customLayout)
        commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {

            override fun onDeselected(index: Int, totalCount: Int) {
                item_text.setBackgroundResource(R.drawable.bg_fc1a19)
                item_text.paint.isFakeBoldText = false
                item_text.setTextColor(context.resources.getColor(R.color.color_white))
            }

            override fun onSelected(index: Int, totalCount: Int) {
                item_text.setBackgroundResource(R.drawable.bg_dialog_white)
                item_text.paint.isFakeBoldText = true
                item_text.setTextColor(context.resources.getColor(R.color.color_E25838))
            }

            override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}

            override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}

        }
        commonPagerTitleView.setOnClickListener {
            mOnClickItem.onSelectSecMenu(index)
        }
        return commonPagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context?): IPagerIndicator? {
        return null
    }

    interface OnClickItem{
        fun onSelectSecMenu(index: Int)
    }
}