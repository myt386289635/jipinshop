package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemTeam2Binding

/**
 * @author 莫小婷
 * @create 2020/6/9
 * @Describe
 */
class TeamTwoAdapter : RecyclerView.Adapter<TeamTwoAdapter.ViewHolder>{

    private var mList: MutableList<String>
    private var mContext : Context
    private lateinit var mOnItem: OnItem

    fun setClick(onItem: OnItem){
        mOnItem = onItem
    }

    constructor(mList: MutableList<String>, context: Context){
        this.mList = mList
        this.mContext = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemTeam2Binding = DataBindingUtil.inflate<ItemTeam2Binding>(LayoutInflater.from(mContext), R.layout.item_team2, viewGroup, false)
        var holder = ViewHolder(itemTeam2Binding)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.run {
            var params =  binding.itemContainer.layoutParams as RecyclerView.LayoutParams
            if (position == 0) {
                params.topMargin = mContext.resources.getDimension(R.dimen.y30).toInt()
                params.bottomMargin = 0
                if (mList.size == 1) {
                    binding.itemContainer.setBackgroundResource(R.drawable.bg_balance)
                } else {
                    binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_one)
                }
            } else if (position == mList.size - 1) {
                params.topMargin = 0
                params.bottomMargin = mContext.resources.getDimension(R.dimen.y30).toInt()
                binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_last)
            } else {
                params.topMargin = 0
                params.bottomMargin = 0
                binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_other)
            }
            binding.itemContainer.layoutParams = params
            binding.itemContainer.setOnClickListener {
                mOnItem.onItem(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var binding: ItemTeam2Binding

        constructor(binding: ItemTeam2Binding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnItem{
        fun onItem(position: Int)
    }
}