package com.example.administrator.jipinshop.adapter

import android.content.Context
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import android.R.layout
import android.view.LayoutInflater
import android.widget.TextView
import com.example.administrator.jipinshop.bean.CircleTitleBean
import java.util.*


/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe 自定义布局
 */
class KTTabAdapter3 : CommonNavigatorAdapter {

    private var mTitle: MutableList<CircleTitleBean.DataBean.ChildrenBean>
    private var mOnClickItem: OnClickItem

    constructor(mTitle: MutableList<CircleTitleBean.DataBean.ChildrenBean> , onClickItem: OnClickItem) {
        this.mTitle = mTitle
        mOnClickItem = onClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var commonPagerTitleView = CommonPagerTitleView(context)
        //自定义布局
        var customLayout = LayoutInflater.from(context).inflate(R.layout.navigator_custom_title, null)
        var titleText = customLayout.findViewById<TextView>(R.id.item_text)
        titleText.text = mTitle[index].title
        commonPagerTitleView.setContentView(customLayout)
        commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {
            override fun onDeselected(index: Int, totalCount: Int) {
                titleText.setTextColor(context.resources.getColor(R.color.color_565252))
            }

            override fun onSelected(index: Int, totalCount: Int) {
                titleText.setTextColor(context.resources.getColor(R.color.color_E25838))
            }

            override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}

            override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}

        }
        commonPagerTitleView.setOnClickListener {
            mOnClickItem.onSendMenu(index)
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
        fun onSendMenu( index: Int)
    }
}