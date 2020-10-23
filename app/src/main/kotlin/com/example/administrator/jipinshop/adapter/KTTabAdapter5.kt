package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.util.ToastUtil
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

/**
 * @author 莫小婷
 * @create 2020/10/22
 * @Describe
 */
class KTTabAdapter5 : CommonNavigatorAdapter {

    private var mTitle: MutableList<String?>
    private var mViewPager: ViewPager
    private var mainMenu : MagicIndicator

    constructor(mTitle: MutableList<String?>, mViewPager: ViewPager, mainMenu: MagicIndicator){
        this.mTitle = mTitle
        this.mViewPager = mViewPager
        this.mainMenu = mainMenu
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        var commonPagerTitleView = CommonPagerTitleView(context)
        //自定义布局
        var customLayout = LayoutInflater.from(context).inflate(R.layout.navigator_custom_title2, null)
        var titleText = customLayout.findViewById<TextView>(R.id.item_text)
        titleText.text = mTitle[index]
        titleText.setTextColor(context.resources.getColor(R.color.color_202020))
        var originalDrawable = titleText.background
        var wrappedDrawable = DrawableCompat.wrap(originalDrawable)
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(Color.WHITE))
        titleText.setBackgroundDrawable(wrappedDrawable)
        commonPagerTitleView.setContentView(customLayout)
        commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {

            override fun onDeselected(index: Int, totalCount: Int) {
                titleText.setTextColor(context.resources.getColor(R.color.color_202020))
                var originalDrawable = titleText.background
                var wrappedDrawable = DrawableCompat.wrap(originalDrawable)
                DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(Color.WHITE))
                titleText.setBackgroundDrawable(wrappedDrawable)
            }

            override fun onSelected(index: Int, totalCount: Int) {
                titleText.setTextColor(context.resources.getColor(R.color.color_white))
                var originalDrawable = titleText.background
                var wrappedDrawable = DrawableCompat.wrap(originalDrawable)
                DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(context.resources.getColor(R.color.color_E25838)))
                titleText.setBackgroundDrawable(wrappedDrawable)
            }

            override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}

            override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}

        }
        commonPagerTitleView.setOnClickListener {
            mViewPager.currentItem = index
            mainMenu.onPageSelected(index)
            mainMenu.onPageScrolled(index,0.0F, 0)
        }
        return commonPagerTitleView
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
        return null
    }
}