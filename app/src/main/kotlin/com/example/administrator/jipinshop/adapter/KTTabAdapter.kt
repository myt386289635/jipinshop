package com.example.administrator.jipinshop.adapter

import android.content.Context
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.TBCategoryBean
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe 正常有title  无下划线
 */
class KTTabAdapter : CommonNavigatorAdapter{

    private var mTitle: MutableList<TBCategoryBean.DataBean>
    private var mOnClickItem: OnClickItem

    constructor(title: MutableList<TBCategoryBean.DataBean>, onClickItem: OnClickItem){
        mTitle = title
        mOnClickItem = onClickItem
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var simplePagerTitleView = ColorFlipPagerTitleView(context, context.resources.getDimension(R.dimen.x30).toInt(), context.resources.getDimension(R.dimen.x30).toInt())
        simplePagerTitleView.text = mTitle[index].name
        var paint =  simplePagerTitleView.paint
        paint.isFakeBoldText = true
        simplePagerTitleView.normalColor = context.resources.getColor(R.color.color_202020)
        simplePagerTitleView.selectedColor = context.resources.getColor(R.color.color_E25838)
        simplePagerTitleView.setOnClickListener {
            mOnClickItem.onMenu(index)
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
        fun onMenu(index: Int)
    }
}