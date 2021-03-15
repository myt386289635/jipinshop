package com.example.administrator.jipinshop.adapter

import android.content.Context
import com.example.administrator.jipinshop.view.tab.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author 莫小婷
 * @create 2020/4/23
 * @Describe 正常有title和下划线的tab 通用
 */
class KTTabAdapter4 : CommonNavigatorAdapter {

    private var mTitle: MutableList<String>
    private var mOnItem: OnItem? = null
    private var leftPadding = 0
    private var rightPadding = 0
    private var normalColor = 0
    private var selectedColor = 0
    private var textSize  = 14f //字体大小 默认为14
    private var isBold = false //是否加粗字体 默认不加粗

    constructor(title: MutableList<String>){
        mTitle = title

    }

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    fun setPadding(leftPadding : Int,rightPadding: Int ){
        this.leftPadding = leftPadding
        this.rightPadding = rightPadding
    }

    fun setColor(normalColor : Int , selectedColor: Int){
        this.normalColor = normalColor
        this.selectedColor = selectedColor
    }

    fun setTextSize(textSize : Float){
        this.textSize = textSize
    }

    //指示器默认文字是否加粗
    fun isBold(bold: Boolean){
        isBold = bold
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var simplePagerTitleView = ColorFlipPagerTitleView(context, leftPadding, rightPadding)
        simplePagerTitleView.text = mTitle[index]
        simplePagerTitleView.textSize = textSize
        var paint =  simplePagerTitleView.paint
        paint.isFakeBoldText = isBold
        simplePagerTitleView.normalColor = normalColor
        simplePagerTitleView.selectedColor = selectedColor
        simplePagerTitleView.setOnClickListener {
            mOnItem?.onClick(index)
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
        indicator.setColors(selectedColor) //引导线的颜色默认为选中的颜色
        return indicator
    }

    interface OnItem{
        fun onClick(position:Int)
    }
}