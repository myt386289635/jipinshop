package com.example.administrator.jipinshop.adapter

import android.content.Context
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.CircleTitleBean
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

/**
 * @author 莫小婷
 * @create 2020/3/16
 * @Describe
 */
class KTTabAdapter2 : CommonNavigatorAdapter {

    private var mTitle: MutableList<CircleTitleBean.DataBean>
    private var mOnClickItem: OnClickItem

    constructor(mTitle: MutableList<CircleTitleBean.DataBean> , onClickItem: OnClickItem) {
        this.mTitle = mTitle
        mOnClickItem = onClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var simplePagerTitleView = ColorFlipPagerTitleView(context, context.resources.getDimension(R.dimen.x25).toInt(), context.resources.getDimension(R.dimen.x25).toInt())
        simplePagerTitleView.text = mTitle[index].title
        var paint =  simplePagerTitleView.paint
        paint.isFakeBoldText = true
        simplePagerTitleView.normalColor = context.resources.getColor(R.color.color_565252)
        simplePagerTitleView.selectedColor = context.resources.getColor(R.color.color_E25838)
        simplePagerTitleView.setOnClickListener {
            mOnClickItem.onFirstMenu(index)
        }
        return simplePagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
       return null
    }

    interface OnClickItem{
        fun onFirstMenu( index: Int)
    }
}