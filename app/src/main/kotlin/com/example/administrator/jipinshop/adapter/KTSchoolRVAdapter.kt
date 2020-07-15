package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.school.SchoolSpecialActivity
import com.example.administrator.jipinshop.activity.school.video.VideoActivity
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.databinding.ItemSchoolGvContentBinding
import com.example.administrator.jipinshop.databinding.ItemSchoolGvFootBinding
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
class KTSchoolRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private var CONTENT = 1
    private var FOOT = 2

    private var mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>
    private var mContext: Context
    private lateinit var mOnClick: OnMoreClick

    constructor(mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>, mContext: Context){
        this.mList = mList
        this.mContext = mContext
    }

    fun setClick(onClick: OnMoreClick){
        mOnClick = onClick
    }

    override fun getItemViewType(position: Int): Int {
        if (mList.size >= 4){
            if (position < 4){
                return CONTENT
            }else {
                return FOOT
            }
        }else{
            return CONTENT
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            CONTENT -> {
                var itemSchoolContentBinding = DataBindingUtil.inflate<ItemSchoolGvContentBinding>(LayoutInflater.from(mContext), R.layout.item_school_gv_content, viewGroup, false)
                holder = ContentViewHolder(itemSchoolContentBinding)
            }
            FOOT -> {
                var itemSchoolGvFootBinding = DataBindingUtil.inflate<ItemSchoolGvFootBinding>(LayoutInflater.from(mContext), R.layout.item_school_gv_foot, viewGroup, false)
                holder = FootViewHolder(itemSchoolGvFootBinding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList.size >= 4) {
            5
        } else {
            mList.size
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            CONTENT -> {
                var contentViewHolder : ContentViewHolder = holder as ContentViewHolder
                contentViewHolder.run {
                    binding.date = mList[position]
                    binding.executePendingBindings()
                    var layoutParams = binding.itemContainer.layoutParams as RecyclerView.LayoutParams
                    if (position == mList.size - 1){
                        layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
                    }else{
                        layoutParams.rightMargin = 0
                    }
                    binding.itemContainer.layoutParams = layoutParams
                    binding.itemContainer.setOnClickListener {
                        mContext.startActivity(Intent(mContext, VideoActivity::class.java)
                                .putExtra("courseId", mList[position].id)
                        )
                    }
                }
            }
            FOOT -> {
                var footViewHolder : FootViewHolder = holder as FootViewHolder
                footViewHolder.run {
                    binding.itemMore.setOnClickListener {
                        //更多
                        mOnClick.onMore()
                    }
                }
            }
        }
    }

    inner class ContentViewHolder : RecyclerView.ViewHolder{

        var binding: ItemSchoolGvContentBinding

        constructor(itemView: ItemSchoolGvContentBinding) : super(itemView.root){
            binding = itemView
        }
    }

    inner class FootViewHolder : RecyclerView.ViewHolder{

        var binding: ItemSchoolGvFootBinding

        constructor(itemView: ItemSchoolGvFootBinding) : super(itemView.root){
            binding = itemView
        }
    }

    interface OnMoreClick{
       fun onMore()
    }
}