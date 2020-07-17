package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.TbOrderBean
import com.example.administrator.jipinshop.databinding.ItemRecoveryBinding

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
class OrderRecoveryAdapter : RecyclerView.Adapter<OrderRecoveryAdapter.ViewHolder>{

    private var mList: MutableList<TbOrderBean.DataBean>
    private var mContext: Context

    constructor(mList: MutableList<TbOrderBean.DataBean>, mContext: Context) {
        this.mList = mList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemRecoveryBinding>(LayoutInflater.from(mContext), R.layout.item_recovery, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.run {
            binding.date = mList[position]
            binding.executePendingBindings()
            if (position == 0){
                binding.itemTitleContainer.visibility = View.VISIBLE
            }else{
                binding.itemTitleContainer.visibility = View.GONE
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemRecoveryBinding

        constructor(itemView: ItemRecoveryBinding) : super(itemView.root){
            binding = itemView
        }
    }
}