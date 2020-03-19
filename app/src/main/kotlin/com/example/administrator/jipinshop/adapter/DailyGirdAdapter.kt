package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2020/3/18
 * @Describe
 */
class DailyGirdAdapter : BaseAdapter {

    private var mList: List<String>
    private var mContext: Context
    private lateinit var mOnGridItem: OnGridItem

    fun setOnClick(onGridItem: OnGridItem){
        mOnGridItem = onGridItem
    }

    constructor(mList: List<String>, mContext: Context) {
        this.mList = mList
        this.mContext = mContext
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_daily_grid, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.let {
            GlideApp.loderImage(mContext, mList[position], it.mImageView,0,0)
            it.mImageView.setOnClickListener { v ->
                mOnGridItem.onGrid(position, v)
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

    class ViewHolder {

        var mImageView: ImageView

        constructor(itemView :View){
            mImageView = itemView.findViewById(R.id.item_image)
        }
    }

    interface OnGridItem {
        fun onGrid(position: Int, view: View)
    }
}