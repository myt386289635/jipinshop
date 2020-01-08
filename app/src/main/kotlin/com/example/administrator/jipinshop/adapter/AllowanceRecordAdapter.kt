package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.AllowanceRecordBean
import com.example.administrator.jipinshop.databinding.ItemRecordBinding

/**
 * @author 莫小婷
 * @create 2020/1/7
 * @Describe
 */
class AllowanceRecordAdapter : RecyclerView.Adapter<AllowanceRecordAdapter.ViewHolder>{

    private var list:MutableList<AllowanceRecordBean.DataBean>
    private var mContext: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setOnClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(list: MutableList<AllowanceRecordBean.DataBean>, context: Context){
        this.list = list
        mContext = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemRecordBinding = DataBindingUtil.inflate<ItemRecordBinding>(LayoutInflater.from(mContext), R.layout.item_record, viewGroup, false)
        var holder = ViewHolder(itemRecordBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            binding.itemPriceOld.setColor(R.color.color_9D9D9D)
            binding.itemPriceOld.setTv(true)
            binding.date = list[pos]
            binding.executePendingBindings()
            itemView.setOnClickListener {
                mOnClickItem.onItem(pos)
            }
        }
    }


    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemRecordBinding

        constructor(binding: ItemRecordBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onItem(pos: Int)
    }
}