package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2020/2/25
 * @Describe 适用于普遍的轮播图
 */
class KTPagerAdapter3 : PagerAdapter {

    private lateinit var mAdListBeans: List<EvaluationTabBean.DataBean.AdListBean>
    private val mContext: Context
    private lateinit var point: List<ImageView>
    private var imgCenter: Boolean = true
    private var mOnClickItem: OnClickItem? = null

    fun setOnClickItem(onClickItem: OnClickItem) {
        mOnClickItem = onClickItem
    }

    fun setImgCenter(imgCenter: Boolean) {
        this.imgCenter = imgCenter
    }

    constructor(context: Context){
        mContext = context
    }

    fun setPoint(point: List<ImageView>) {
        this.point = point
    }

    fun setList(list: List<EvaluationTabBean.DataBean.AdListBean>) {
        mAdListBeans = list
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return if (mAdListBeans.isEmpty()){
            0
        }else{
            mAdListBeans.size
        }
    }

    //给ImageView设置显示的图片
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_common_banner, container, false)
        val imageView = view.findViewById<View>(R.id.recommend_img_rotate) as ImageView
        if (imgCenter) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        GlideApp.loderImage(mContext,mAdListBeans[position].img,imageView,0,0)
        imageView.setOnClickListener {
            mOnClickItem?.onClickItem(position)
        }
        container.addView(view)
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    interface OnClickItem {
        fun onClickItem(postion: Int)
    }
}