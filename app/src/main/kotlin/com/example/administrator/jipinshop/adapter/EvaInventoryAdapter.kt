package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemEvaInventoryBinding

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
class EvaInventoryAdapter() : RecyclerView.Adapter<EvaInventoryAdapter.ViewHolder>() {

    private lateinit var mList: MutableList<String>
    private lateinit var context: Context

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<String>, context: Context) : this(){
        this.mList = mList
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemEvaInventoryBinding = DataBindingUtil.inflate<ItemEvaInventoryBinding>(LayoutInflater.from(context), R.layout.item_eva_inventory,viewGroup,false)
        var holder = ViewHolder(itemEvaInventoryBinding)
        return holder
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {

        }
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var mBinding: ItemEvaInventoryBinding

        constructor(binding: ItemEvaInventoryBinding) : super(binding.root){
            mBinding = binding
        }
    }
}