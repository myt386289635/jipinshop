package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.Action11Bean
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2019/10/29
 * @Describe
 */
class Action11GvAdapter : BaseAdapter{

    private var mGvListBeans: MutableList<Action11Bean.DataBean.CategoryListBean>
    private var mContext: Context

    constructor(mList: MutableList<Action11Bean.DataBean.CategoryListBean>, context: Context){
        this.mGvListBeans = mList
        this.mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_action_gv, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.run {
            item_text.text = mGvListBeans[position].categoryName
            GlideApp.loderCircleImage(mContext,mGvListBeans[position].iconImg,item_image,0,0)
            view!!.setOnClickListener {
                ToastUtil.show("点击了$position")
            }
        }
        return view!!
    }

    override fun getItem(position: Int): Any {
       return mGvListBeans[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mGvListBeans.size
    }

    class ViewHolder {

        var item_image: ImageView
        var item_text: TextView

        constructor(view: View){
            item_image = view.findViewById(R.id.item_image)
            item_text = view.findViewById(R.id.item_text)
        }
    }
}