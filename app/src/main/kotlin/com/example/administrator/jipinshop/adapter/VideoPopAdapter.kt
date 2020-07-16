package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.databinding.ItemVideoBinding

/**
 * @author 莫小婷
 * @create 2020/7/16
 * @Describe
 */
class VideoPopAdapter : RecyclerView.Adapter<VideoPopAdapter.ViewHolder>{

    private var mContext: Context
    private var mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>
    private var location = 0
    private lateinit var mOnClick: OnClick

    constructor(mContext: Context, mList: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>){
        this.mContext = mContext
        this.mList = mList
    }

    fun setPosition(pos: Int){
        location = pos
    }

    fun setOnClick(onClick: OnClick){
        mOnClick = onClick
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemVideoBinding = DataBindingUtil.inflate<ItemVideoBinding>(LayoutInflater.from(mContext), R.layout.item_video, viewGroup, false)
        return ViewHolder(itemVideoBinding)
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            binding.itemName.text = mList[pos].title
            binding.itemLookNum.text = mList[pos].reading + "人学习"
            binding.itemGoodNum.text = mList[pos].liked
            binding.itemShareNum.text = mList[pos].share
            if (location == pos){
                binding.itemPlay.setImageResource(R.mipmap.school_played)
            }else{
                binding.itemPlay.setImageResource(R.mipmap.school_play)
            }
            binding.itemContainer.setOnClickListener {
                mOnClick.onClick(pos)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemVideoBinding

        constructor(itemView: ItemVideoBinding) : super(itemView.root){
            binding = itemView
        }
    }

    interface OnClick{
        fun onClick(pos: Int)
    }
}