package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2019/12/12
 * @Describe 首页4宫格的adapter
 */
class KTMainGridAdapter : BaseAdapter{

    private var mList: MutableList<TbkIndexBean.DataBean.BoxListBean>
    private var mContext: Context

    constructor(list: MutableList<TbkIndexBean.DataBean.BoxListBean> , context: Context){
        mList = list
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
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
            GlideApp.loderCircleImage(mContext,mList[position].iconUrl,mImageView,0,0)
        }
        view?.setOnClickListener {
            ShopJumpUtil.openCommen(mContext,mList[position].type,mList[position].targetId,
                    mList[position].title)
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
       return if (mList.size <= 4){
           mList.size
       }else{
           4
       }
    }

    inner class ViewHolder {

        var mItemName: TextView
        var mImageView: ImageView

        constructor(view: View) {
            mItemName = view.findViewById(R.id.item_name)
            mImageView = view.findViewById(R.id.item_image)
        }
    }

}