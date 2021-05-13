package com.example.administrator.jipinshop.adapter

import android.content.Context
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

/**
 * @author 莫小婷
 * @create 2021/5/12
 * @Describe
 */
class KTTabAdapter9 : CommonNavigatorAdapter {

    private var mTitle: MutableList<String>
    private lateinit var mOnClickItem: OnClickItem

    constructor(mTitle: MutableList<String>) {
        this.mTitle = mTitle
    }

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var simplePagerTitleView = ColorFlipPagerTitleView(context, context.resources.getDimension(R.dimen.x33).toInt(),
                context.resources.getDimension(R.dimen.x33).toInt())
        simplePagerTitleView.text = mTitle[index]
        simplePagerTitleView.textSize = 16f
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