package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe 轮播图是4个圆角的
 */
class KTPagerAdapter : PagerAdapter{

    private lateinit var mAdListBeans: List<TbkIndexBean.DataBean.Ad1ListBean>
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

    fun setList(list: List<TbkIndexBean.DataBean.Ad1ListBean>) {
        mAdListBeans = list
    }

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view == p1
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
        GlideApp.loderRoundImage(mContext,mAdListBeans[position].img,imageView)
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