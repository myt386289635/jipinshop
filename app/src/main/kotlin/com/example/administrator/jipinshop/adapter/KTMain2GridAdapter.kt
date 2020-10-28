package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @author 莫小婷
 * @create 2020/4/20
 * @Describe 宫格
 */
class KTMain2GridAdapter : BaseAdapter{

    private var mList: MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>
    private var mContext: Context
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>
    private var mUtil = ""

    constructor(list: MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>, context: Context , util : String){
        mList = list
        mContext = context
        mUtil = util
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.kthome_tab, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.run {
            mItemName.text = mList[position].title
            GlideApp.loderImage(mContext,mList[position].iconUrl,mImageView,0,0)
            itemView.setOnClickListener {
                appStatisticalUtil.addEvent(mUtil + (position + 1),transformer)
                ShopJumpUtil.openBanner(mContext,mList[position].type,mList[position].targetId,
                        mList[position].title,mList[position].source)
            }
        }
        return view!!
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var mItemName: TextView
        var mImageView: ImageView
        var item_container : LinearLayout

        constructor(view: View) : super(view){
            mItemName = view.findViewById(R.id.item_name)
            mImageView = view.findViewById(R.id.item_image)
            item_container = view.findViewById(R.id.item_container)
        }
    }
}