package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
class KTSchoolGVAdapter : RecyclerView.Adapter<KTSchoolGVAdapter.ViewHolder>{

    private var mList: MutableList<SchoolHomeBean.DataBean.BoxListBean>
    private var mContext: Context

    constructor(list: MutableList<SchoolHomeBean.DataBean.BoxListBean>, context: Context){
        mList = list
        mContext = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.kthome_tab, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.run {
            mItemName.text = mList[position].title
            GlideApp.loderImage(mContext,mList[position].iconUrl,mImageView,0,0)
            itemView.setOnClickListener {
                ShopJumpUtil.openBanner(mContext,mList[position].type,mList[position].targetId,
                        mList[position].title,mList[position].source)
            }
            var wight = mContext.resources.getDimension(R.dimen.x120)
            var zz = (DistanceHelper.getAndroiodScreenwidthPixels(mContext) - (wight * 4)) / 4
            var result = zz / 2
            var layoutParams = item_container.layoutParams as LinearLayout.LayoutParams
            layoutParams.leftMargin = result.toInt()
            layoutParams.rightMargin = result.toInt()
            item_container.layoutParams = layoutParams

            var imageParams  = mImageView.layoutParams as LinearLayout.LayoutParams
            imageParams.width = mContext.resources.getDimension(R.dimen.x70).toInt()
            imageParams.height = mContext.resources.getDimension(R.dimen.y70).toInt()
            mImageView.layoutParams = imageParams
        }
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