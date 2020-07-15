package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.school.video.VideoActivity
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.databinding.ItemSchoolBinding
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe
 */
class KTSchoolSpecialAdapter : RecyclerView.Adapter<KTSchoolSpecialAdapter.ViewHolder>{

    private var mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>
    private var mContext: Context

    constructor(mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>, mContext: Context){
        this.mList = mList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemSchoolBinding = DataBindingUtil.inflate<ItemSchoolBinding>(LayoutInflater.from(mContext), R.layout.item_school, viewGroup, false)
        return ViewHolder(itemSchoolBinding)
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.run {
            binding.date = mList[position]
            binding.executePendingBindings()
            itemView.setOnClickListener {
                mContext.startActivity(Intent(mContext, VideoActivity::class.java)
                        .putExtra("courseId", mList[position].id)
                )
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemSchoolBinding

        constructor(itemView: ItemSchoolBinding) : super(itemView.root){
            binding = itemView
        }
    }
}