package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SeckillTabBean
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe
 */
class KTTabAdapter6 : CommonNavigatorAdapter {

    private var mTitle: MutableList<SeckillTabBean.DataBean>
    private var mOnClickItem: OnClickItem

    constructor(mTitle: MutableList<SeckillTabBean.DataBean>, mOnClickItem: OnClickItem) {
        this.mTitle = mTitle
        this.mOnClickItem = mOnClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var commonPagerTitleView = CommonPagerTitleView(context)
        //自定义布局
        var customLayout = LayoutInflater.from(context).inflate(R.layout.navigator_custom_title3, null)
        var item_title = customLayout.findViewById<TextView>(R.id.item_title)
        var item_decs = customLayout.findViewById<TextView>(R.id.item_decs)
        item_title.text = mTitle[index].startTime
        when(mTitle[index].status){
            3 -> {
                item_decs.text = "即将开始"
            }
            2 -> {
                item_decs.text = "抢购中"
            }
            else -> {
                item_decs.text = "已结束"
            }
        }
        item_decs.setBackgroundColor(Color.TRANSPARENT)
        item_title.setTextColor(context.resources.getColor(R.color.color_FACBC4))
        item_decs.setTextColor(context.resources.getColor(R.color.color_FACBC4))
        commonPagerTitleView.setContentView(customLayout)
        commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {

            override fun onDeselected(index: Int, totalCount: Int) {
                item_title.setTextColor(context.resources.getColor(R.color.color_FACBC4))
                item_decs.setTextColor(context.resources.getColor(R.color.color_FACBC4))
                item_title.paint.isFakeBoldText = false
                item_decs.paint.isFakeBoldText = false
                item_decs.setBackgroundColor(Color.TRANSPARENT)
            }

            override fun onSelected(index: Int, totalCount: Int) {
                item_title.setTextColor(context.resources.getColor(R.color.color_white))
                item_decs.setTextColor(context.resources.getColor(R.color.color_E25838))
                item_title.paint.isFakeBoldText = true
                item_decs.paint.isFakeBoldText = true
                item_decs.setBackgroundResource(R.drawable.bg_classfiy)
            }

            override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}

            override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}

        }
        commonPagerTitleView.setOnClickListener {
            mOnClickItem.onSelectMenu(index)
        }
        return commonPagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
        return null
    }

    interface OnClickItem{
        fun onSelectMenu(index: Int)
    }
}